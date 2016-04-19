package main.java.model.dao;

public class ActivityTypeData {
	int id;
	String name;
	public ActivityTypeData(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
}
