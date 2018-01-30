package it.univaq.disim.sose.boredweekend.activitiesservice.business.impl.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
 	
	private static final String FK_ACTIVITIES_ID_CATEGORY = "id_activity";
	private static final String CATEGORY_COLUMN = "category";
	
	private static final String FK_ACTIVITIES_ID_DAYS ="id_activity";
	private static final String DAY_COLUMN = "day";

	
	
	
	@Autowired
	private DataSource dataSource;

	@Override
	public void insert(Activity activity) {
		// TODO Auto-generated method stub		
	}

	@Override
	public Activity find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Activity> find(String city, List<ActivityCategory> categories, List<WeekDay> days, Daytime daytime) {

		String sql_init = "SELECT a.*, b."+DAY_COLUMN+", c."+CATEGORY_COLUMN+" FROM activities as a JOIN activities_days as b JOIN category_type as c ON a."+ID_COLUMN+"=b."+FK_ACTIVITIES_ID_DAYS+" AND a."+ID_COLUMN+"=c."+FK_ACTIVITIES_ID_CATEGORY+" WHERE a."+CITY_COLUMN+" ='"+city+"'";	
		
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
		
		sql_init = sql_init+" AND a.daytime='"+daytime.value()+"'";
		
		//sql_init contiene la query del giusto formato
		
		
		String sql = "SELECT * FROM activities WHERE " + CITY_COLUMN + "=\"" + city + "\" AND " + DAYTIME_COLUMN + "=\"" + daytime.value() + "\"";
		
		//SELECT * FROM activities as a JOIN activities_days ON a.id_activity=activities_days.id_activity

		LOGGER.info("Activity DAO is going to perform the query: " + sql_init);
		
        
	
		List<Activity> result = new ArrayList<>();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		/*
		ResultSet result_day = null;
		ResultSet result_category = null;
		*/
	    

		
		Map<Integer, Activity> activityMap = new HashMap<>();
		List<Activity> activityList = null;

		
		
		try {
			con = dataSource.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sql_init);		
			
			while (rs.next()) {
				
				/*
				if(!activityMap.containsKey(rs.getInt(ID_COLUMN))) {
					System.err.println("diocristo2");
					
					
					List<WeekDay> day_list = new ArrayList<WeekDay>();
					
					List<ActivityCategory> category_list = new ArrayList<ActivityCategory>();
					
					
					
					while(result_day.next()) {
						
						if(result_day.getInt(ID_COLUMN) == rs.getInt(ID_COLUMN) && !day_list.contains(result_day.getObject(DAY_COLUMN))){
							System.err.println("asdasfsjkdh");
							day_list.add((WeekDay) result_day.getObject(DAY_COLUMN));
						}
					}
					System.err.println(day_list);
					
					
					System.err.println(day_list);
					
					while(result_category.next()) {
						if(result_category.getInt(ID_COLUMN) == rs.getInt(ID_COLUMN) && !category_list.contains(result_category.getObject(CATEGORY_COLUMN))){
							category_list.add((ActivityCategory)result_category.getObject(CATEGORY_COLUMN));
						}
					}
					
					System.err.println(category_list);


					/*
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
				*/
				
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

				result.add(activity);

				
			}
			
			activityList = new ArrayList<Activity>(activityMap.values());
			

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
