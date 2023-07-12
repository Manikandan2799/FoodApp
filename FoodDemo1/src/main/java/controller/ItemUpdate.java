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


@WebServlet("/ItemUpdate")
public class ItemUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	     throws ServletException, IOException {
		PrintWriter out = response.getWriter();
	    BufferedReader reader = request.getReader();
	    StringBuilder sb = new StringBuilder();
	    String line;
	    while ((line = reader.readLine()) != null) {
	        sb.append(line);
	    }
	    JSONObject requestBody = new JSONObject(sb.toString());
	    int id = requestBody.getInt("id");
	    String itemname = requestBody.getString("item_name");
	    int price = requestBody.getInt("price_rupee");
	    String category = requestBody.getString("category");
	    String availability = requestBody.getString("availability");
        System.out.println(id);
        System.out.println(itemname);
        System.out.println(price);
        System.out.println(category);
        System.out.println(availability);
        System.out.println(requestBody);;
	    JSONObject jsonResponse = new JSONObject();
	    jsonResponse.put("success", true);
	    response.setContentType("application/json");
	    response.getWriter().write(jsonResponse.toString());
	
	try {
		Connection con = DbConnection.init();
		PreparedStatement preparedStatement = con.prepareStatement("update items set item_name = ?,category = ?,price_rupee = ?,availability = ? where id = ? ");
		preparedStatement.setString(1, itemname);
		preparedStatement.setString(2, category);
		preparedStatement.setInt(3, price);
        preparedStatement.setString(4, availability);
        preparedStatement.setInt(5, id);
		preparedStatement.executeUpdate();
		out.println("Item Edited Successfully.");

	} catch (SQLException se) {

		se.printStackTrace();
	} catch (Exception e) {

		e.printStackTrace();
	}

	}

}

