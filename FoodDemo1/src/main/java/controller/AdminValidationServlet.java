package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import model.Response;
import view.DbConnection;

@WebServlet("/AdminServlet")

public class AdminValidationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out=response.getWriter();
		 response.setContentType("application/json");
	    String username = request.getParameter("username");
	    String password = request.getParameter("password");

	    System.out.println("Username: " + username);
	    System.out.println("Password: " + password);
	    boolean check=false;
	    try {
			Connection con = DbConnection.init();
			PreparedStatement st = con.prepareStatement("SELECT * FROM admin where uname = ? and password=?");
			st.setString(1, username);
			st.setString(2, password);
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				check=true;
				
			Gson gson = new Gson();
		
			String jsonResponse = gson.toJson(new Response("Success", username));
			out.println(jsonResponse);
			System.out.print(jsonResponse);
		}
		
			
	    }catch (SQLException se) {

			se.printStackTrace();
		}
	    if(!check)
	    {
	    	Gson gson = new Gson();
	    	String jsonResponse = gson.toJson(new Response("Failure", username));
			out.println(jsonResponse);
			System.out.print(jsonResponse);
	    }
	}
}
