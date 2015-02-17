package tel_ran.tests.model;

import java.util.*;

public class Question {

	List<String> mAnswrIDsList = null;
	
	public List<String> getAnswrIDsList() {
		return mAnswrIDsList;
	}

	public void setAnswrIDsList(List<String> answrIDsList) {
		mAnswrIDsList = new ArrayList<String>();
		for(int i = 0; i < answrIDsList.size(); i++){
			mAnswrIDsList.add(answrIDsList.get(i));
		}
	}

	private long id;

	private String questionText;

	private String description;		

	private String category;
	
	private int level;	

	public void setId(long id) {
		this.id = id;
	}

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
}
