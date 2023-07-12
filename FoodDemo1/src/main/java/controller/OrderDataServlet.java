package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import view.DbConnection;

@WebServlet("/getOrdersData")
public class OrderDataServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    try {
      conn = DbConnection.init();
  
      stmt = conn.prepareStatement("SELECT item, SUM(quantity) AS quantity FROM orders GROUP BY item");

      rs = stmt.executeQuery();

      JSONArray ordersArray = new JSONArray();
      while (rs.next()) {
        JSONObject order = new JSONObject();
        order.put("item", rs.getString("item"));
        order.put("quantity", rs.getInt("quantity"));
        ordersArray.put(order);
      }
      
      JSONObject responseObj = new JSONObject();
      responseObj.put("success", true);
      responseObj.put("orders", ordersArray);
      System.out.println(ordersArray);
      
      response.setContentType("application/json");
      response.setCharacterEncoding("UTF-8");
      response.getWriter().write(responseObj.toString());
    }
    catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try { if (stmt != null) stmt.close(); } catch (Exception e) {}
      try { if (conn != null) conn.close(); } catch (Exception e) {}
    }
  }
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 
		  BufferedReader reader = request.getReader();
		  StringBuilder stringBuilder = new StringBuilder();
		  String line = null;
		  while ((line = reader.readLine()) != null) {
		    stringBuilder.append(line);
		  }
		  reader.close();
		  String data = stringBuilder.toString();

		  JSONObject jsonObject = new JSONObject(data);
		  String username = jsonObject.getString("username");
		  JSONArray selectedItems = jsonObject.getJSONArray("selectedItems");
        System.out.println(username);
        System.out.println(selectedItems);
		 
		  Connection conn = null;
		  PreparedStatement stmt = null;
		  try {
			  conn = DbConnection.init();
		    stmt = conn.prepareStatement("INSERT INTO orders (order_id,username,id, item,price,quantity,order_date) VALUES (?,?,?,?,?,?,?)");
		    stmt.setString(2, username);
		    for (int i = 0; i < selectedItems.length(); i++) {
		    	JSONObject selectedItem = selectedItems.getJSONObject(i);
		    	stmt.setInt(3, selectedItem.getInt("id"));
		        stmt.setString(4, selectedItem.getString("item_name"));
		        stmt.setInt(5, selectedItem.getInt("price_rupee"));
		        stmt.setInt(6, selectedItem.getInt("quantity"));
		        stmt.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
		        int orderIdSuffix = 0;
		        String orderId = "";
		        boolean orderCreated = false;
		        while (!orderCreated) {
		            orderIdSuffix++;
		            orderId = "SBB" + String.format("%05d", orderIdSuffix);
		           
		            PreparedStatement checkStmt = conn.prepareStatement("SELECT COUNT(*) FROM orders WHERE order_id = ?");
		            checkStmt.setString(1, orderId);
		            ResultSet rs = checkStmt.executeQuery();
		            if (rs.next() && rs.getInt(1) == 0) {
		                orderCreated = true;
		            }
		            checkStmt.close();
		        }
		        stmt.setString(1, orderId);
		        stmt.executeUpdate();
		    }
		      
		  } catch (SQLException e) {
		    e.printStackTrace();
		  } finally {
		    try { if (stmt != null) stmt.close(); } catch (Exception e) {}
		    try { if (conn != null) conn.close(); } catch (Exception e) {}
		  }

		  response.setContentType("application/json");
		  response.setCharacterEncoding("UTF-8");
		  response.getWriter().write("{ \"success\": true }");
		}


}


