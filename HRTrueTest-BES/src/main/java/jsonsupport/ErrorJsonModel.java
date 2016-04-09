package main.java.jsonsupport;

public class ErrorJsonModel implements IJsonModel {
	private String error = "";

	public ErrorJsonModel(String error) {
		super();
		this.error = error;
	}

	public String getError() {
		return error;
	}
	
}
