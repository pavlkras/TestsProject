package tel_ran.tests.services.subtype_handlers.gradle;

public interface ICodeTester {
	
	public boolean testIt(String codeFromPersonPath, String pathToAnswersZip);
	
	
	public static String testTemplatePath = "";
	public static String workFolderPath = "";
		
	public static String ReadMeFile = "Readme.txt";
	public static String LinkToJUnitFile = "JUnit";
	public static String LinkToInterface = "Interface";
};