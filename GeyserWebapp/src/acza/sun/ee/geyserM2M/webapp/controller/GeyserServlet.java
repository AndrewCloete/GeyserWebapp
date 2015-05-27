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
		
		final String NIP_ID = "geyser_udpNIP";
		String geyser_id = request.getParameter("geyser_id_box");
		String data_container_id = geyser_id + "_data";
		String control_container_id = geyser_id + "_control_settings";
		String data_container_uri = "localhost:8080/om2m/nscl/applications/" + NIP_ID + "/containers/" + data_container_id + "/contentInstances/latest";
		String control_container_uri = "localhost:8080/om2m/nscl/applications/" + NIP_ID + "/containers/" + control_container_id + "/contentInstances";
		
		String scl_reply = SCLhttpClient.get(data_container_uri);
		System.out.println(scl_reply);//Debug reply
		System.out.println(data_container_id);

		request.setAttribute("geyserID", getValueFromJSON("id", scl_reply));
		request.setAttribute("internalTemp", getValueFromJSON("t1", scl_reply));
		request.setAttribute("elementState", getValueFromJSON("e", scl_reply));
		request.setAttribute("geyser_id_box", geyser_id);
		
		String next_element_state = request.getParameter("element_select");

		try{
			if(next_element_state.equals("ON")){
				SCLhttpClient.post(control_container_uri, "{\"e\":\"ON\"}");
				System.out.println("Element on select: " + next_element_state);
			}
			else if (next_element_state.equals("OFF")){
				SCLhttpClient.post(control_container_uri, "{\"e\":\"OFF\"}");
				System.out.println("Element off select: " + next_element_state);
			}
			else if (next_element_state.equals("AUTO")){
				SCLhttpClient.post(control_container_uri, "{\"e\":\"AUTO\"}");
				System.out.println("Element off select: " + next_element_state);
			}
			else{
				System.out.println("Element none select: " + next_element_state);
			}
		}catch(NullPointerException e){}
		
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
