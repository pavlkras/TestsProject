package tel_ran.tests.entitys;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import tel_ran.tests.services.subtype_handlers.AbstractTestQuestionHandler;
import tel_ran.tests.services.subtype_handlers.ITestQuestionHandler;


@Entity
@Table(name="Questions")
@DiscriminatorValue(value="Programming")
public class GeneratedProgrammingQuestion extends GeneratedQuestion {
	
	@Column(length = 2500)
	private String description;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "question")
	List<Texts> texts;

	
	
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Texts> getTextsList() {
		return texts;
	}

	public void setTextsList(List<Texts> textsList) {
		this.texts = textsList;
	}
	
	public void setStubList(List<String> texts) {		
		if(texts!=null) {
			this.texts = new ArrayList();
			for(String s : texts) {
				Texts	text = new Texts();
				text.setText(s);
				this.texts.add(text);				
				
			}			
		}		
	}

	@Override
	public GeneratedQuestion getNewInstance() {
		
		return new GeneratedProgrammingQuestion();
	}

	@Override
	public ITestQuestionHandler getHandler() {
		String beanName = this.category.getCategoryName().toLowerCase();	
		ITestQuestionHandler handler = AbstractTestQuestionHandler.handlerFactory(beanName);
		handler.setQuestion(this);
		return handler;
	}	
	

}
