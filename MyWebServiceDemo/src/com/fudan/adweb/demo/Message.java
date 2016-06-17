package com.fudan.adweb.demo;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.json.JSONException;
import org.json.JSONObject;

import dao.MessageDao;

@Produces("text/html")
@Consumes("text/html")
@Path("message")
public class Message {

	@POST
	@Path("addmessage")
	@Produces("text/html")
	@Consumes("text/html")
	public String addMessage(@QueryParam("message") String message) {
		System.out.println(message);
		MessageDao dao = MessageDao.getInstance();
		try {
			JSONObject o = new JSONObject(message);
			String username = o.getString("username");
			String imageURL = o.getString("imageURL");
			String content = o.getString("content");
			String time = o.getString("time");
			boolean f = dao.addMessage(username, imageURL, content, time);
			if (f) return "Success";
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Failed";
	}

	@GET
	@Path("getallmessage")
	@Produces("text/html")
	@Consumes("text/html")
	public String getAllMessage(@QueryParam("message") String message) {
		System.out.println(message);
		MessageDao dao = MessageDao.getInstance();
		String result = dao.getAllMessage();
		System.out.println(result);
		return result;
	}

	@DELETE
	@Path("deletemessage")
	@Produces("text/html")
	@Consumes("text/html")
	public String deleteMessage(@QueryParam("message") String message) {
		System.out.println(message);
		int messageID = Integer.parseInt(message);
		MessageDao dao = MessageDao.getInstance();
		boolean f = dao.deleteMessage(messageID);
		if (f) return "Success";
		return "Failed";
	}
}