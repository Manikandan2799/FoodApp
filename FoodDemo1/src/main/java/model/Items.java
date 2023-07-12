package model;

public class Items {
	private int id;
	private String name;
	private String category;
    private int price;
    private String availability;
   
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
	public String getAvailability() {
		return availability;
	}
	public void setAvailability(String availability) {
		this.availability = availability;
	}
	public Items(int id, String name, String category,int price, String availability) {
		
		this.id = id;
		this.name = name;
		this.category= category;
		this.price = price;
		this.availability=availability;
		
	}
	
	

	
	
}
