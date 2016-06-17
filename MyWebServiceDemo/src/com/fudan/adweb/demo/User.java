package com.fudan.adweb.demo;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.json.JSONException;
import org.json.JSONObject;

import dao.UserDao;

@Produces("text/html")
@Consumes("text/html")
@Path("user")
public class User {

	@POST
	@Path("login")
	@Produces("text/html")
	@Consumes("text/html")
	public String login(@QueryParam("message") String message) {
		System.out.println(message);
		UserDao dao = UserDao.getInstance();
		try {
			JSONObject o = new JSONObject();
			String username = o.getString("username");
			String password = o.getString("password");
			String imageURL = dao.login(username, password);
			return imageURL;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "Failed";
	}

	@POST
	@Path("signup")
	@Produces("text/html")
	@Consumes("text/html")
	public String signup(@QueryParam("message") String message) {
		UserDao dao = UserDao.getInstance();
		try {
			JSONObject o = new JSONObject(message);
			String imageURL = o.getString("imageURL");
			String username = o.getString("username");
			String password = o.getString("password");
			boolean f = dao.signup(username, password, imageURL);
			if (f) return imageURL;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Failed";
	}

	@PUT
	@Path("reset")
	@Produces("text/html")
	@Consumes("text/html")
	public String reset(@QueryParam("message") String message) {
		UserDao dao = UserDao.getInstance();
		try {
			JSONObject o = new JSONObject(message);
			String imageURL = o.getString("imageURL");
			String username = o.getString("username");
			String password = o.getString("password");
			boolean f = dao.signup(username, password, imageURL);
			if (f) return "Success";
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Failed";
	}

	@GET
	@Path("getsimilarity")
	@Produces("text/html")
	@Consumes("text/html")
	public String getSimilarity(@QueryParam("message") String message) {
		String result = null;
		try {
			JSONObject o = new JSONObject(message);
			String username1 = o.getString("username1");
			String username2 = o.getString("username2");
			
			//result = getSimilarity(username1, username2);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}