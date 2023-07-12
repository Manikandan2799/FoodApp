package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

import view.DbConnection;


@WebServlet("/AddressServlet")
public class AddressServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {

	    String username = request.getParameter("username");

	    try {
	        Connection con = DbConnection.init();
	        String query = "SELECT a.* FROM address a JOIN members m ON a.username = m.uname WHERE m.uname = ?";

	        PreparedStatement pstmt = con.prepareStatement(query);
	        pstmt.setString(1, username);
	        ResultSet rs = pstmt.executeQuery();
	        System.out.println(username);

	        if (rs.next()) {
	        	String name = rs.getString("a.name");
	            String city = rs.getString("a.city");
	            String address = rs.getString("a.address");
	            String zip = rs.getString("a.zip");
	      
	            
	            JsonObject addressJson = new JsonObject();
	            addressJson.addProperty("username", username);
	            addressJson.addProperty("name", name);
	            addressJson.addProperty("city", city);
	            addressJson.addProperty("address", address);
	            addressJson.addProperty("zip", zip);
	         
	            System.out.println(addressJson);

	            response.setContentType("application/json");
	            response.setCharacterEncoding("UTF-8");
	            response.getWriter().write(addressJson.toString());
	        } else {
	            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
	        }

	        con.close();

	    } catch (SQLException e) {
	        e.printStackTrace();
	        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	    }
	}


	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String name = request.getParameter("name");
		String city = request.getParameter("city");
		String address = request.getParameter("address");
		String zip = request.getParameter("zip");

		

		try {
			
Connection con = DbConnection.init();
String query = "INSERT INTO address (name, city, address, zip) VALUES (?, ?, ?, ?)";

			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setString(1, name);
			pstmt.setString(2, city);
			pstmt.setString(3, address);
			pstmt.setString(4, zip);

			int count = pstmt.executeUpdate();

			if (count > 0) {
				response.setContentType("application/json");
				PrintWriter out = response.getWriter();
				out.println("Address added successfully!");
			} else {
				response.setContentType("application/json");
				PrintWriter out = response.getWriter();
				out.println("Failed to add address!");
			}

			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
