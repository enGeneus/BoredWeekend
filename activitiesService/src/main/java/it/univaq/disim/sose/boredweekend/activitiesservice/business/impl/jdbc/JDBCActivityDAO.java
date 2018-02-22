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
	
	// Table names BW_activities
	private static final String ACTIVITIES = "activities";
	private static final String ACTIVITIES_DAYS = "activities_days";
	private static final String CATEGORY_TYPE = "category_type";
	
	// Column names for table activities
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
	
	// Column names for table category
	private static final String FK_ACTIVITIES_ID_CATEGORY = "id_activity";
	private static final String CATEGORY_COLUMN = "category";
	
	// Column names for table  day
	private static final String FK_ACTIVITIES_ID_DAYS ="id_activity";
	private static final String DAY_COLUMN = "day";

	@Autowired
	private DataSource dataSource;

	@Override
	public void insert(Activity activity) {

		//  Query for activity insert
		String query = "INSERT INTO `"+ACTIVITIES+"`(`"+NAME_COLUMN+"`, `"+CITY_COLUMN+"`, `"+LAT_COLUMN+"`, `"+LON_COLUMN+"`, `"+DAYTIME_COLUMN+"`, `"+STATE_COLUMN+"`, `"+INFO_COLUMN+"`, `"+PAYMENT_COLUMN+"`, `"+IMG_COLUMN+"`)"
				+ " VALUES ('"+activity.getName().replace("'", "\\'")+"','"+activity.getCity().replace("'", "\\'")+"',"+activity.getLat()+","+activity.getLon()+",'"+activity.getDaytime()+"',"+activity.isAvailable()+",'"+activity.getInfo().replace("'", "\\'")+"',"+activity.isPayment()+",'"+activity.getImg()+"')";		

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			
			con = dataSource.getConnection();
			
	        st = con.createStatement();

	        LOGGER.info("ActivityDAO is going to perform the query " + query);

	        st.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
	        
		    int generatedKey = -1;
	        rs = st.getGeneratedKeys();
	        if (rs.next()){
	            generatedKey=rs.getInt(1);
            }
	        
	        //Insert categories for the activity
	        List<String> categories = activity.getCategories();
	     
	        for(String cateory : categories) {
		        String query_category = "INSERT INTO `"+CATEGORY_TYPE+"`(`"+FK_ACTIVITIES_ID_CATEGORY+"`, `"+CATEGORY_COLUMN+"`) VALUES ("+generatedKey+",'"+cateory+"')";

		        LOGGER.info("ActivityDAO is going to perform the query " + query_category);

		        st.executeUpdate(query_category);
	        }
	            
	        //Insert days for the activity
	        List<String> days = activity.getDays();
	        
	        for(String day : days) {
		        String query_days = "INSERT INTO `"+ACTIVITIES_DAYS+"`(`"+FK_ACTIVITIES_ID_DAYS+"`, `"+DAY_COLUMN+"`) VALUES ("+generatedKey+",'"+day+"')";

		        LOGGER.info("ActivityDAO is going to perform the query " + query_days);

		        st.executeUpdate(query_days);
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
	}

	@Override
	public Activity find(int id) {
		// Query for activity find
		String sql_init = "SELECT a.*, b."+DAY_COLUMN+", c."+CATEGORY_COLUMN+" FROM "+ACTIVITIES+" as a JOIN "+ACTIVITIES_DAYS+" as b JOIN "+CATEGORY_TYPE+" as c ON a."+ID_COLUMN+"=b."+FK_ACTIVITIES_ID_DAYS+" AND a."+ID_COLUMN+"=c."+FK_ACTIVITIES_ID_CATEGORY+" WHERE a."+ID_COLUMN+" ='"+id+"'";	

		LOGGER.info("Activity DAO is going to perform the query: " + sql_init);

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		Activity activity = null;
		Set<String> categories = new HashSet<>(); // Keep track of returned categories
		Set<String> days = new HashSet<>(); // Keep track of returned days
		
		try {
			con = dataSource.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sql_init);
			
			// Populate category and days sets
			while(rs.next()) {
				categories.add(rs.getString(CATEGORY_COLUMN));
				days.add(rs.getString(DAY_COLUMN));
			}
			
			// After reading categories and days 
			rs.beforeFirst();
			
			// Read again the returned activity fields and add to the object to return
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
				activity.setImg(rs.getString(IMG_COLUMN));

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
	public List<Activity> find(List<String> city, List<String> categories, List<String> days) {

		// Start building query
		String sql_init0 = "SELECT a.*, b."+DAY_COLUMN+", c."+CATEGORY_COLUMN+" FROM "+ACTIVITIES+" as a JOIN "+ACTIVITIES_DAYS+" as b JOIN "+CATEGORY_TYPE+" as c ON a."+ID_COLUMN+"=b."+FK_ACTIVITIES_ID_DAYS+" AND a."+ID_COLUMN+"=c."+FK_ACTIVITIES_ID_CATEGORY+" WHERE (";	

		// Add where condition for cities
		for(String citys : city) {
			if(city.size() == 1) {
				sql_init0 = sql_init0+" a."+CITY_COLUMN+" ='"+citys.replace("'", "\\'")+"'";
			} else {
				sql_init0 = sql_init0+" a."+CITY_COLUMN+" ='"+citys.replace("'", "\\'")+"'"+" OR ";				
			}	
		}
		String  sql_init;
		
		// Remove last "OR" occurrence and close parenthesis
		if(city.size() > 1) {
			 sql_init = sql_init0.substring(0, sql_init0.length()-4);
		}else {
			 sql_init = sql_init0;
		}
		sql_init = sql_init + ")";

		// Add where condition for days
		if(!days.isEmpty()) {
			sql_init += " AND (";		
			String sql_day = sql_init;
			for(int i =0; i<days.size();i++) {
				sql_day = sql_day+"b."+DAY_COLUMN+"='"+days.get(i)+"' OR ";
			}
			sql_init = sql_day.substring(0, sql_day.length()-4)+")";						
		}
		
		// Add where condition for categories
		if(!categories.isEmpty()) {
			sql_init += " AND (";
			String sql_category = sql_init;
			for(int i =0; i<categories.size();i++) {
				sql_category = sql_category+"c."+CATEGORY_COLUMN+"='"+categories.get(i)+"' OR ";
			}
			sql_init = sql_category.substring(0, sql_category.length()-4)+")";
		}

		LOGGER.info("Activity DAO is going to perform the query: " + sql_init);
	
		List<Activity> result = new ArrayList<>();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		// Map which keeps track that the activity has not been inserted yet into results
		Map<Integer, Activity> activityMap = new HashMap<>();

		// List of maps which contain days and categories rows of result set
		List<Map<Integer,String>> dayListMap = new ArrayList<>();
		List<Map<Integer,String>> categoryListMap = new ArrayList<>(); 
		
		try {
			con = dataSource.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sql_init);
			
			// Populate days and categories list
			while(rs.next()) {
				Map<Integer,String> mapDay = new HashMap<>();
				Map<Integer,String> mapCategory = new HashMap<>();
				mapDay.put(rs.getInt(ID_COLUMN), rs.getString(DAY_COLUMN));
				dayListMap.add(mapDay);
				mapCategory.put(rs.getInt(ID_COLUMN), rs.getString(CATEGORY_COLUMN));
				categoryListMap.add(mapCategory);
			}
			
			// Read again result list 
			rs.beforeFirst();
			
			// Populate activity list
			while (rs.next()) {
				// Check if the current activity has been inserted yet into the results
				if(!activityMap.containsKey(rs.getInt(ID_COLUMN))) {

					// List of days and categories of the activity
					List<String> dayList = new ArrayList<>();
					List<String> categoryList = new ArrayList<>();

					// Iterate over the of days and categories list of all the activities found and add to the list of the current
					for(Map<Integer,String> m : dayListMap) {
						if(m.containsKey(rs.getInt(ID_COLUMN)) && !dayList.contains(m.get(rs.getInt(ID_COLUMN)))) {
							String w = m.get(rs.getInt(ID_COLUMN));
							dayList.add(w);
						}
					}
					
					for(Map<Integer,String> m : categoryListMap) {
						if(m.containsKey(rs.getInt(ID_COLUMN)) && !categoryList.contains(m.get(rs.getInt(ID_COLUMN)))) {
							String a = m.get(rs.getInt(ID_COLUMN));
							categoryList.add(a);
						}
					}

					Activity activity = new Activity();
					
					activity.setId(rs.getInt(ID_COLUMN));
					activity.setName(rs.getString(NAME_COLUMN));
					activity.setCity(rs.getString(CITY_COLUMN));
					activity.setLat(rs.getLong(LAT_COLUMN));
					activity.setLon(rs.getLong(LON_COLUMN));
					activity.setDaytime(rs.getString(DAYTIME_COLUMN));
					activity.setState(rs.getBoolean(STATE_COLUMN));
					activity.setInfo(rs.getString(INFO_COLUMN));
					activity.setPayment(rs.getBoolean(PAYMENT_COLUMN));
					activity.setImg(rs.getString(IMG_COLUMN));
					activity.setDays(dayList);
					activity.setCategories(categoryList);
					activityMap.put(rs.getInt(ID_COLUMN), activity);
					
					result.add(activity);
				
				}
				
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
		return result;
	}
}
