package tel_ran.tests.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import tel_ran.tests.dao.IDataTestsQuestions;


public abstract class TestResultsService extends AbstractService {

	@Autowired
	IDataTestsQuestions testQuestsionsData;		
	
	@Override
	public abstract String getAllElements();

	@Override
	public String getElement(String params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createNewElement(String dataJson) {
		// TODO Auto-generated method stub
		throw new RuntimeException();
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

	public abstract void setTemplateId(long template_id);
	

}
