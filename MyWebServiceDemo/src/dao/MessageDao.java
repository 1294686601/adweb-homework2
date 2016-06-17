package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MessageDao {
	
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
	
	public static MessageDao dao = null;

	public static MessageDao getInstance() {
		if (dao == null) dao = new MessageDao();
		return dao;
	}
	
	public boolean addMessage(String username, String imageURL, String content, String time) {
		Connection con = null;
		PreparedStatement psm = null;

		try {
			con = DriverManager.getConnection(url, dbUsername, dbPassword);
			
			psm = con.prepareStatement("insert into message(username, imageURL, content, time, usable)"
					+ " values (?, ?, ?, ?, ?)");
			psm.setString(1, username);
			psm.setString(2, imageURL);
			psm.setString(3, content);
			psm.setString(4, time);
			psm.setString(5, "1");
			psm.execute();
			return true;
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return false;
	}
	
	public boolean deleteMessage(int messageID) {
		Connection con = null;
		PreparedStatement psm = null;
		Statement sm = null;
		ResultSet rs = null;
		
		try {
			con = DriverManager.getConnection(url, dbUsername, dbPassword);
			
			psm = con.prepareStatement("select * from message where message.messageID= ? ");
			psm.setInt(1, messageID);
			rs = psm.executeQuery();
			if (rs.next()){
				sm = con.createStatement();
				String command = "update message set usable=0 where messageID = " + messageID;
				sm.executeUpdate(command);
				return true;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return false;
	}
	
	public String getAllMessage(){
		Connection con = null;
		PreparedStatement psm = null;
		ResultSet rs = null;
		
		JSONArray arr = new JSONArray();
		
		try {
			con = DriverManager.getConnection(url, dbUsername, dbPassword);
			
			psm = con.prepareStatement("select * from message where usable = 0 order by time desc");
			rs = psm.executeQuery();
			while (rs.next()){
				JSONObject o = new JSONObject();
				o.put("username", rs.getString("username"));
				o.put("image", rs.getString("imageURL"));
				o.put("messageID", rs.getInt("messageID"));
				o.put("content", rs.getString("content"));
				o.put("time", rs.getString("time"));
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return arr.toString();
	}


}
