"package com.realnet.template.service;" + "\r\n" + 
"" + "\r\n" + 
"import java.sql.Connection;" + "\r\n" + 
"import java.sql.DriverManager;" + "\r\n" + 
"import java.sql.ResultSet;" + "\r\n" + 
"import java.sql.SQLException;" + "\r\n" + 
"import java.sql.Statement;" + "\r\n" + 
"import java.util.ArrayList;" + "\r\n" + 
"import java.util.List;" + "\r\n" + 
"" + "\r\n" + 
"import org.springframework.beans.factory.annotation.Autowired;" + "\r\n" + 
"import org.springframework.beans.factory.annotation.Value;" + "\r\n" + 
"import org.springframework.stereotype.Service;" + "\r\n" + 
"import org.springframework.web.bind.annotation.PathVariable;" + "\r\n" + 
"" + "\r\n" + 
"@Service" + "\r\n" + 
"public class DatabaseQueryService {" + "\r\n" + 
"" + "\r\n" + 
"	@Value(\"${spring.datasource.username}\")" + "\r\n" + 
"	private String userName;" + "\r\n" + 
"" + "\r\n" + 
"	@Value(\"${spring.datasource.password}\")" + "\r\n" + 
"	private String password;" + "\r\n" + 
"" + "\r\n" + 
"	@Value(\"${spring.datasource.url}\")" + "\r\n" + 
"	private String url;" + "\r\n" + 
"" + "\r\n" + 
"	// get all column" + "\r\n" + 
"" + "\r\n" + 
"	public List<String> getColumnAliasList1(String table_schema, String tABLE_NAME) {" + "\r\n" + 
"		String query = \"SELECT column_name FROM information_schema.columns WHERE TABLE_SCHEMA='\" + table_schema" + "\r\n" + 
"				+ \"' and table_name = '\" + tABLE_NAME + \"' \";" + "\r\n" + 
"		List<String> list = new ArrayList<String>();" + "\r\n" + 
"		try (Connection con = DriverManager.getConnection(url, userName, password);" + "\r\n" + 
"" + "\r\n" + 
"				Statement stmt = con.createStatement()) {" + "\r\n" + 
"			ResultSet rs = stmt.executeQuery(query);" + "\r\n" + 
"			while (rs.next()) {" + "\r\n" + 
"				String coffeeName = rs.getString(\"column_name\");" + "\r\n" + 
"				list.add(coffeeName);" + "\r\n" + 
"			}" + "\r\n" + 
"		} catch (SQLException e) {" + "\r\n" + 
"			e.printStackTrace();" + "\r\n" + 
"		}" + "\r\n" + 
"		return list;" + "\r\n" + 
"	}" + "\r\n" + 
"" + "\r\n" + 
"	// get all table list from databse" + "\r\n" + 
"" + "\r\n" + 
"	public List<String> getListOftable(String table_schema) {" + "\r\n" + 
"		String query = \"SELECT table_name FROM information_schema.tables WHERE table_schema='\" + table_schema + \"' \";" + "\r\n" + 
"" + "\r\n" + 
"		List<String> list = new ArrayList<String>();" + "\r\n" + 
"		try (Connection con = DriverManager.getConnection(url, userName, password);" + "\r\n" + 
"" + "\r\n" + 
"				Statement stmt = con.createStatement()) {" + "\r\n" + 
"			ResultSet rs = stmt.executeQuery(query);" + "\r\n" + 
"			while (rs.next()) {" + "\r\n" + 
"				String coffeeName = rs.getString(\"table_name\");" + "\r\n" + 
"				list.add(coffeeName);" + "\r\n" + 
"			}" + "\r\n" + 
"		} catch (SQLException e) {" + "\r\n" + 
"			e.printStackTrace();" + "\r\n" + 
"		}" + "\r\n" + 
"		return list;" + "\r\n" + 
"	}" + "\r\n" + 
"	" + "\r\n" + 
"	public List<Integer> createdatabase(String table_schema){" + "\r\n" + 
"		String query = \"CREATE SCHEMA \" + table_schema + \";\";" + "\r\n" + 
"" + "\r\n" + 
"		List<Integer> list = new ArrayList<Integer>();" + "\r\n" + 
"		try (Connection con = DriverManager.getConnection(url, userName, password); // conn.str" + "\r\n" + 
"" + "\r\n" + 
"				Statement stmt = con.createStatement()) {" + "\r\n" + 
"			int rs = stmt.executeUpdate(query);" + "\r\n" + 
"" + "\r\n" + 
"			list.add(rs);" + "\r\n" + 
"" + "\r\n" + 
"		} catch (SQLException e) {" + "\r\n" + 
"			e.printStackTrace();" + "\r\n" + 
"		} finally {" + "\r\n" + 
"		}" + "\r\n" + 
"		return list;" + "\r\n" + 
"" + "\r\n" + 
"	}" + "\r\n" + 
"	" + "\r\n" + 
"" + "\r\n" + 
"" + "\r\n" + 
"}" 