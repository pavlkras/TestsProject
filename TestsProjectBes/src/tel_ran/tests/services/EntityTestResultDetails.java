package tel_ran.tests.services;

import java.util.Arrays;

import javax.persistence.Embeddable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tel_ran.tests.services.common.CommonData;
@Embeddable
public class EntityTestResultDetails {	
/*
 	�	Test duration  
	�	Number of the questions
	�	Complexity level
	�	Number of the right answers with the percentage 
	�	Number of the wrong answers with the percentage
	�	5 photos made during the test
	*/
	private int duration;
	private int complexityLevel;
	private int quantityRight;
	private int quantityWrong;
	private String pictures = "";

	public EntityTestResultDetails() {
	}

	public JSONObject fillJsonObject(JSONObject jsonObj) {
		try {
			jsonObj.put("duration",duration);
			jsonObj.put("complexityLevel",complexityLevel);
			jsonObj.put("quantityRight", quantityRight);
			jsonObj.put("quantityWrong", quantityWrong);
			
			JSONArray picturesURLArray = new JSONArray();
			if(pictures.length() > 0){
				String [] picturesSplittedURLs = pictures.split(CommonData.delimiter);
				int i=0;
				for(String picture:picturesSplittedURLs){
					picturesURLArray.put(i++, picture);
				}
			}
			if(picturesURLArray.length() > 0){
				jsonObj.put("picturesURL", picturesURLArray);
			}
		} catch (JSONException e) {}
		return jsonObj;
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
	
	public void addPictureLink(String pictureLink) { //http-links with delimiters
		if(this.pictures.length() == 0){
			this.pictures = pictureLink;
		}
		else{
			this.pictures += CommonData.delimiter + pictureLink;
		}
	}
}
