package main.java.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import main.java.model.dao.BaseQuestionData;

@Entity
@Table(name="base_question")
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class BaseQuestionEntity implements Convertable<BaseQuestionData> {
	@Id
	@Column(name="aa_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	long id;
	@Column(name="question_type", nullable=false)
	byte questionType;
	@Column(name="candidate_answer")
	String candidateAnswer;
	@Column(name="passed")
	Boolean passed;
	@ManyToOne
	@JoinColumn(name="cat_diff_id", nullable=false)
	CatDiffEntity catDiff;
	@ManyToOne
	@JoinColumn(name="test_id",nullable=false)
	TestEntity test;
	public BaseQuestionEntity(Byte questionType, String candidateAnswer, Boolean passed, CatDiffEntity catDiff, TestEntity test) {
		super();
		this.questionType = questionType;
		this.candidateAnswer = candidateAnswer;
		this.passed = passed;
		this.catDiff = catDiff;
		this.test = test;
	}
	public BaseQuestionEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public byte getQuestionType() {
		return questionType;
	}
	public void setQuestionType(byte questionType) {
		this.questionType = questionType;
	}
	public String getCandidateAnswer() {
		return candidateAnswer;
	}
	public void setCandidateAnswer(String candidateAnswer) {
		this.candidateAnswer = candidateAnswer;
	}
	public CatDiffEntity getCatDiff() {
		return catDiff;
	}
	public void setCatDiff(CatDiffEntity catDiff) {
		this.catDiff = catDiff;
	}
	public TestEntity getTest() {
		return test;
	}
	public void setTest(TestEntity test) {
		this.test = test;
	}
	public Boolean isPassed() {
		return passed;
	}
	public void setPassed(Boolean passed) {
		this.passed = passed;
	}
	public long getId() {
		return id;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
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
		BaseQuestionEntity other = (BaseQuestionEntity) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
