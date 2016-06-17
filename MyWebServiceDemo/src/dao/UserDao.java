package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
	
	private final static String driver = "com.mysql.jdbc.Driver";
	private final static String url = "jdbc:mysql://127.0.0.1:3306/adweb?allowMultiQueries=true&useUnicode=true&amp;"
			+ "characterEncoding=UTF-8&amp;";
	private final static String dbUsername = "root"; 
	private final static String dbPassword = "123456";
	
	static {
		try {
			Class.forName(driver).newInstance();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static UserDao dao = null;

	public static UserDao getInstance() {
		if (dao == null) dao = new UserDao();
		return dao;
	}
	
	public String login(String username, String password) {
		Connection con = null;
		PreparedStatement psm = null;
		ResultSet rs = null;
		
		try {
			con = DriverManager.getConnection(url, dbUsername, dbPassword);
			
			psm = con.prepareStatement("select * from user where user.password = ? ");
			psm.setString(1, password);
			rs = psm.executeQuery();
			while (rs.next()){
				if (rs.getString("username").equals(username)){
					return rs.getString("imageURL");
				}
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null;
	}

	public boolean signup(String username, String password, String imageURL) {
		Connection con = null;
		PreparedStatement psm = null;
		ResultSet rs = null;

		System.out.println(username + " " + password + " " +imageURL);
		try {
			con = DriverManager.getConnection(url, dbUsername, dbPassword);
			
			psm = con.prepareStatement("select * from user where user.username= ? ");
			psm.setString(1, username);
			rs = psm.executeQuery();
			if (rs.next()){
				return false;
			}
			
			psm = con.prepareStatement("insert into user(username, password, imageURL)"
					+ " values (?, ?, ?)");
			psm.setString(1, username);
			psm.setString(2, password);
			psm.setString(3, imageURL);
			psm.execute();
			return true;
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return false;
	}

}
