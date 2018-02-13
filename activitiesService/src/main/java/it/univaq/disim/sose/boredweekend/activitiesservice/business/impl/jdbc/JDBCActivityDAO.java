package it.univaq.disim.sose.boredweekend.activitiesservice.business.impl.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
				+ " VALUES ('"+activity.getName().replace("'", "\\'")+"','"+activity.getCity().replace("'", "\\'")+"',"+activity.getLat()+","+activity.getLon()+",'"+activity.getDaytime()+"',"+activity.isAvailable()+",'"+activity.getInfo().replace("'", "\\'")+"',"+activity.isPayment()+","+activity.getImg()+")";		
		
		// dobbiamo fare i controlli sulle caratteristiche dell'attività?
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			
		    int risultato = -1;
			con = dataSource.getConnection();
			
	        st = con.createStatement();
	        st.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
	        
	        rs = st.getGeneratedKeys();
	        if (rs.next()){
	            risultato=rs.getInt(1);
	            }
	        
	        //ciclo per la generazione della query delle categorie
	        List<String> categories = activity.getCategories();
	     
	        for(String cateory : categories) {
		        String query_category = "INSERT INTO `"+CATEGORY_TYPE+"`(`"+FK_ACTIVITIES_ID_CATEGORY+"`, `"+CATEGORY_COLUMN+"`) VALUES ("+risultato+",'"+cateory+"')";
		        st.executeUpdate(query_category);
	        }
	            
	        //ciclo per la generazione della query dei giorni
	        List<String> days = activity.getDays();
	        
	        for(String day : days) {
		        String query_days = "INSERT INTO `"+ACTIVITIES_DAYS+"`(`"+FK_ACTIVITIES_ID_DAYS+"`, `"+DAY_COLUMN+"`) VALUES ("+risultato+",'"+day+"')";
		        st.executeUpdate(query_days);
	        }
	        
	        rs.close();
	        st.close();
	        		   
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (st != null) {
				try {
					st.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}	
	}

	@Override
	public Activity find(int id) {
		// inizio costruzione query
		String sql_init = "SELECT a.*, b."+DAY_COLUMN+", c."+CATEGORY_COLUMN+" FROM "+ACTIVITIES+" as a JOIN "+ACTIVITIES_DAYS+" as b JOIN "+CATEGORY_TYPE+" as c ON a."+ID_COLUMN+"=b."+FK_ACTIVITIES_ID_DAYS+" AND a."+ID_COLUMN+"=c."+FK_ACTIVITIES_ID_CATEGORY+" WHERE a."+ID_COLUMN+" ='"+id+"'";	

		LOGGER.info("Activity DAO is going to perform the query: " + sql_init);

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		Activity activity = null;
		Set<String> categories = new HashSet<>();
		Set<String> days = new HashSet<>();
		
		try {
			con = dataSource.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sql_init);
			
			// popolazione della lista di giorni e categorie
			while(rs.next()) {
				categories.add(rs.getString(CATEGORY_COLUMN));
				days.add(rs.getString(DAY_COLUMN));
			}
			
			// serve per far ripuntare alla prima locazione del result set 
			rs.beforeFirst();
			
			// popolazione della lista di attività da ritornare
			if (rs.next()) {
				activity = new Activity();

				for (String day : days) {
					activity.getDays().add(day);
				}

				for (String category : categories) {
					activity.getCategories().add(category);
				}

				activity.setId(rs.getInt(ID_COLUMN));
				activity.setName(rs.getString(NAME_COLUMN));
				activity.setCity(rs.getString(CITY_COLUMN));
				activity.setLat(rs.getLong(LAT_COLUMN));
				activity.setLat(rs.getLong(LON_COLUMN));
				activity.setDaytime(rs.getString(DAYTIME_COLUMN));
				activity.setState(rs.getBoolean(STATE_COLUMN));
				activity.setInfo(rs.getString(INFO_COLUMN));
				activity.setPayment(rs.getBoolean(PAYMENT_COLUMN));
				activity.setImg(rs.getBytes(IMG_COLUMN));

			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (st != null) {
				try {
					st.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return activity;
	}

	@Override
	public List<Activity> find(String city, List<String> categories, List<String> days, String daytime) {

		// inizio costruzione query
		String sql_init = "SELECT a.*, b."+DAY_COLUMN+", c."+CATEGORY_COLUMN+" FROM "+ACTIVITIES+" as a JOIN "+ACTIVITIES_DAYS+" as b JOIN "+CATEGORY_TYPE+" as c ON a."+ID_COLUMN+"=b."+FK_ACTIVITIES_ID_DAYS+" AND a."+ID_COLUMN+"=c."+FK_ACTIVITIES_ID_CATEGORY+" WHERE a."+CITY_COLUMN+" ='"+city.replace("'", "\\'")+"'";	
		
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
		
		if (daytime!=null && !daytime.isEmpty()) {
			//sql_init contiene la query del giusto formato
			sql_init = sql_init+" AND a.daytime='"+daytime+"'";
		}
		
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
					
					List<String> day_list = new ArrayList<>();
					List<String> category_list = new ArrayList<>();
					
					for(Map<Integer,String> m : listMap) {
						if(m.containsKey(rs.getInt(ID_COLUMN)) && !day_list.contains(m.get(rs.getInt(ID_COLUMN)))) {
							String w = m.get(rs.getInt(ID_COLUMN));
							day_list.add(w);
						}
					}
					
					for(Map<Integer,String> m : listMap2) {
						if(m.containsKey(rs.getInt(ID_COLUMN)) && !category_list.contains(m.get(rs.getInt(ID_COLUMN)))) {
							String a = m.get(rs.getInt(ID_COLUMN));
							category_list.add(a);
						}
					}

					Activity activity = new Activity();
					
					activity.setId(rs.getInt(ID_COLUMN));
					activity.setName(rs.getString(NAME_COLUMN));
					activity.setCity(rs.getString(CITY_COLUMN));
					activity.setLat(rs.getLong(LAT_COLUMN));
					activity.setLat(rs.getLong(LON_COLUMN));
					activity.setDaytime(rs.getString(DAYTIME_COLUMN));
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
