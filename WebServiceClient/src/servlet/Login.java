package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
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
		String password = request.getParameter("password");
		
		JSONObject o = new JSONObject();
		try {
			o.put("username", username);
			o.put("password", password);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String url = "http://localhost:8080/MyWebServiceDemo/services/user/login";
		try{
			Client client = Client.create();
			WebResource webResource = client.resource(url);
			ClientResponse clientResponse = webResource.queryParam("message", o.toString()).post(ClientResponse.class);
			
			if (clientResponse.getStatus() != 200){
				System.out.println("Login Error!");
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
