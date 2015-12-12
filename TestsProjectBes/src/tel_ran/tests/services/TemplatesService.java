package tel_ran.tests.services;

import java.util.List;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;

import json_models.ResultAndErrorModel;
import json_models.TemplateListModel;
import json_models.TemplateModel;
import tel_ran.tests.dao.IDataTestsQuestions;
import tel_ran.tests.entitys.EntityTestTemplate;
import tel_ran.tests.services.common.IPublicStrings;

public class TemplatesService extends AbstractService {
	
	@Autowired
	IDataTestsQuestions testQuestsionsData;	
	

	@Override
	public String getAllElements() {
		List<EntityTestTemplate> templatesList = testQuestsionsData.getTemplates((int)this.user.getId());
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
		TemplateModel template = null;
		try {
			template = new TemplateModel(dataJson);			
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
		testQuestsionsData.createTemplate(template.getEntity(), this.user.getRole(), this.user.getId());		
	}

}
