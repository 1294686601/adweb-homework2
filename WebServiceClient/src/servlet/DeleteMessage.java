package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * Servlet implementation class DeleteMessage
 */
@WebServlet("/DeleteMessage")
public class DeleteMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteMessage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		String messageID = request.getParameter("messageID");
		System.out.println(messageID); 
		
		String url = "http://localhost:8080/MyWebServiceDemo/services/messager/deletemessage";
		try{
			Client client = Client.create();
			WebResource webResource = client.resource(url);
			ClientResponse clientResponse = webResource.queryParam("message", messageID).post(ClientResponse.class);
			
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
