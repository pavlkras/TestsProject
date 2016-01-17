package tel_ran.tests.entitys;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import tel_ran.tests.dao.IDataTestsQuestions;

/**
 * TestTemplate - entity for templates of tests.
 */
@Entity
public class TestTemplate {
	
	@Id
	@GeneratedValue
	private long id;
	
	/**
	 * The name of template. It's unique for each user.
	 */
	@Column(nullable=false)
	private String templateName;
	
	/**
	 * Content of template in JSON-format. It's a JSON with fields:
	 * questionsId : [] - array with id of questions (EntityQuestionAttributes) - long
	 * template : [] - array width data:
	 * ---- cat_id : int
	 * ---- difficulty : int
	 * ---- amount : int
	 */
	@Column(length=2000)
	private String template;
	
	/**
	 * Link to the company that owns this template.
	 * If it was created by Admin this field is null 
	 */
	@ManyToOne
	private Company company;
		

	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(name="template_questions", joinColumns= @JoinColumn(name="template_id"), inverseJoinColumns=@JoinColumn(name="question_id"))
	private Set<Question> questions;
	
	@OneToMany(mappedBy="template")
	private Set<TemplateCategory> categories;			
			


	public Set<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(Set<Question> questions) {
		this.questions = questions;
	}

	public Set<TemplateCategory> getCategories() {
		return categories;
	}

	public void setCategories(Set<TemplateCategory> categories) {
		this.categories = categories;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public long getId() {
		return id;
	}

	public Set<Question> getQuestions(IDataTestsQuestions dao) {
		Set<Question> result = new HashSet<Question>();
		
		if(this.questions!=null)
			addQuestionsByIds(result, dao);
		
		if(this.categories!=null)
			addQuestionsByCategory(result, dao);
		
		return result;
	}

	private void addQuestionsByCategory(Set<Question> result, IDataTestsQuestions dao) {
		Random ran = new Random();
		for(TemplateCategory tCategory : this.categories) {
			
			List<Question> questions = dao.getQuestionsByParams(tCategory);
			int quantity = tCategory.getQuantity();
			int maxErrorNum = questions.size()*2;
			int errorCounter = 0;
			
			for(int i = 0; i < quantity; i++) {
				Question q = randomQuestion(questions, ran);
				if(result.add(q)){					
					
				} else {
					i--;
					errorCounter++;
					if(errorCounter>maxErrorNum)
						break;
				}				
			}			
		}		
	}
	
	

	private Question randomQuestion(List<Question> questions, Random ran) {
		int index = ran.nextInt(questions.size());
		return questions.get(index);
	}

	private void addQuestionsByIds(Set<Question> result, IDataTestsQuestions dao) {		
		result.addAll(questions);		
	}
	
	


	
	

}
