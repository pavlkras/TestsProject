package main.java.model.dao;

import java.util.List;

public class TemplateData {
	long id;
	String name;
	List<TemplateItemData> items;
	
	
	public TemplateData() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TemplateData(long id, String name, List<TemplateItemData> items) {
		super();
		this.id = id;
		this.name = name;
		this.items = items;
	}
	public long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public List<TemplateItemData> getItems() {
		return items;
	}
	
	
}
