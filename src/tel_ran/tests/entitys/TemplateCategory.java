package tel_ran.tests.entitys;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class TemplateCategory {
	
	@Id
	@GeneratedValue
	private long id;
	
	@ManyToOne
	private TestTemplate template;

	@ManyToOne
	private Category category;
	
	private int quantity;
	private int difficulty;
	
	private String source;
	private String typeOfQuestion;
	
	
	public String getTypeOfQuestion() {
		return typeOfQuestion;
	}
	public void setTypeOfQuestion(String typeOfQuestion) {
		this.typeOfQuestion = typeOfQuestion;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public TestTemplate getTemplate() {
		return template;
	}
	public void setTemplate(TestTemplate template) {
		this.template = template;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}
	public long getId() {
		return id;
	}
	
	
	
	
}
