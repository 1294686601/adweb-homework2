package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * Servlet implementation class AddMessage
 */
@WebServlet("/AddMessage")
public class AddMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddMessage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		String username = request.getParameter("username");
		String imageURL = request.getParameter("imageURL");
		String message = request.getParameter("message");
		System.out.println(message);
		
		Date date = new Date(); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//可以方便地修改日期格式
		String time = dateFormat.format(date); 
		System.out.println(time);
		
		JSONObject o = new JSONObject();
		try {
			o.put("username", username);
			o.put("imageURL", imageURL);
			o.put("text", message);
			o.put("time", time);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String url = "http://localhost:8080/MyWebServiceDemo/services/messager/addmessage";
		try{
			Client client = Client.create();
			WebResource webResource = client.resource(url);
			ClientResponse clientResponse = webResource.queryParam("message", o.toString()).post(ClientResponse.class);
			
			if (clientResponse.getStatus() != 200){
				System.out.println("Error!");
			}
			
			String output = clientResponse.getEntity(String.class);
			System.out.println(output);
			PrintWriter out = response.getWriter();
			out.print(output);
			out.flush();
			out.close();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
