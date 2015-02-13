package tel_ran.tests.services;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
@Entity
public class EntityQuestion implements Serializable {
	private static final long serialVersionUID = 1L;
	// constructor by default
	public EntityQuestion() {	}
	//column 1 id (long)
	@Id
	@Column(name="ID")
	@GeneratedValue	
	private long id;
	// column 2 question text	
	@Column(name="QUESTIONTEXT",unique = true, nullable = false, length = 500)
	private String questionText;
	//column 3 description text
	@Column(name="DESCRIPTION",unique = false, nullable = false, length = 100)	
	private String description;		

	private String category;	
	private int level;	
	@OneToMany(mappedBy = "quest")	
	List<EntityAnswer> answers;	

	public long getId() {
		return id;
	}
	public String getQuestion() {
		return questionText;
	}
	public void setQuestion(String question) {
		this.questionText = question;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return id + ":" + questionText+ ":" + description + ":" + category	+ ":" + level+ ":";
	}
	
}
