/* --------------------------------------------------------------------------------------------------------
 * DATE:	17 Apr 2015
 * AUTHOR:	Cloete A.H
 * PROJECT:	M-Eng, Inteligent geyser M2M system.	
 * ---------------------------------------------------------------------------------------------------------
 * DESCRIPTION: 
 * ---------------------------------------------------------------------------------------------------------
 * PURPOSE: 
 * ---------------------------------------------------------------------------------------------------------
 */

package acza.sun.ee.geyserM2M.webapp.web;

import acza.sun.ee.geyserM2M.webapp.model.SCLhttpClient;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

/**
 * Servlet implementation class GeyserServlet
 */
@WebServlet(description = "Web version of GeyserSense Android application", urlPatterns = { "/GeyserServlet" })
public class GeyserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GeyserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String nip_id = "geyser_udpNIP";
		String container_id = "1234_data";
		String content_uri = "localhost:8080/om2m/nscl/applications/" + nip_id + "/containers/" + container_id + "/contentInstances/latest";
		
		String scl_reply = SCLhttpClient.get(content_uri);
		System.out.println(scl_reply);//Debug reply
		
		request.setAttribute("geyserID", getValueFromJSON("id", scl_reply));
		request.setAttribute("internalTemp", getValueFromJSON("t1", scl_reply));
		request.setAttribute("elementState", getValueFromJSON("e", scl_reply));
		
		RequestDispatcher view = request.getRequestDispatcher("/geyserstatus.jsp");
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
	
	
	private static Object getValueFromJSON(String key, String JSON){

		JSONParser parser=new JSONParser();
		try{
			Object obj = parser.parse(JSON);
			JSONArray array = new JSONArray();
			array.add(obj);	
			JSONObject jobj = (JSONObject)array.get(0);

			return jobj.get(key);

		}catch(ParseException pe){
			System.out.println("position: " + pe.getPosition());
			System.out.println(pe);

			return "error";
		}
	}

}


/*
 * ---------------------------------------------------------------------------------------------------------
 * NOTES:
 * 
 * ---------------------------------------------------------------------------------------------------------
 */
