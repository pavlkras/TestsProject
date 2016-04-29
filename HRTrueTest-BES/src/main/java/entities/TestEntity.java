package main.java.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import main.java.model.dao.CandidateData;
import main.java.model.dao.TestData;

@Entity
@Table(name="test")
public class TestEntity {
	@Id
	@Column(name="aa_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	long id;
	@ManyToOne
	@JoinColumn(name="template_id")
	TemplateEntity template;
	@ManyToOne
	@JoinColumn(name="candidate_id")
	CandidateEntity candidate;
	@Column(name="link_to_test", nullable=false, unique=true)
	String link;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="creation_date")
	Date creationDate;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="start_date")
	Date startDate;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="end_date")
	Date endDate;
	@OneToMany(mappedBy="test",fetch=FetchType.EAGER,orphanRemoval=true)
	Set<BaseQuestionEntity> questions;
	
	public TestEntity(TemplateEntity template, CandidateEntity candidate, String link, 
			Date creationDate, Date startDate, Date endDate) {
		super();
		this.template = template;
		this.candidate = candidate;
		this.link = link;
		this.creationDate = creationDate;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	public TestEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TemplateEntity getTemplate() {
		return template;
	}
	public void setTemplate(TemplateEntity template) {
		this.template = template;
	}
	public CandidateEntity getCandidate() {
		return candidate;
	}
	public void setCandidate(CandidateEntity candidate) {
		this.candidate = candidate;
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
	public Set<BaseQuestionEntity> getQuestions() {
		return questions;
	}
	public void setQuestions(Set<BaseQuestionEntity> questions) {
		this.questions = questions;
	}
	public long getId() {
		return id;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((link == null) ? 0 : link.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TestEntity other = (TestEntity) obj;
		if (id != other.id)
			return false;
		if (link == null) {
			if (other.link != null)
				return false;
		} else if (!link.equals(other.link))
			return false;
		return true;
	}
	public static TestData convertToTestData(TestEntity entity){
		CandidateData candidate = CandidateEntity.convertToCandidateData(entity.candidate);
		return new TestData(candidate, entity.template.id,
				entity.link, entity.creationDate, entity.startDate, entity.endDate);
	}
	public static List<TestData> convertToTestDataList(Iterable<TestEntity> entities){
		List<TestData> tests = new ArrayList<TestData>();
		for (TestEntity entity : entities){
			tests.add(convertToTestData(entity));
		}
		return tests;
	}
}
