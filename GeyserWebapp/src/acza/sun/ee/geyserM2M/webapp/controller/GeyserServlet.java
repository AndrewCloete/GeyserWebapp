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

package acza.sun.ee.geyserM2M.webapp.controller;

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
		System.out.println("Hello GET");//Debug reply
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		long geyser_id = (long)0000; //Default id; TODO: Next version, find a better way to handle this
		try{
			geyser_id = new Long(request.getParameter("geyser_id_box"));
		}catch(NumberFormatException e){
			System.out.println("Invalid geyser_ID.");
		}
		
		String application_id = "geyser_"+geyser_id;
		String data_container_uri = "localhost:8080/om2m/nscl/applications/" + application_id + "/containers/DATA/contentInstances/latest";
		String control_container_uri = "localhost:8080/om2m/nscl/applications/" + application_id + "/containers/SETTINGS/contentInstances";
		
		String scl_reply = SCLhttpClient.get(data_container_uri);
		System.out.println("data_container_uri: " + data_container_uri);//Debug reply

		request.setAttribute("geyserID", getValueFromJSON("id", scl_reply));
		request.setAttribute("internalTemp", getValueFromJSON("t1", scl_reply));
		request.setAttribute("elementState", getValueFromJSON("e", scl_reply));
		request.setAttribute("geyser_id_box", geyser_id);
		
		
		String next_element_state = request.getParameter("element_select");
		try{
		//If a new element mode is selected
			if(!next_element_state.equals("-")){
				SCLhttpClient.post(control_container_uri, "{\"e\":\""+ next_element_state +"\"}");
				System.out.println("Element on select: " + next_element_state);
			}
		}catch(NullPointerException e){}
		//Update view
		RequestDispatcher view = request.getRequestDispatcher("/geyserstatus.jsp");
		view.forward(request, response);
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
			System.out.println(pe);

			return "Parse error";
		}catch(NullPointerException e){
			return "Unavailable";
		}
	}

}


/*
 * ---------------------------------------------------------------------------------------------------------
 * NOTES:
 * 
 * ---------------------------------------------------------------------------------------------------------
 */
