package main.java.model.dao;

import java.util.List;

public class CategoryData {
	int id;
	String name;
	List<CategoryData> children;
	
	public CategoryData(int id, String name, List<CategoryData> children) {
		super();
		this.id = id;
		this.name = name;
		this.children = children;
	}
	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<CategoryData> getChildren() {
		return children;
	}

}
