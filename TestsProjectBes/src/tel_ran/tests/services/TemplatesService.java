package tel_ran.tests.services;

import java.util.List;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;

import json_models.ResultAndErrorModel;
import json_models.TemplateListModel;
import json_models.TemplateModel;
import tel_ran.tests.dao.IDataTestsQuestions;
import tel_ran.tests.entitys.TestTemplate;
import tel_ran.tests.services.common.IPublicStrings;

public class TemplatesService extends AbstractService {
	
	@Autowired
	IDataTestsQuestions testQuestsionsData;	
	

	@Override
	public String getAllElements() {
		List<TestTemplate> templatesList = testQuestsionsData.getTemplates((int)this.user.getId());
		TemplateListModel model = new TemplateListModel(templatesList);
		try {
			return model.getString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "{}";
		}
	}

	@Override
	public String createNewElement(String dataJson) {
		
		TemplateModel template = new TemplateModel();
		template.setDao(this.testQuestsionsData);		
				
		try {
			template.setData(dataJson);		
		} catch (JSONException e) {
			e.printStackTrace();
			try {
				return ResultAndErrorModel.getJson(IPublicStrings.TEST_SOME_TROUBLE);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		saveTemplate(template);
				
		try {
			return ResultAndErrorModel.getJson(IPublicStrings.TEST_SUCCESS);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "{}";
		}
		
	}
	
	protected void saveTemplate(TemplateModel template) {
		testQuestsionsData.createTemplate(template, this.user.getRole(), this.user.getId());		
	}

	@Override
	public List<String> getSimpleList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getInformation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getElement(String params) {
		// TODO Auto-generated method stub
		return null;
	}

}
