package main.java.model.dao;

public class TemplateItemData {
	long id;
	byte difficulty;
	byte amount;
	byte category;
	
	
	public TemplateItemData() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TemplateItemData(long id, byte difficulty, byte amount, byte category) {
		super();
		this.id = id;
		this.difficulty = difficulty;
		this.amount = amount;
		this.category = category;
	}

	public long getId() {
		return id;
	}

	public byte getDifficulty() {
		return difficulty;
	}

	public byte getAmount() {
		return amount;
	}

	public byte getCategory() {
		return category;
	}

	public void setAmount(byte amount) {
		this.amount = amount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + category;
		result = prime * result + difficulty;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TemplateItemData other = (TemplateItemData) obj;
		if (category != other.category)
			return false;
		if (difficulty != other.difficulty)
			return false;
		return true;
	}
}
