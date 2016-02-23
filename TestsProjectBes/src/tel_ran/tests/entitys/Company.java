package tel_ran.tests.entitys;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Company implements Serializable {
	
	private static final long serialVersionUID = 2L;
	
	@Id
	@GeneratedValue	
	private int id;
	
	@OneToMany (mappedBy = "company")
	private List<Test> personsTests; 
	
	@OneToMany(mappedBy = "entityCompany", fetch = FetchType.LAZY)
	List<EntityQuestionAttributes> questionAttributes;
	
	@OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
	List<Question> questions;
		
	private String C_Name;
	private String C_Site;
	private String C_Specialization;
	private String C_AmountEmployes;
	private String C_Password;
	private String C_email;	
	
	@OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
	List<TestTemplate> testTemplates;
	
	@OneToMany(mappedBy = "company")
	Set<Person> addedPersons;
	
	
		
	public List<Question> getQuestions() {
		return questions;
	}
	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}
	public String getC_Password() {
		return C_Password;
	}
	public void setC_Password(String c_Password) {
		C_Password = c_Password;
	}
	public String getC_email() {
		return C_email;
	}
	public void setC_email(String c_email) {
		C_email = c_email;
	}
	
	public int getId() {
		return id;
	}

	public String getC_Name() {
		return C_Name;
	}
	public void setC_Name(String c_Name) {
		C_Name = c_Name;
	}
	public String getC_Site() {
		return C_Site;
	}
	public void setC_Site(String c_Site) {
		C_Site = c_Site;
	}
	public String getC_Specialization() {
		return C_Specialization;
	}
	public void setC_Specialization(String c_Specialization) {
		C_Specialization = c_Specialization;
	}
	public String getC_AmountEmployes() {
		return C_AmountEmployes;
	}
	public void setC_AmountEmployes(String c_AmountEmployes) {
		C_AmountEmployes = c_AmountEmployes;
	}
	public List<Test> getPersonsTests() {
		return personsTests;
	}
	public void setPersonsTests(List<Test> personsTests) {
		this.personsTests = personsTests;
	}	
	public List<EntityQuestionAttributes> getQuestionAttributes() {
		return questionAttributes;
	}
	public void setQuestionAttributes(
			List<EntityQuestionAttributes> questionAttributes) {
		this.questionAttributes = questionAttributes;
	}
	@Override
	public String toString() {
		return "Company " + C_Name + ", Site : " + C_Site
				+ ", Specialization : " + C_Specialization
				+ ", Amount Employes : " + C_AmountEmployes 
				;
	}
	public void addQuestionAttributes(
			EntityQuestionAttributes questionAttributes) {
		if(this.questionAttributes==null)
			this.questionAttributes = new ArrayList<EntityQuestionAttributes>();
		this.questionAttributes.add(questionAttributes);		
	}
	
	public void deleteQuestionAttributes(EntityQuestionAttributes eqa) {
		this.questionAttributes.remove(eqa);
	}
	public List<TestTemplate> getTestTemplates() {
		return testTemplates;
	}
	public void setTestTemplates(List<TestTemplate> testTemplates) {
		this.testTemplates = testTemplates;
	}
}


