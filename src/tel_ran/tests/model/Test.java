package tel_ran.tests.model;

import java.io.Serializable;
import java.util.*;

public class Test implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2947833404671486451L;
	private int countOfQuestionsFromUser = 0;// count of questions from user	
	private String userMailAddress;// for sending test on mail 
	private List<Long> testQuestionID_setNumbers = null;
	private int complexityLevel = 0;
	private String categoryName = null;
	//
	private long startTimeMillis = 0;
	private long endTimeMillis = 0;
	private long totalTimeMillis = 0;
	//
	private String rightAnswerChars;
	private String userAnswer;
	private List<String> mTestResultList = null;
	////
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public int getCountOfQuestionsFromUser() {
		return countOfQuestionsFromUser;
	}
	public void setCountOfQuestionsFromUser(int countOfQuestionsFromUser) {
		this.countOfQuestionsFromUser = countOfQuestionsFromUser;
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
		for(String tl :testResultList){
			mTestResultList.add(tl);
		}
	}
	public void clearTestResultList() {
		mTestResultList.clear();
	}

	public List<Long> getQstnNmList() {
		return testQuestionID_setNumbers;
	}
	////////////////
	public String getRightAnswersChars() {
		return rightAnswerChars;
	}
	public void setRightAnswersChars(String rightAnswerChars) {
		this.rightAnswerChars = rightAnswerChars;
	}
	public String getUserAnswers() {
		return userAnswer;
	}
	public void setUserAnswers(String userAnswers) {
		this.userAnswer = userAnswers;
	}
	////////////////
	public void setQstnNmList(List<Long> qstnNmList) {
		testQuestionID_setNumbers = new ArrayList<Long>();
		for(long ltl :qstnNmList){
			testQuestionID_setNumbers.add(ltl);
		}
	}
	public int getLevel() {
		return complexityLevel;
	}
	public void setLevel(int level) {
		this.complexityLevel = level;
	}
	public String getUserMailAddress() {
		return userMailAddress;
	}
	public void setUserMailAddress(String userMailAddress) {
		this.userMailAddress = userMailAddress;
	}
	@Override
	public String toString() {
		return "Test [countOfQuestionsFromUser=" + countOfQuestionsFromUser
				+ ", userMailAddress=" + userMailAddress + ", mQstnIDsList="
				+ testQuestionID_setNumbers + ", startTimeMillis=" + startTimeMillis
				+ ", endTimeMillis=" + endTimeMillis + ", totalTimeMillis="
				+ totalTimeMillis + ", level=" + complexityLevel + ", categoryName="
				+ categoryName + ", rightAnswerCounter=" + rightAnswerChars
				+ ", wrongAnswerCounter=" + userAnswer
				+ ", mTestResultList=" + mTestResultList + "]";
	}
	
}
