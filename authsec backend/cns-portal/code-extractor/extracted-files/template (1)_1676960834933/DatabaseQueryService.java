package com.realnet.template.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class DatabaseQueryService {

	@Value("${spring.datasource.username}")
	private String userName;

	@Value("${spring.datasource.password}")
	private String password;

	@Value("${spring.datasource.url}")
	private String url;

	// get all column

	public List<String> getColumnAliasList1(String table_schema, String tABLE_NAME) {
		String query = "SELECT column_name FROM information_schema.columns WHERE TABLE_SCHEMA='" + table_schema
				+ "' and table_name = '" + tABLE_NAME + "' ";
		List<String> list = new ArrayList<String>();
		try (Connection con = DriverManager.getConnection(url, userName, password);

				Statement stmt = con.createStatement()) {
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String coffeeName = rs.getString("column_name");
				list.add(coffeeName);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// get all table list from databse

	public List<String> getListOftable(String table_schema) {
		String query = "SELECT table_name FROM information_schema.tables WHERE table_schema='" + table_schema + "' ";

		List<String> list = new ArrayList<String>();
		try (Connection con = DriverManager.getConnection(url, userName, password);

				Statement stmt = con.createStatement()) {
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String coffeeName = rs.getString("table_name");
				list.add(coffeeName);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<Integer> createdatabase(String table_schema){
		String query = "CREATE SCHEMA " + table_schema + ";";

		List<Integer> list = new ArrayList<Integer>();
		try (Connection con = DriverManager.getConnection(url, userName, password); // conn.str

				Statement stmt = con.createStatement()) {
			int rs = stmt.executeUpdate(query);

			list.add(rs);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		}
		return list;

	}
	


}
