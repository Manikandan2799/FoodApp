package view;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;


import model.Items;

public class ItemDAO {
	private static final String INSERT_ITEMS_SQL = "INSERT INTO items VALUES(?, ?, ?,?,?,?)";
	private static final String SELECT_ITEMS_BY_ID = "select id,item_name,category,price_rupee,availability from items where id =?";
	private static final String SELECT_ALL_ITEMS = "select * from items";
	private static final String DELETE_ITEMS_SQL = "delete from items where id = ?";
	private static final String UPDATE_ITEMS_SQL = "update items set item_name = ?,category = ?,price_rupee = ?,availability = ? where id = ?";

	public void insertItem(Items item) throws SQLException {
		try (Connection connection = DbConnection.init();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ITEMS_SQL)) {
			preparedStatement.setInt(1, item.getId());
			preparedStatement.setString(2, item.getName());
			preparedStatement.setString(3, item.getCategory());
            preparedStatement.setInt(4, item.getPrice());
            preparedStatement.setString(5, item.getAvailability());
            
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			
			printSQLException(e);
		}
	}

	public JSONObject selectItem(int id) {
		try (Connection connection = DbConnection.init();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ITEMS_BY_ID);) {
			preparedStatement.setInt(1, id);
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				JSONObject json = new JSONObject();
			    json.put("id", rs.getInt("id"));
			    json.put("item_name", rs.getString("item_name"));
			    json.put("category", rs.getString("category"));
			    json.put("price_rupee", rs.getString("price_rupee"));
			    json.put("availability", rs.getString("availability"));
			    json.put("Image", rs.getString("Image"));
			    return json;
			}
		} catch (SQLException e) {
			printSQLException(e);
			return null;
		}
		return null;
		
	}

	public JSONArray selectAllItems() {

		
		 JSONArray dataArray = new JSONArray();
		try (Connection connection = DbConnection.init();

		PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_ITEMS);) {
	    ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
              JSONObject data = new JSONObject();
              data.put("id", rs.getInt("id"));
              data.put("item_name", rs.getString("item_name"));
              data.put("category", rs.getString("category"));
              data.put("price_rupee", rs.getString("price_rupee"));
              data.put("availability", rs.getString("availability"));
              data.put("Image", rs.getString("Image"));
              dataArray.put(data);
            }
          } catch (SQLException e) {
            e.printStackTrace();
				
            
			}
		return dataArray;
	}
	

	public boolean deleteItem(int id) throws SQLException {
		boolean rowDeleted;
		try (Connection connection = DbConnection.init();
				PreparedStatement statement = connection.prepareStatement(DELETE_ITEMS_SQL);) {
			statement.setInt(1, id);
			rowDeleted = statement.executeUpdate() > 0;
		}
		return rowDeleted;
	}

	public boolean updateItem(Items item) throws SQLException {
		boolean rowUpdated;
		try (Connection connection = DbConnection.init();
				PreparedStatement statement = connection.prepareStatement(UPDATE_ITEMS_SQL);) {
			statement.setString(1, item.getName());
			statement.setString(2, item.getCategory());
            statement.setInt(3, item.getPrice());
			statement.setInt(4, item.getId());
			statement.setString(5, item.getAvailability());
			rowUpdated = statement.executeUpdate() > 0;
		}
		return rowUpdated;
	}

	private void printSQLException(SQLException ex) {
		for (Throwable e : ex) {
			if (e instanceof SQLException) {
				e.printStackTrace(System.err);
				System.err.println("SQLState: " + ((SQLException) e).getSQLState());
				System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
				System.err.println("Message: " + e.getMessage());
				Throwable t = ex.getCause();
				while (t != null) {
					System.out.println("Cause: " + t);
					t = t.getCause();
				}
			}
		}
	}

}
