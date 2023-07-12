package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import view.DbConnection;


@WebServlet("/AddNewItem")
public class AddNewItem extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out=response.getWriter();
	    BufferedReader reader = request.getReader();
	    StringBuilder sb = new StringBuilder();
	    String line;
	    while ((line = reader.readLine()) != null) {
	        sb.append(line);
	    }
	    JSONObject requestBody = new JSONObject(sb.toString());
	    int id = requestBody.getInt("id");
	    String itemName = requestBody.getString("name");
	    String category = requestBody.getString("category");
	    int priceRupee = requestBody.getInt("price");
	    String availability = requestBody.getString("availability");
      System.out.println(requestBody);
	    JSONObject jsonResponse = new JSONObject();
	    jsonResponse.put("success", true);
	    response.setContentType("application/json");
	    response.getWriter().write(jsonResponse.toString());
	
	    try {
	        Connection con = DbConnection.init();
	        PreparedStatement st = con.prepareStatement("INSERT INTO items (id,item_name, category, price_rupee, availability) VALUES (?, ?, ?, ?,?)");
	        st.setInt(1, id);
	        st.setString(2, itemName);
	        st.setString(3, category);
	        st.setInt(4, priceRupee);
	        st.setString(5, availability);
	        int rowsInserted = st.executeUpdate();
	        if (rowsInserted > 0) {
	          jsonResponse.put("success", true);
	          out.println(jsonResponse);
	        } else {
	          jsonResponse.put("success", false);
	          out.println(jsonResponse);
	        }
	      } catch (SQLException se) {
	        se.printStackTrace();
	        jsonResponse.put("success", false);
	        jsonResponse.put("message", "Database error: " + se.getMessage());
	        out.println(jsonResponse);
	      } catch (Exception e) {
	        e.printStackTrace();
	        jsonResponse.put("success", false);
	        jsonResponse.put("message", "Server error: " + e.getMessage());
	        out.println(jsonResponse);
	      }
	    }


}