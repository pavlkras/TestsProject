package tel_ran.tests.services.subtype_handlers;

import com.google.gson.Gson;

import tel_ran.tests.services.inner_result.dataobjects.InnerResultDataObject;

public abstract class AbstractTestQuestionHandler {
	
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

}
