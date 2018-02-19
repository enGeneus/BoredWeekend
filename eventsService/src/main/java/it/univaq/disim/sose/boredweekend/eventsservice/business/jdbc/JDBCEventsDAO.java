package it.univaq.disim.sose.boredweekend.eventsservice.business.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	private static final String CATEGORY_TYPE = "category_type";

	// nomi colonne tabella events
	private static final String ID_COLUMN = "id_event";
	private static final String NAME_COLUMN = "name";
	private static final String CITY_COLUMN = "city";
	private static final String DATE_COLUMN = "date";
	private static final String START_COLUMN = "start";
	private static final String END_COLUMN = "end";
	private static final String PAYMENT_COLUMN = "payment";
	private static final String IMG_COLUMN = "img";
	private static final String LOCATION_COLUMN = "location_name";
	private static final String DESCRIPTION_COLUMN = "description";
	private static final String INFO_COLUMN = "info";
	private static final String ADDRESS_COLUMN = "address";

	// nomi colonne tabella category
	private static final String FK_ACTIVITIES_ID_CATEGORY = "id_event";
	private static final String CATEGORY_COLUMN = "category";

	@Autowired
	private DataSource dataSource;

	@Override
	public void insert(Event event) {

		String query = "INSERT INTO `" + EVENTS + "`(`" + NAME_COLUMN + "`,`" + INFO_COLUMN + "`,`" + ADDRESS_COLUMN
				+ "`, `" + CITY_COLUMN + "`, `" + DATE_COLUMN + "`, `" + START_COLUMN + "`, `" + END_COLUMN + "`, `"
				+ PAYMENT_COLUMN + "`, `" + IMG_COLUMN + "`, `" + LOCATION_COLUMN + "`, `" + DESCRIPTION_COLUMN + "`, `" + IMG_COLUMN + "`)"
				+ " VALUES ('" + event.getName().replace("'", "\\'") + "','" + event.getInfo().replace("'", "\\'")
				+ "','" + event.getAddress().replace("'", "\\'") + "','" + event.getCity().replace("'", "\\'") + "','"
				+ "2018-10-10"+"','" + Utility.date2Mysql(event.getStart()) + "','" + Utility.date2Mysql(event.getEnd()) + "',"
				+ event.isPayment() + "," + event.getImg() + ",'" + event.getLocationName().replace("'", "\\'") + "','"
				+ event.getDescription().replace("'", "\\'") + "','" + event.getImg().replace("'", "\\'") + "')";

		// dobbiamo fare i controlli sulle caratteristiche dell'evento?

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {

			int risultato = -1;
			con = dataSource.getConnection();

			st = con.createStatement();
			st.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

			rs = st.getGeneratedKeys();
			if (rs.next()) {
				risultato = rs.getInt(1);
			}

			String category = event.getCategories();

			String query_category = "INSERT INTO `" + CATEGORY_TYPE + "`(`" + FK_ACTIVITIES_ID_CATEGORY + "`, `"
					+ CATEGORY_COLUMN + "`) VALUES (" + risultato + ",'" + category + "')";
			st.executeUpdate(query_category);

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
	public List<Event> find(List<String> city, Date start, Date end) {

		/*
		String sql_init0 = "SELECT a.*, b."+DAY_COLUMN+", c."+CATEGORY_COLUMN+" FROM "+ACTIVITIES+" as a JOIN "+ACTIVITIES_DAYS+" as b JOIN "+CATEGORY_TYPE+" as c ON a."+ID_COLUMN+"=b."+FK_ACTIVITIES_ID_DAYS+" AND a."+ID_COLUMN+"=c."+FK_ACTIVITIES_ID_CATEGORY+" WHERE";	

		for(String citys : city) {
			if(city.size() == 1) {
				sql_init0 = sql_init0+" a."+CITY_COLUMN+" ='"+citys.replace("'", "\\'")+"'";
			} else {
				sql_init0 = sql_init0+" a."+CITY_COLUMN+" ='"+citys.replace("'", "\\'")+"'"+" OR ";				
			}	
		}
		String  sql_init;
		
		if(city.size() > 1) {
			 sql_init = sql_init0.substring(0, sql_init0.length()-4);
		}else {
			 sql_init = sql_init0;
		}
		*/
		

		// inizio costruzione query
		//old query
		/*
		String sql_init = "SELECT a.*, c." + CATEGORY_COLUMN + " FROM " + EVENTS + " as a JOIN " + CATEGORY_TYPE
				+ " as c ON a." + ID_COLUMN + "=c." + FK_ACTIVITIES_ID_CATEGORY + " WHERE a." + CITY_COLUMN + " = '"
				+ city.replace("'", "\\'") + "" + "' AND a." + START_COLUMN + " >= '" + Utility.date2Mysql(start)
				+ "' AND a." + END_COLUMN + " <= '" + Utility.date2Mysql(end) + "'";
		 */
		
		String sql_init0 = "SELECT a.*, c." + CATEGORY_COLUMN + " FROM " + EVENTS + " as a JOIN " + CATEGORY_TYPE
				+ " as c ON a." + ID_COLUMN + "=c." + FK_ACTIVITIES_ID_CATEGORY + " WHERE (";
		
		for(String citys : city) {
			if(city.size() == 1) {
				sql_init0 = sql_init0+" a."+CITY_COLUMN+" ='"+citys.replace("'", "\\'")+"'";
			} else {
				sql_init0 = sql_init0+" a."+CITY_COLUMN+" ='"+citys.replace("'", "\\'")+"'"+" OR ";				
			}	
		}
		
		String  sql_init;
		
		if(city.size() > 1) {
			 sql_init = sql_init0.substring(0, sql_init0.length()-4);
		}else {
			 sql_init = sql_init0;
		}
		
		sql_init = sql_init+") AND a." + START_COLUMN + " >= '" + Utility.date2Mysql(start)
		+ "' AND a." + END_COLUMN + " <= '" + Utility.date2Mysql(end) + "'";
		
		System.out.println(sql_init);

		// sql_init contiene la query del giusto formato

		LOGGER.debug("Activity DAO is going to perform the query: " + sql_init);

		List<Event> result = new ArrayList<>();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		// utilizziamo questa map per controllare che l'attività non sia già stata
		// inserita nel result
		Map<Integer, Event> eventMap = new HashMap<>();

		// liste di map che rispettivamente conterranno giorni e categorie delle righe
		// del result set
		List<Map<Integer, String>> listMap = new ArrayList<>();

		try {
			con = dataSource.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sql_init);

			// popolazione della lista di giorni e categorie
			while (rs.next()) {
				Map<Integer, String> mapCategory = new HashMap<>();
				mapCategory.put(rs.getInt(ID_COLUMN), rs.getString(CATEGORY_COLUMN));
				listMap.add(mapCategory);
			}

			// serve per far ripuntare alla prima locazione del result set
			rs.beforeFirst();

			// popolazione della lista di attività da ritornare
			while (rs.next()) {
				// controllo che l'attività con quell'id non sia già stata inserita, se si
				// scarto la riga
				if (!eventMap.containsKey(rs.getInt(ID_COLUMN))) {

					List<String> category_list = new ArrayList<>();

					for (Map<Integer, String> m : listMap) {
						if (m.containsKey(rs.getInt(ID_COLUMN))
								&& !category_list.contains(m.get(rs.getInt(ID_COLUMN)))) {
							category_list.add(m.get(rs.getInt(ID_COLUMN)));
						}
					}

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
					event.setCategory(category_list.get(0));
					eventMap.put(rs.getInt(ID_COLUMN), event);
					result.add(event);

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
