package it.univaq.disim.sose.boredweekend.activitiesservice.business.impl.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
 	
	private static final String FK_ACTIVITIES_ID = "id_activity";
	private static final String CATEGORY_COLUMN = "category";
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

		String sql = "SELECT * FROM activities WHERE " + CITY_COLUMN + "=\"" + city + "\" AND " + DAYTIME_COLUMN + "=\"" + daytime.value() + "\"";
//		SELECT * FROM activities as a JOIN activities_days ON a.id_activity=activities_days.id_activity

		LOGGER.info("Activity DAO is going to perform the query: " + sql);

		List<Activity> result = new ArrayList<>();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			con = dataSource.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
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
