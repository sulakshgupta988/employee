package com.increff.employee;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class EmployeeApi {

	private static Logger logger = Logger.getLogger(EmployeeApi.class);
	private Connection con;

	public static void main(String args[]) throws ClassNotFoundException, SQLException, IOException {
		// Remember to delete all the rows in the db
		// before running this program

	}

	EmployeeApi() throws Exception {
		BasicConfigurator.configure();

		Properties props = new Properties();
		InputStream inStream = new FileInputStream("employee.properties");
		props.load(inStream);
		Class.forName(props.getProperty("jdbc.driver"));
		con = DriverManager.getConnection(props.getProperty("jdbc.url"), props.getProperty("jdbc.username"),
				props.getProperty("jdbc.password"));
	}

	public void insert() throws SQLException {
		logger.error("\nInserting Employees...");
		Statement stat = con.createStatement();

		for (int i = 0; i < 3; i++) {
			int id = 10 + i;
			String name = "Name" + i;
			int age = 30 + i;
			logger.info("insert into employee values(" + id + ", '" + name + "'," + age + ")");
			stat.executeUpdate("insert into employee values(" + id + ",'" + name + "'," + age + ")");
		}
		stat.close();
	}

	public ResultSet select() throws SQLException {
		logger.info("Selecting Employees!!");
		Statement stat = con.createStatement();
		ResultSet rs = stat.executeQuery("Select * from employee");
		/*
		 * while (rs.next()) { logger.info(rs.getInt(1) + " " + rs.getString(2) + " " +
		 * rs.getInt(3)); }
		 */
		return rs;
		
	}

	public void delete() throws SQLException {
		logger.info("Deleting Employees...");
		Statement stat = con.createStatement();
		ResultSet rs = stat.executeQuery("select * from employee");
		List<Integer> idList = new ArrayList<Integer>();
		while (rs.next()) {
			idList.add(rs.getInt(1));
		}
		for (int i = 0; i < idList.size(); i++) {
			stat.executeUpdate("delete from employee where id =" + idList.get(i));
		}
		stat.close();
	}
}
