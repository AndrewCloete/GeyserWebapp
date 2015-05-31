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

import acza.sun.ee.geyserM2M.SCLapi;

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
			System.out.println("Invalid geyser_ID");
		}
		
		//Create new SCL instance that represents the NSCL
		SCLapi nscl = new SCLapi(); //TODO: This will become the singleton in the new webapp
		
		//Retrieve geyser DATA from NSCL
		String json_data  = "{\"e\":\"DUMMY\"}"; //nscl.retrieveLatestContent((long)1234, "DATA");
		nscl.createContainer(geyser_id, "TEST");
		System.out.println("DATA from NSCL: " + json_data);//Debug reply
		
		//Add values to new form update
		request.setAttribute("geyserID", getValueFromJSON("id", json_data));
		request.setAttribute("internalTemp", getValueFromJSON("t1", json_data));
		request.setAttribute("elementState", getValueFromJSON("e", json_data));
		request.setAttribute("geyser_id_box", geyser_id);
		
		
		//--------------------- Read value of desired element mode from form --------------------
		String next_element_state = request.getParameter("element_select");
		String reply_command = "{}";
		try{
			if(next_element_state.equals("ON")){
				reply_command = "{\"e\":\"ON\"}";
				System.out.println("Element on select: " + next_element_state);
			}
			else if (next_element_state.equals("OFF")){
				reply_command = "{\"e\":\"OFF\"}";
				System.out.println("Element off select: " + next_element_state);
			}
			else if (next_element_state.equals("AUTO")){
				reply_command = "{\"e\":\"AUTO\"}";
				System.out.println("Element off select: " + next_element_state);
			}
			else{
				System.out.println("Element none select: " + next_element_state);
			}
		}catch(NullPointerException e){}
		//---------------------------------------------------------------------------------------
		
		//PUT new settings request to NSCL. 
		//NB client must PUT not POST data. This will ensure that a container is not accidentally created
		nscl.updateContentInstance(geyser_id, "SETTINGS", reply_command);
		
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
			System.out.println("position: " + pe.getPosition());
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
