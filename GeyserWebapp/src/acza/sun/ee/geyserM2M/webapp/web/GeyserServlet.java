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

import org.apache.commons.codec.binary.Base64;

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
		
		final String GEYSER_ID = "sim_geyser_1";
		final String CONTAINER_ID = "DATA";
		final String CONTENT_URI = "localhost:8181/om2m/gscl/applications/" + GEYSER_ID + "/containers/" + CONTAINER_ID + "/contentInstances/latest";
		
		String content64 = SCLhttpClient.get(CONTENT_URI);
		//System.out.println(content64);//Debug reply
		
		String decoded = new String(Base64.decodeBase64(content64.getBytes()));
		//System.out.println(decoded);
		
		request.setAttribute("geyserID", SCLhttpClient.parseInstanceContent(decoded, "appId"));
		request.setAttribute("internalTemp", SCLhttpClient.parseInstanceContent(decoded, "Internal Temperature"));
		request.setAttribute("elementState", SCLhttpClient.parseInstanceContent(decoded, "ElementState"));
		
		RequestDispatcher view = request.getRequestDispatcher("/geyserstatus.jsp");
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}


/*
 * ---------------------------------------------------------------------------------------------------------
 * NOTES:
 * 
 * ---------------------------------------------------------------------------------------------------------
 */
