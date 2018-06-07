package com.medicine.model.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtil {
	private static String DRIVERNAME = null;
	private static String USERNAME = null;
	private static String PASSWORD = null;
	private static String URL = null;
	
	static {
		Properties properties = new Properties();
		FileReader reader = null;
		try {
			reader = new FileReader("./config/db.properties");
			properties.load(reader);
			
			DRIVERNAME = properties.getProperty("database_driver");
			URL = properties.getProperty("database_url");
			USERNAME = properties.getProperty("username");
			PASSWORD = properties.getProperty("password");
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(reader != null)
					reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private Connection con = null;
	private PreparedStatement preState = null;
	private ResultSet resultSet = null;
	
	public int updateExe(String sql, String[] parse) {
		int result = 0;
		try {
			// 1.加载驱动(将需要的驱动程序加入内存).
			Class.forName(DRIVERNAME);

			// 2.根据 IP, port, dbName 指定连接到数据库.
			this.con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			preState = this.con.prepareStatement(sql);
			if(parse != null) {
				for (int i = 0; i < parse.length; i++) {
					preState.setString(i + 1, parse[i]);
				}
			}

			result = preState.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			this.closeConnection();
		}
		return result;
	}

	public ResultSet queryExe(String sql, String[] parse) {
		try {
			// 1.加载驱动(将需要的驱动程序加入内存).
			Class.forName(DRIVERNAME);

			// 2.根据 IP, port, dbName 指定连接到数据库.
			this.con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			preState = this.con.prepareStatement(sql);
			if(parse != null) {
				for (int i = 0; i < parse.length; i++) {
					preState.setString(i + 1, parse[i]);
				}
			}

			resultSet = preState.executeQuery();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return this.resultSet;
	}

	public void closeConnection() {
		try {
			if (this.resultSet != null) {
				this.resultSet.close();
				this.resultSet = null;
			}
			if (this.preState != null) {
				this.preState.close();
				this.preState = null;
			}
			if (this.con != null) {
				this.con.close();
				this.con = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
