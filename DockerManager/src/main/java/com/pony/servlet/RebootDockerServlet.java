package com.pony.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import com.pony.docker.RemoteApi;
import com.pony.utils.JsonConvert;
import com.pony.vo.DockerContainer;


/**
 * Servlet implementation class RebootDockerServlet
 */
public class RebootDockerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RebootDockerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		String id = "5a3a1b4b3e78";
//		HttpClient client = new HttpClient();
//		PostMethod post = new PostMethod("http://schoolproject.cloudapp.net:2375/containers/" + id + "/start");
//		int statusCode = client.executeMethod(post);
//		String res = "code " + statusCode;
//		post.releaseConnection();
		
		//get need reboot container ip
		String ip = request.getParameter("rebIP");
		
		try {
			String contJson = RemoteApi.listContainer();
			DockerContainer[] containers = JsonConvert.toDcokerContainer(contJson);
			for(DockerContainer cont : containers){
				if(ip.equals(cont.getIp())){
					RemoteApi.stopContainer(cont.getId());
					RemoteApi.removeContainer(cont.getId());
					String newContID = RemoteApi.createContainer("opony/school:server");
					RemoteApi.startContainer(newContID);
					break;
				}
					
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
