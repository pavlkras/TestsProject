package tel_ran.tests.model;

import java.io.Serializable;
import java.util.*;

public class Test implements Serializable{

	private int testId = 0;	

	private int userID = 0;
	
	private List<Long> mQstnIDsList = null;
	
	private long startTimeMillis = 0;
	private long endTimeMillis = 0;
	private long totalTimeMillis = 0;
	

	private boolean cameraMode = false;
	private String fotoLinks = null;
	
	private int level = 0;
	
	private String categoryName = null;
	
	private int qNum = 0;
	
	private int rightAnswerCounter = 0;
	private int wrongAnswerCounter = 0;
	
	private List<String> mTestResultList = null;

	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public int getqNum() {
		return qNum;
	}
	public void setqNum(int qNum) {
		this.qNum = qNum;
	}
	public long getStartTimeMillis() {
		return startTimeMillis;
	}
	public void setStartTimeMillis(long startTimeMillis) {
		this.startTimeMillis = startTimeMillis;
	}
	public long getEndTimeMillis() {
		return endTimeMillis;
	}
	public void setEndTimeMillis(long endTimeMillis) {
		this.endTimeMillis = endTimeMillis;
	}
	public long getTotalTimeMillis() {
		return totalTimeMillis;
	}
	public void setTotalTimeMillis(long totalTimeMillis) {
		this.totalTimeMillis = totalTimeMillis;
	}
	
	public List<String> getTestResultList() {
		return mTestResultList;
	}
	
	public void setTestResultList(List<String> testResultList) {
		
		mTestResultList = new ArrayList<String>();
		for(int i = 0; i < testResultList.size(); i++){
			mTestResultList.add(testResultList.get(i));
		}
	}
	
	public void clearTestResultList() {
		mTestResultList.clear();
	}
	
	public void setTestId(int testId) {
		this.testId = testId;
	}

	public List<Long> getQstnNmList() {
		return mQstnIDsList;
	}

	public int getRightAnswerCounter() {
		return rightAnswerCounter;
	}
	public void setRightAnswerCounter(int rightAnswerCounter) {
		this.rightAnswerCounter = rightAnswerCounter;
	}
	public int getWrongAnswerCounter() {
		return wrongAnswerCounter;
	}
	public void setWrongAnswerCounter(int wrongAnswerCounter) {
		this.wrongAnswerCounter = wrongAnswerCounter;
	}
	public void setQstnNmList(List<Long> qstnNmList) {
		mQstnIDsList = new ArrayList<Long>();
		for(int i = 0; i < qstnNmList.size(); i++){
			mQstnIDsList.add(qstnNmList.get(i));
		}
	}

	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public boolean isCameraMode() {
		return cameraMode;
	}
	public void setCameraMode(boolean cameraMode) {
		this.cameraMode = cameraMode;
	}
	public String getFotoLinks() {
		return fotoLinks;
	}
	public void setFotoLinks(String fotoLinks) {
		this.fotoLinks = fotoLinks;
	}
	
	
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public int getTestId() {
		return testId;
	}
	@Override
	public String toString() {
		return "Test [testId=" + testId + "]";
	}

}
