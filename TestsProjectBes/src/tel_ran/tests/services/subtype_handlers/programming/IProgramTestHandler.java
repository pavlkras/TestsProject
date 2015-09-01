package tel_ran.tests.services.subtype_handlers.programming;

import java.io.File;

public interface IProgramTestHandler {
	
	static final String BASE_PATH_FOR_BUILDS = File.separator + "gradleBuilds" + File.separator; 
	
	String getPathToBuild();
	void createStructure(String path);
	void fillTemplate(String codeFromPersonPath, String pathToAnswersZip);
	boolean test();
	void cleanWorkFolder();

}
