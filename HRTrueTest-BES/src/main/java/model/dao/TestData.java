package main.java.model.dao;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import main.java.model.config.NamesAndFormats;

public class TestData {
	public static final String DELIMETER = "-";
	
	CandidateData candidate;
	long templateId;
	String link;
	@JsonFormat(shape=Shape.STRING, pattern=NamesAndFormats.DATE_FORMAT)
	Date creationDate;
	@JsonFormat(shape=Shape.STRING, pattern=NamesAndFormats.DATE_TIME_FORMAT)
	Date startDate;
	@JsonFormat(shape=Shape.STRING, pattern=NamesAndFormats.DATE_TIME_FORMAT)
	Date endDate;
	public TestData(CandidateData candidate, long templateId, String link, 
			Date creationDate, Date startDate, Date endDate) {
		this.candidate = candidate;
		this.templateId = templateId;
		this.link = link;
		this.creationDate = creationDate;
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
