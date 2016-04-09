package main.java.jsonsupport;

public class SuccessJsonModel implements IJsonModel {
	private String status;

	public SuccessJsonModel(String status) {
		super();
		this.status = status;
	}

	public String getStatus() {
		return status;
	}
	
	
}
