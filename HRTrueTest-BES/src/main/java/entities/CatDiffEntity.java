package main.java.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="category_difficulty", uniqueConstraints={@UniqueConstraint(columnNames={"category","difficulty"})})
public class CatDiffEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="aa_id")
	int id;
	@Column(name="difficulty", nullable=false)
	byte difficulty;
	@Column(name="category", nullable=false)
	byte category;
	public CatDiffEntity(byte difficulty, byte category) {
		super();
		this.difficulty = difficulty;
		this.category = category;
	}
	public CatDiffEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public byte getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(byte difficulty) {
		this.difficulty = difficulty;
	}
	public byte getCategory() {
		return category;
	}
	public void setCategory(byte category) {
		this.category = category;
	}
	public int getId() {
		return id;
	}
}
