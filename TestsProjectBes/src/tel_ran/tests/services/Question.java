package tel_ran.tests.services;
import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
@Entity
public class Question implements Serializable {
	private static final long serialVersionUID = 1L;
	// constructor by default
	public Question() {	}
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
	List<Answer> answers;	

	public long getId() {
		return id;
	}
	protected String getQuestion() {
		return questionText;
	}
	protected void setQuestion(String question) {
		this.questionText = question;
	}
	protected String getCategory() {
		return category;
	}
	protected void setCategory(String category) {
		this.category = category;
	}
	protected int getLevel() {
		return level;
	}
	protected void setLevel(int level) {
		this.level = level;
	}	
	protected String getDescription() {
		return description;
	}
	protected void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return id + ":" + questionText+ ":" + description + ":" + category	+ ":" + level+ ":";
	}
	
}
