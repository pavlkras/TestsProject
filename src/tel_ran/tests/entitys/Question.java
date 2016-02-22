package tel_ran.tests.entitys;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import json_models.IJsonModels;
import tel_ran.tests.services.subtype_handlers.ITestQuestionHandler;

@Entity
@Table(name="Questions")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
		name="discriminator",
		discriminatorType=DiscriminatorType.STRING
		)
public abstract class Question implements IJsonModels {

	@Id
	@GeneratedValue	
	protected long id;
	
	@Column(length = 500)
	protected String fileLocationLink;
	
	@ManyToOne
	protected Category category;
	
	@ManyToOne
	protected QuestionTitle title;
	
	protected int levelOfDifficulty;
	
	@ManyToOne
	protected Company company;
	
	
	public void setTitle(String string) {
		QuestionTitle title = new QuestionTitle();
		title.setQuestionText(string);
		this.title = title;	
	}
	
	
	public String getFileLocationLink() {
		return fileLocationLink;
	}
	public void setFileLocationLink(String fileLocationLink) {
		this.fileLocationLink = fileLocationLink;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public int getLevelOfDifficulty() {
		return levelOfDifficulty;
	}
	public void setLevelOfDifficulty(int levelOfDifficulty) {
		this.levelOfDifficulty = levelOfDifficulty;
	}
	public String getLinkToFile() {
		return fileLocationLink;
	}
	public void setLinkToFile(String linkToFile) {
		this.fileLocationLink = linkToFile;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public QuestionTitle getTitle() {
		return title;
	}
	public void setTitle(QuestionTitle title) {
		this.title = title;
	}
	public long getId() {
		return id;
	}
	
	@Override
	public String getString() throws JSONException {		
		return this.getJSON().toString();
	}
	


	@Override
	public JSONArray getJSONArray() throws JSONException {		
		return null;
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
		Question other = (Question) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public abstract JSONObject getShortJSON() throws JSONException;

	public abstract ITestQuestionHandler getHandler();
		
	
}
