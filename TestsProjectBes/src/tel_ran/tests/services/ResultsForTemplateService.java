package tel_ran.tests.services;

import org.json.JSONException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import json_models.ResultAndErrorModel;
import json_models.ResultsByTemplateModel;
import tel_ran.tests.entitys.TestTemplate;
import tel_ran.tests.services.common.IPublicStrings;

@Component("testResultTemplat")
@Scope("prototype")
public class ResultsForTemplateService extends TestResultsService {
	
	long template_id;
	TestTemplate template;
	
	
	public void setTemplateId(long template_id) {
		this.template_id = template_id;		
		this.template = testQuestsionsData.getTemplateForResults(template_id, user.getId(), user.getRole());
	}
	
	@Override
	public String getAllElements() {
		ResultsByTemplateModel model = new ResultsByTemplateModel();
		model.setTemplate(this.template);
		model.setTests(testQuestsionsData.getFinishedTestsForTemplate(this.template, user.getId(), user.getRole()));
		
		try {
			String result = model.getString();
			return result;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				return ResultAndErrorModel.getJson(IPublicStrings.TEST_SOME_TROUBLE);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		
		return null;
	}

}
