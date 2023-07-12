package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import model.Items;
import view.ItemDAO;


@WebServlet("/ItemServlet")
public class ItemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public ItemDAO itemDAO= new ItemDAO();  
   

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    	PrintWriter out=response.getWriter();
	    BufferedReader reader = request.getReader();
	    StringBuilder sb = new StringBuilder();
	    String line;
	    while ((line = reader.readLine()) != null) {
	        sb.append(line);
	    }
	    JSONObject requestBody = new JSONObject(sb.toString());
	    int id = requestBody.getInt("id");
	    String name = requestBody.getString("item_name");
	    String category = requestBody.getString("category");
	    int rupee = requestBody.getInt("rupee");
	    String availability = requestBody.getString("availability");
        System.out.println(id);
        System.out.println(name);
	    JSONObject jsonResponse = new JSONObject();
	    jsonResponse.put("success", true);
	    response.setContentType("application/json");
	    response.getWriter().write(jsonResponse.toString());
	    out.println("Item Saved Successfully");

    		        Items item = new Items(id, name, category, rupee,availability);
    		        try {
						itemDAO.updateItem(item);
					} catch (SQLException e) {
						
						e.printStackTrace();
					}
    		       
    		    }
      
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");

        try {
            switch (action) {
                case "/new":
                    showNewForm(request, response);
                    break;
                case "/insert":
                    insertItem(request, response);
                    break;
                case "/delete":
                    deleteItem(request, response);
                    break;
                case "/edit":
                    showEditForm(request, response);
                    break;
                case "/update":
                    updateItem(request, response);
                    break;
                default:
                    listItem(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
	}
	private void listItem(HttpServletRequest request, HttpServletResponse response)
		    throws SQLException, IOException, ServletException {
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		
		        JSONArray listItem =itemDAO.selectAllItems() ;
		        System.out.println(listItem);
		        out.println(listItem);

   }

		    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
		    throws ServletException, IOException {
		       
		    }

		    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
		    throws SQLException, ServletException, IOException {
				response.setContentType("application/json");

				PrintWriter out = response.getWriter();

		        int id = Integer.parseInt(request.getParameter("id"));
		        JSONObject existingItem =itemDAO.selectItem(id);
		        out.println(existingItem);
		      

		    }
		    private void updateItem(HttpServletRequest request, HttpServletResponse response)
				    throws SQLException, IOException {
				        int id = Integer.parseInt(request.getParameter("id"));
				        String name = request.getParameter("name");
				        String category = request.getParameter("category");
		                int rupee = Integer.parseInt(request.getParameter("rupee"));
				        String availability = request.getParameter("availability");

				        Items item = new Items(id, name, category, rupee,availability);
				        itemDAO.updateItem(item);
				        
				    }

		    private void insertItem(HttpServletRequest request, HttpServletResponse response)
		    throws SQLException, IOException {
		    	int id=Integer.parseInt(request.getParameter("id"));
		        String name = request.getParameter("name");
		        String category = request.getParameter("category");
                int rupee=Integer.parseInt(request.getParameter("rupee"));
                String availability=request.getParameter("availability");
                

		        Items newItem = new Items(id,name, category, rupee,availability);
		        itemDAO.insertItem(newItem);
		    }

		   

		    private void deleteItem(HttpServletRequest request, HttpServletResponse response)
		    throws SQLException, IOException {
		        int id = Integer.parseInt(request.getParameter("id"));
		        itemDAO.deleteItem(id);

		    }



}
