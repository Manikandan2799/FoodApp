package controller;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import view.DbConnection;

@WebServlet("/myOrdersData")
public class MyOrdersServlet extends HttpServlet {
  
	private static final long serialVersionUID = 1L;

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        System.out.println(username);
        String sql = "SELECT * FROM orders WHERE username=?";
        try (Connection conn = DbConnection.init();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            JsonArray orders = new JsonArray();
            while (rs.next()) {
                JsonObject order = new JsonObject();
                order.addProperty("orderId", rs.getString("order_id"));
                order.addProperty("item", rs.getString("item"));
                order.addProperty("quantity", rs.getInt("quantity"));
                int quantity = rs.getInt("quantity");
                int price = rs.getInt("price");
                order.addProperty("totalPrice", quantity * price);
                order.addProperty("orderDate", rs.getTimestamp("order_date").toString());

                orders.add(order);
                System.out.println(order);
            }
            JsonObject result = new JsonObject();
            result.add("orders", orders);
            result.addProperty("success", true);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            System.out.println(result);
            out.print(result.toString());
            out.flush();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.print("Error: " + ex.getMessage());
            out.flush();
        }
    }
}
