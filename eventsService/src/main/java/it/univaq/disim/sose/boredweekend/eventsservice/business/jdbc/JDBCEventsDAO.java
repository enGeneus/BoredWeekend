package it.univaq.disim.sose.boredweekend.eventsservice.business.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.univaq.disim.sose.boredweekend.eventsservice.business.EventsDAO;
import it.univaq.disim.sose.boredweekend.eventsservice.business.model.Event;
import it.univaq.disim.sose.boredweekend.eventsservice.business.util.Utility;

@Component
public class JDBCEventsDAO implements EventsDAO {

	private static Logger LOGGER = LoggerFactory.getLogger(JDBCEventsServiceImpl.class);

	// Table names BW_events
	private static final String EVENTS = "events";

	// Column names table events
	private static final String ID_COLUMN = "id_event";
	private static final String NAME_COLUMN = "name";
	private static final String CITY_COLUMN = "city";
	private static final String START_COLUMN = "start";
	private static final String END_COLUMN = "end";
	private static final String PAYMENT_COLUMN = "payment";
	private static final String IMG_COLUMN = "img";
	private static final String LOCATION_COLUMN = "location_name";
	private static final String DESCRIPTION_COLUMN = "description";
	private static final String INFO_COLUMN = "info";
	private static final String ADDRESS_COLUMN = "address";
	private static final String CATEGORY_COLUMN = "category";

	@Autowired
	private DataSource dataSource;

	@Override
	public void insert(Event event) {

		String query = "INSERT INTO `" + EVENTS + "`(`" + NAME_COLUMN + "`,`" + INFO_COLUMN + "`,`" + ADDRESS_COLUMN
				+ "`, `" + CITY_COLUMN + "`, `" + START_COLUMN + "`, `" + END_COLUMN + "`, `" + PAYMENT_COLUMN + "`, `"
				+ IMG_COLUMN + "`, `" + LOCATION_COLUMN + "`, `" + DESCRIPTION_COLUMN + "`, `" + CATEGORY_COLUMN + "`)"
				+ " VALUES ('" + event.getName().replace("'", "\\'") + "','" + event.getInfo().replace("'", "\\'")
				+ "','" + event.getAddress().replace("'", "\\'") + "','" + event.getCity().replace("'", "\\'") + "','"
				+ Utility.date2Mysql(event.getStart()) + "','" + Utility.date2Mysql(event.getEnd()) + "',"
				+ event.isPayment() + ",'" + event.getImg().replace("'", "\\'") + "','"
				+ event.getLocationName().replace("'", "\\'") + "','" + event.getDescription().replace("'", "\\'")
				+ "','" + event.getCategories().replace("'", "\\'") + "')";

		Connection con = null;
		Statement st = null;

		try {

			con = dataSource.getConnection();

			st = con.createStatement();
			st.executeUpdate(query);

			LOGGER.info("Event DAO is going to perform the query: " + query);

			st.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
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
	public List<Event> find(List<String> cities, Date start, Date end) {

		String sqlQuery = "SELECT * FROM " + EVENTS + " WHERE (";

		// Add where conditions for cities
		for (String city : cities) {
			if (cities.size() == 1) {
				sqlQuery = sqlQuery + CITY_COLUMN + " ='" + city.replace("'", "\\'") + "'";
			} else {
				sqlQuery = sqlQuery + CITY_COLUMN + " ='" + city.replace("'", "\\'") + "'" + " OR ";
			}
		}

		// Remove last "OR" occurrence and close parenthesis
		if (cities.size() > 1) {
			sqlQuery = sqlQuery.substring(0, sqlQuery.length() - 4);
		}

		// Add WHERE condition for event start and end
		sqlQuery = sqlQuery + ") AND " + START_COLUMN + " >= '" + Utility.date2Mysql(start) + "' AND " + END_COLUMN
				+ " <= '" + Utility.date2Mysql(end) + "'";

		LOGGER.info("Event DAO is going to perform the query: " + sqlQuery);

		List<Event> result = new ArrayList<>();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = dataSource.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);

			// Populating list of events
			while (rs.next()) {

				Event event = new Event();

				event.setId(rs.getInt(ID_COLUMN));
				event.setName(rs.getString(NAME_COLUMN));
				event.setCity(rs.getString(CITY_COLUMN));
				event.setStart(rs.getDate(START_COLUMN));
				event.setEnd(rs.getDate(END_COLUMN));
				event.setPayment(rs.getBoolean(PAYMENT_COLUMN));
				event.setImg(rs.getString(IMG_COLUMN));
				event.setDescription(rs.getString(DESCRIPTION_COLUMN));
				event.setLocationName(rs.getString(LOCATION_COLUMN));
				event.setAddress(rs.getString(ADDRESS_COLUMN));
				event.setInfo(rs.getString(INFO_COLUMN));
				event.setCategory(rs.getString(CATEGORY_COLUMN));
				result.add(event);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
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
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}
		return result;
	}

}
