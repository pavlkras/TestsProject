package tel_ran.tests.services;

import javax.persistence.Embeddable;
@Embeddable
public class EntityTestDetails {	
/*
 	•	Test duration  
	•	Number of the questions
	•	Complexity level
	•	Number of the right answers with the percentage 
	•	Number of the wrong answers with the percentage
	•	5 photos made during the test
	*/
	private int duration;
	private int complexityLevel;
	private int quantityRight;
	private int quantityWrong;
	private String pictures;

	public EntityTestDetails() {
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getComplexityLevel() {
		return complexityLevel;
	}

	public void setComplexityLevel(int complexityLevel) {
		this.complexityLevel = complexityLevel;
	}

	public int getQuantityRight() {
		return quantityRight;
	}

	public void setQuantityRight(int quantityRight) {
		this.quantityRight = quantityRight;
	}

	public int getQuantityWrong() {
		return quantityWrong;
	}

	public void setQuantityWrong(int quantityWrong) {
		this.quantityWrong = quantityWrong;
	}

	public String getPictures() {
		return pictures;
	}

	public void setPictures(String pictures) {
		this.pictures = pictures;
	}
	
	
}
