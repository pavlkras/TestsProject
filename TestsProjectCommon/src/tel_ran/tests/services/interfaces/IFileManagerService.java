package tel_ran.tests.services.interfaces;

import java.util.List;

import tel_ran.tests.services.fields.ApplicationFinalFields;

public interface IFileManagerService extends ApplicationFinalFields{
	public boolean initializeTestFileStructure(long compId, long testId);

	public List<String> getImage(long compId, long testId);
	public void saveImage(long compId, long testId, String image);

	public List<String> getScreen(long compId, long testId);
	public void saveScreen(long compId, long testId, String screen);

	public String getJson(long compId, long testId);
	public void saveJson(long compId, long testId, String json);

	public String getPathToCode(long compId, long testId, long questionID);
	public void saveCode(long compId, long testId, long questionID, String code);

	public boolean deleteTest(long compId, long testId);
	public boolean deleteCompany(long compId);

}
