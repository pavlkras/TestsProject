package main.java.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="question")
public class QuestionEntity {
	@Id
	@Column(name="aa_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	long id;
	@Column(name="answer")
	String answer;
	@ManyToOne
	@JoinColumn(name="cat_diff_id", nullable=false)
	CatDiffEntity catDiff;
	@ManyToOne
	@JoinColumn(name="test_id",nullable=false)
	TestEntity test;
	public QuestionEntity(String answer, CatDiffEntity catDiff, TestEntity test) {
		super();
		this.answer = answer;
		this.catDiff = catDiff;
		this.test = test;
	}
	public QuestionEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
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
		QuestionEntity other = (QuestionEntity) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
}
