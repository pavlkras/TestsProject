package main.java.model.dao;

import java.util.Date;

public class TestData {
	long candidateId;
	long templateId;
	String link;
	Date startDate;
	Date endDate;
	public TestData(long candidateId, long templateId, String link, Date startDate, Date endDate) {
		this.candidateId = candidateId;
		this.templateId = templateId;
		this.link = link;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	public TestData() {
		super();
		// TODO Auto-generated constructor stub
	}
	public long getCandidateId() {
		return candidateId;
	}
	public void setCandidateId(long candidateId) {
		this.candidateId = candidateId;
	}
	public long getTemplateId() {
		return templateId;
	}
	public void setTemplateId(long templateId) {
		this.templateId = templateId;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
