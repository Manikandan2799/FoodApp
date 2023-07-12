package controller;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import view.DbConnection;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
        System.out.println(username);
		try {
			Connection connection = DbConnection.init();
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE username = ? and password = ?");
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);

			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				response.getWriter().println("Login successful");
			} else {
				response.getWriter().println("Login failed");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
