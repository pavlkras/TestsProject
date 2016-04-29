package main.java.model.dao;

import java.util.Date;

public class TestData {
	CandidateData candidate;
	long templateId;
	String link;
	Date creationDate;
	Date startDate;
	Date endDate;
	public TestData(CandidateData candidate, long templateId, String link, 
			Date creationDate, Date startDate, Date endDate) {
		this.candidate = candidate;
		this.templateId = templateId;
		this.link = link;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	public TestData() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CandidateData getCandidate() {
		return candidate;
	}
	public void setCandidateId(CandidateData candidate) {
		this.candidate = candidate;
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
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
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
