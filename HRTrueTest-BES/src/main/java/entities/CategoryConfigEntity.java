package main.java.entities;

public class CategoryConfigEntity {
	int id;
	String name;
	CategoryConfigEntity parent;
	
	public CategoryConfigEntity(int id, String name, CategoryConfigEntity parent) {
		super();
		this.id = id;
		this.name = name;
		this.parent = parent;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public CategoryConfigEntity getParent() {
		return parent;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		CategoryConfigEntity other = (CategoryConfigEntity) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
}
