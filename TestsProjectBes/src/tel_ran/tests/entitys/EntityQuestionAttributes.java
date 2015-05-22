package tel_ran.tests.entitys;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import tel_ran.tests.services.interfaces.IMaintenanceService;

@Entity
public class EntityQuestionAttributes implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	////
	public EntityQuestionAttributes() {	}
	////
	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;
	////codeQuestionTable
	@ManyToOne
	private EntityQuestion questionId;
	////
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "questionAttributeId")
	List<EntityAnswersText> questionAnswersList;
	////
	@Column(name = "fileLocationLink", unique = false, nullable = true, length = 500)
	private String fileLocationLink;
	////
	@Column(name = "metaCategory")
	private String metaCategory;
	////
	@Column(name = "category")
	private String category;
	////
	@Column(name = "levelOfDifficulty")
	private int levelOfDifficulty;
	////
	@Column(name = "correctAnswer")
	private String correctAnswer;
	////
	@Column(name = "numresponses")
	private int numberOfResponsesInThePicture;
	////
	@Column(name = "codeLine", length = 1500)
	private String codeLine;	
	////
	@Column(name = "languageName")
	private String languageName;
	////
	public String getLineCod() {
		return codeLine;
	}
	public void setLineCod(String lineCod) {
		this.codeLine = lineCod;
	}	
	public EntityQuestion getQuestionId() {
		return questionId;
	}
	public void setQuestionId(EntityQuestion questionId) {
		this.questionId = questionId;
	}
	public String getFileLocationLink() {
		return fileLocationLink;
	}	
	public void setFileLocationLink(String fileLocationLink) {
		this.fileLocationLink = fileLocationLink;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getLevelOfDifficulty() {
		return levelOfDifficulty;
	}
	public void setLevelOfDifficulty(int levelOfDifficulty) {
		this.levelOfDifficulty = levelOfDifficulty;
	}
	public String getCorrectAnswer() {
		return correctAnswer;
	}
	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}
	public List<EntityAnswersText> getQuestionAnswersList() {
		return questionAnswersList;
	}
	public void setQuestionAnswersList(List<EntityAnswersText> questionAnswers) {
		this.questionAnswersList = questionAnswers;
	}
	public int getNumberOfResponsesInThePicture() {
		return numberOfResponsesInThePicture;
	}
	public void setNumberOfResponsesInThePicture(int numberOfResponsesInThePicture) {
		this.numberOfResponsesInThePicture = numberOfResponsesInThePicture;
	}	
	public long getId() {
		return id;
	}	
	public String getMetaCategory() {
		return metaCategory;
	}
	public void setMetaCategory(String metaCategory) {
		this.metaCategory = metaCategory;
	}
	public String getLanguageName() {
		return languageName;
	}
	public void setLanguageName(String languageName) {
		this.languageName = languageName;
	}
	////
	@Override
	public String toString() {
		return questionId.getId()
				+ IMaintenanceService.DELIMITER + fileLocationLink
				+ IMaintenanceService.DELIMITER + category
				+ IMaintenanceService.DELIMITER + levelOfDifficulty
				+ IMaintenanceService.DELIMITER + correctAnswer
				+ IMaintenanceService.DELIMITER + numberOfResponsesInThePicture
				+ IMaintenanceService.DELIMITER + languageName
				+ IMaintenanceService.DELIMITER + metaCategory;
	}
}
