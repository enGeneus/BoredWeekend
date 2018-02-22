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

	// nomi tabelle BW_events
	private static final String EVENTS = "events";

	// nomi colonne tabella events
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
				+ "`, `" + CITY_COLUMN + "`, `" + START_COLUMN + "`, `" + END_COLUMN + "`, `"
				+ PAYMENT_COLUMN + "`, `" + IMG_COLUMN + "`, `" + LOCATION_COLUMN + "`, `" + DESCRIPTION_COLUMN + "`, `"+CATEGORY_COLUMN+"`)"
				+ " VALUES ('" + event.getName().replace("'", "\\'") + "','" + event.getInfo().replace("'", "\\'")
				+ "','" + event.getAddress().replace("'", "\\'") + "','" + event.getCity().replace("'", "\\'") + "','"
				+ Utility.date2Mysql(event.getStart()) + "','" + Utility.date2Mysql(event.getEnd()) + "',"
				+ event.isPayment() + ",'" + event.getImg().replace("'", "\\'") + "','" + event.getLocationName().replace("'", "\\'") + "','"
				+ event.getDescription().replace("'", "\\'") +"','"+event.getCategories().replace("'", "\\'") + "')";

		Connection con = null;
		Statement st = null;

		try {

			con = dataSource.getConnection();

			st = con.createStatement();
			st.executeUpdate(query);

			
			LOGGER.info("Activity DAO is going to perform the query: " + query);

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
	public List<Event> find(List<String> city, Date start, Date end) {
		
		String sql_init0 = "SELECT * FROM " + EVENTS +" WHERE (";
		
		for(String citys : city) {
			if(city.size() == 1) {
				sql_init0 = sql_init0+CITY_COLUMN+" ='"+citys.replace("'", "\\'")+"'";
			} else {
				sql_init0 = sql_init0+CITY_COLUMN+" ='"+citys.replace("'", "\\'")+"'"+" OR ";				
			}	
		}
		
		String  sql_init;
		
		if(city.size() > 1) {
			 sql_init = sql_init0.substring(0, sql_init0.length()-4);
		}else {
			 sql_init = sql_init0;
		}
		
		sql_init = sql_init+") AND " + START_COLUMN + " >= '" + Utility.date2Mysql(start)
		+ "' AND " + END_COLUMN + " <= '" + Utility.date2Mysql(end) + "'";

		// sql_init contiene la query del giusto formato

		LOGGER.info("Activity DAO is going to perform the query: " + sql_init);

		List<Event> result = new ArrayList<>();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {
			con = dataSource.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sql_init);

			
			// popolazione della lista di attività da ritornare
			while (rs.next()) {
				// controllo che l'attività con quell'id non sia già stata inserita, se si
				// scarto la riga

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
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}

		}
		return result;
	}

}
