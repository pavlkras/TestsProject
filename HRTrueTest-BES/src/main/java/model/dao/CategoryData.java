package main.java.model.dao;

public class CategoryData {
	int id;
	String name;
	
	public CategoryData() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CategoryData(int id2, String name) {
		super();
		this.id = id2;
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	public void setId(byte id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
