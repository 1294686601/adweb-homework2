package servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONException;
import org.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * Servlet implementation class Signup
 */
@WebServlet("/Signup")
public class Signup extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Signup() {
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
		
		String username = null;
		String password = null;
		String imageURL = null;
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setFileSizeMax(1024*1024*4);
		
		String path = getServletContext().getRealPath("/");
		File folder = new File(path + "pic/");
		if (!folder.exists()){
			folder.mkdirs();
		}
		String time = String.valueOf(System.currentTimeMillis());
		
		try {
			List<FileItem> fileItems = upload.parseRequest(request);
			int size = fileItems.size();
			//System.out.println(size);
			for (int i = 0; i < size; i++){
				FileItem item = fileItems.get(i);
				if (item.isFormField()){
					String name = item.getFieldName();
					String value = item.getString();
					if (name.equals("username")) username = value;
					if (name.equals("password")) password = value;
					if (name.equals("image")){
						imageURL = value;
						if (imageURL.equals("default")){
							imageURL = "img/user.jpg";
							break;
						}
					}
				} else {
					File file = new File(folder.getAbsoluteFile()+ "\\" + time + ".jpg");
					item.write(file);
					String tmp = file.getAbsolutePath();
					imageURL = tmp.substring(tmp.lastIndexOf('\\') + 1);
					System.out.println(tmp);
				}
			}		
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JSONObject o = new JSONObject();
		try {
			o.put("username", username);
			o.put("password", password);
			o.put("imageURL", imageURL);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String url = "http://localhost:8080/MyWebServiceDemo/services/user/signup";
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
