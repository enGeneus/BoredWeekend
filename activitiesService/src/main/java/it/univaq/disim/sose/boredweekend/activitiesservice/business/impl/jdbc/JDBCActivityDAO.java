package it.univaq.disim.sose.boredweekend.activitiesservice.business.impl.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import it.univaq.disim.sose.boredweekend.activitiesservice.ActivityCategory;
import it.univaq.disim.sose.boredweekend.activitiesservice.Daytime;
import it.univaq.disim.sose.boredweekend.activitiesservice.WeekDay;
import it.univaq.disim.sose.boredweekend.activitiesservice.business.ActivityDAO;
import it.univaq.disim.sose.boredweekend.activitiesservice.business.model.Activity;

@Component
public class JDBCActivityDAO implements ActivityDAO {

	private static Logger LOGGER = LoggerFactory.getLogger(JDBCActivitiesServiceImpl.class);
	
	// nomi tabelle BW_activities
	private static final String ACTIVITIES = "activities";
	private static final String ACTIVITIES_DAYS = "activities_days";
	private static final String CATEGORY_TYPE = "category_type";
	
	// nomi colonne tabella activities
	private static final String ID_COLUMN = "id_activity";
	private static final String NAME_COLUMN = "name";
	private static final String CITY_COLUMN = "city";
	private static final String LAT_COLUMN = "lat";
	private static final String LON_COLUMN = "longi";
	private static final String DAYTIME_COLUMN = "daytime";
	private static final String STATE_COLUMN = "state";
	private static final String INFO_COLUMN = "info";
	private static final String PAYMENT_COLUMN = "payment";
	private static final String IMG_COLUMN = "img";
	
	// nomi colonne tabella category
	private static final String FK_ACTIVITIES_ID_CATEGORY = "id_activity";
	private static final String CATEGORY_COLUMN = "category";
	
	// nomi colonne tabella day
	private static final String FK_ACTIVITIES_ID_DAYS ="id_activity";
	private static final String DAY_COLUMN = "day";

	@Autowired
	private DataSource dataSource;

	@Override
	public void insert(Activity activity) {

		String query = "INSERT INTO `"+ACTIVITIES+"`(`"+NAME_COLUMN+"`, `"+CITY_COLUMN+"`, `"+LAT_COLUMN+"`, `"+LON_COLUMN+"`, `"+DAYTIME_COLUMN+"`, `"+STATE_COLUMN+"`, `"+INFO_COLUMN+"`, `"+PAYMENT_COLUMN+"`, `"+IMG_COLUMN+"`)"
				+ " VALUES ('"+activity.getName()+"','"+activity.getCity()+"',"+activity.getLat()+","+activity.getLon()+",'"+activity.getDaytime()+"',"+activity.isState()+",'"+activity.getInfo()+"',"+activity.isPayment()+","+activity.getImg()+")";		
		
		// dobbiamo fare i controlli sulle caratteristiche dell'attività?
		
		Connection con = null;
		try {
			
		    int numero;
		    int risultato = -1;
			con = dataSource.getConnection();
			
	        Statement stmt = con.createStatement();
	        numero = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
	        
	        ResultSet rs = stmt.getGeneratedKeys();
	        if (rs.next()){
	            risultato=rs.getInt(1);
	            }
	        
	        //ciclo per la generazione della query delle categorie
	        List<ActivityCategory> category = activity.getCategories();
	     
	        for(ActivityCategory i : category) {
		        String query_category = "INSERT INTO `"+CATEGORY_TYPE+"`(`"+FK_ACTIVITIES_ID_CATEGORY+"`, `"+CATEGORY_COLUMN+"`) VALUES ("+risultato+",'"+i+"')";
		        stmt.executeUpdate(query_category);
	        }
	            
	        //ciclo per la generazione della query dei giorni
	        List<WeekDay> days = activity.getDays();
	        
	        for(WeekDay i : days) {
		        String query_days = "INSERT INTO `"+ACTIVITIES_DAYS+"`(`"+FK_ACTIVITIES_ID_DAYS+"`, `"+DAY_COLUMN+"`) VALUES ("+risultato+",'"+i+"')";
		        stmt.executeUpdate(query_days);
	        }
	        
	        rs.close();
	        stmt.close();
	        		   
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
				}
			}
		}	
	}

	@Override
	public Activity find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Activity> find(String city, List<ActivityCategory> categories, List<WeekDay> days, Daytime daytime) {

		// inizio costruzione query
		String sql_init = "SELECT a.*, b."+DAY_COLUMN+", c."+CATEGORY_COLUMN+" FROM "+ACTIVITIES+" as a JOIN "+ACTIVITIES_DAYS+" as b JOIN "+CATEGORY_TYPE+" as c ON a."+ID_COLUMN+"=b."+FK_ACTIVITIES_ID_DAYS+" AND a."+ID_COLUMN+"=c."+FK_ACTIVITIES_ID_CATEGORY+" WHERE a."+CITY_COLUMN+" ='"+city+"'";	
		
		if(!days.isEmpty()) {
			sql_init += " AND (";		
			String sql_day = sql_init;
			for(int i =0; i<days.size();i++) {
				sql_day = sql_day+"b."+DAY_COLUMN+"='"+days.get(i)+"' OR ";
			}
			sql_init = sql_day.substring(0, sql_day.length()-4)+")";						
		}
		
		if(!categories.isEmpty()) {
			sql_init += " AND (";
			String sql_category = sql_init;
			for(int i =0; i<categories.size();i++) {
				sql_category = sql_category+"c."+CATEGORY_COLUMN+"='"+categories.get(i)+"' OR ";
			}
			sql_init = sql_category.substring(0, sql_category.length()-4)+")";
		}
		
		//sql_init contiene la query del giusto formato
		sql_init = sql_init+" AND a.daytime='"+daytime.value()+"'";
		
		LOGGER.info("Activity DAO is going to perform the query: " + sql_init);
	
		List<Activity> result = new ArrayList<>();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		// utilizziamo questa map per controllare che l'attività non sia già stata inserita nel result
		Map<Integer, Activity> activityMap = new HashMap<>();
		//liste di map che rispettivamente conterranno giorni e categorie delle righe del result set
		List<Map<Integer,String>> listMap = new ArrayList<>();
		List<Map<Integer,String>> listMap2 = new ArrayList<>(); 
		
		try {
			con = dataSource.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sql_init);
			
			// popolazione della lista di giorni e categorie
			while(rs.next()) {
				Map<Integer,String> mapDay = new HashMap<>();
				Map<Integer,String> mapCategory = new HashMap<>();
				mapDay.put(rs.getInt(ID_COLUMN), rs.getString(DAY_COLUMN));
				listMap.add(mapDay);
				mapCategory.put(rs.getInt(ID_COLUMN), rs.getString(CATEGORY_COLUMN));
				listMap2.add(mapCategory);
			}
			
			// serve per far ripuntare alla prima locazione del result set 
			rs.beforeFirst();
			
			// popolazione della lista di attività da ritornare
			while (rs.next()) {
				// controllo che l'attività con quell'id non sia già stata inserita, se si scarto la riga
				if(!activityMap.containsKey(rs.getInt(ID_COLUMN))) {
					
					List<WeekDay> day_list = new ArrayList<WeekDay>();
					List<ActivityCategory> category_list = new ArrayList<ActivityCategory>();
					
					for(Map<Integer,String> m : listMap) {
						if(m.containsKey(rs.getInt(ID_COLUMN)) && !day_list.contains(WeekDay.fromValue(m.get(rs.getInt(ID_COLUMN))))) {
							WeekDay w = WeekDay.fromValue(m.get(rs.getInt(ID_COLUMN)));
							day_list.add(w);
						}
					}
					
					for(Map<Integer,String> m : listMap2) {
						if(m.containsKey(rs.getInt(ID_COLUMN)) && !category_list.contains(ActivityCategory.fromValue(m.get(rs.getInt(ID_COLUMN))))) {
							ActivityCategory a = ActivityCategory.fromValue(m.get(rs.getInt(ID_COLUMN)));
							category_list.add(a);
						}
					}

					Activity activity = new Activity();
					
					activity.setId(rs.getInt(ID_COLUMN));
					activity.setName(rs.getString(NAME_COLUMN));
					activity.setCity(rs.getString(CITY_COLUMN));
					activity.setLat(rs.getLong(LAT_COLUMN));
					activity.setLat(rs.getLong(LON_COLUMN));
					activity.setDaytime(Daytime.fromValue(rs.getString(DAYTIME_COLUMN)));
					activity.setState(rs.getBoolean(STATE_COLUMN));
					activity.setInfo(rs.getString(INFO_COLUMN));
					activity.setPayment(rs.getBoolean(PAYMENT_COLUMN));
					activity.setImg(rs.getBytes(IMG_COLUMN));
					activity.setDays(day_list);
					activity.setCategories(category_list);
					activityMap.put(rs.getInt(ID_COLUMN), activity);
					
					result.add(activity);
				
				}
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (SQLException e) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
				}
			}

		}
		return result;
	}
}
