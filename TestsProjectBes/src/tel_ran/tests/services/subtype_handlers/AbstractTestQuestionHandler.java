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
	
	InnerResultDataObject dataObj;

	final public void fromJsonString(String json) {
		dataObj = new Gson().fromJson(json, InnerResultDataObject.class);
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
		EntityQuestionAttributes entityQuestionAttributes = em.find(EntityQuestionAttributes.class, getQuestionID());
		return entityQuestionAttributes;
	}
}