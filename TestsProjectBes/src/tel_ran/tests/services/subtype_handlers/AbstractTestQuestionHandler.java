package tel_ran.tests.services.subtype_handlers;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;

import tel_ran.tests.entitys.EntityQuestionAttributes;
import tel_ran.tests.services.TestsPersistence;
import tel_ran.tests.services.inner_result.dataobjects.InnerResultDataObject;
import tel_ran.tests.services.interfaces.IFileManagerService;

public abstract class AbstractTestQuestionHandler extends TestsPersistence{
	@Autowired
	IFileManagerService fileManager;
	
	public static final String KEY_INDEX = "index";
	public static final String KEY_QUESTION_TEXT = "question_text";
	public static final String KEY_NUMBER_OF_ANSWERS = "n_answers";
	public static final String KEY_QUESTION_IMAGE = "question_image";
	
	private EntityQuestionAttributes entityQuestionAttributes;
	protected InnerResultDataObject dataObj;
	protected long companyId;
	protected long testId;
	
	final public void fromJsonString(String json, long companyId, long testId) {
		dataObj = new Gson().fromJson(json, InnerResultDataObject.class);
		this.companyId = companyId;
		this.testId = testId;
	}

	final public void createFromQuestion(long questionId, String metacategory) {
		dataObj = new InnerResultDataObject();
		dataObj.setQuestionID(questionId);
		dataObj.setMetacategory(metacategory);
		dataObj.setStatus(InnerResultDataObject.STATUS_NOT_ASKED);		
	}
	
	final public String toJsonString() {
		return new Gson().toJson(dataObj);
	}

	final public long getQuestionID() {
		return dataObj.getQuestionID();
	}

	final public String getStatus() {
		return dataObj.getStatus();
	}
	
	public EntityQuestionAttributes getQuestionAttribubes() {
		if(entityQuestionAttributes == null){
			entityQuestionAttributes = em.find(EntityQuestionAttributes.class, getQuestionID());
		}
		return entityQuestionAttributes;
	}
}