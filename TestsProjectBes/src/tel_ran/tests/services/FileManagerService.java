package tel_ran.tests.services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import tel_ran.tests.services.interfaces.IFileManagerService;
@SuppressWarnings("resource")
@Repository
public class FileManagerService implements IFileManagerService{
	private String workingDir = "D:\\Programming\\IDE\\EclipseKepler\\TESTS_FILES_DATABASE";
	public FileManagerService(){
//		workingDir = System.getProperty("user.dir") + File.separator + NAME_FOLDER_FOR_SAVENG_TESTS_FILES; //  this global directory for saving files
	}
	////
	static final String[] BILD_ATTRIBUTES_ARRAY = {"PersonPictures","ScreenPictures","PersonCodeText","JsonFiles"};
	static final int PERSON_PICTURES = 0;
	static final int SCREEN_PICTURES = 1;
	static final int PERSON_CODE_TEXT = 2;
	static final int JSON_FILES = 3;
	////
	@Override
	public boolean initializeTestFileStructure(long compId, long testId) {
		boolean result = false;		
		try {		
			for(int i=0;i<BILD_ATTRIBUTES_ARRAY.length;i++){
				Files.
				createDirectories(
						Paths.get(
								workingDir + File.separator 
								+ compId + File.separator 
								+ testId + File.separator 
								+ BILD_ATTRIBUTES_ARRAY[i]));// creating a new directiry for person test 
			}
			result = true;
		} catch (IOException e) {
			e.printStackTrace();  
		}	
		return result;
	}
	////
	@Override
	public void saveImage(long compId, long testId, String image) {
		try {
			Date d = new Date(System.currentTimeMillis());
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd_hhmmss");
			BufferedWriter writer = new BufferedWriter(new FileWriter(
					workingDir + File.separator 
					+ compId + File.separator 
					+  testId + File.separator
					+ BILD_ATTRIBUTES_ARRAY[PERSON_PICTURES] + File.separator
					+ sf.format(d) + ".txt"));
			writer.write(image);			 
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	////
	@Override
	public void saveScreen(long compId, long testId, String screen) {
		try {
			Date d = new Date(System.currentTimeMillis());
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd_hhmmss");
			BufferedWriter writer = new BufferedWriter(new FileWriter(
					workingDir + File.separator 
					+ compId + File.separator 
					+  testId + File.separator
					+ BILD_ATTRIBUTES_ARRAY[SCREEN_PICTURES] + File.separator
					+ sf.format(d) + ".txt"));
			writer.write(screen);			 
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	////
	@Override
	public void saveJson(long compId, long testId, String json) {
		try {
//			Date d = new Date(System.currentTimeMillis());
//			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd_hhmmss");
			BufferedWriter writer = new BufferedWriter(new FileWriter(
					workingDir + File.separator 
					+ compId + File.separator 
					+  testId + File.separator
					+ BILD_ATTRIBUTES_ARRAY[JSON_FILES] + File.separator
//					+ sf.format(d)
					+ "test.json"));
			writer.write(json);			 
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	////
	@Override
	public String getJson(long compId, long testId) {
		String outResult = "";
		String TEMP_PATH = workingDir + File.separator 
				+ compId + File.separator 
				+ testId + File.separator  
				+ BILD_ATTRIBUTES_ARRAY[JSON_FILES]
				+ "test.json";		 
		File[] res = new File(TEMP_PATH).listFiles();
		////
		try {
			for(int i =0;i<res.length;i++){						
				BufferedReader reader = new BufferedReader(new FileReader(res[0]));
				outResult += reader.readLine() + ",";			
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return outResult;
	}
	////
	@Override
	public List<String> getImage(long compId, long testId) {
		List<String> outResult = new ArrayList<String>();
		String TEMP_PATH = workingDir + File.separator 
				+ compId + File.separator 
				+ testId + File.separator  
				+ BILD_ATTRIBUTES_ARRAY[PERSON_PICTURES];		 
		File[] res = new File(TEMP_PATH).listFiles();
		////
		try {
			for(int i =0;i<res.length;i++){			
				BufferedReader reader = new BufferedReader(new FileReader(res[0]));
				outResult.add(reader.readLine());			
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return outResult;
	}
	////	
	@Override
	public List<String> getScreen(long compId, long testId) {
		List<String> outResult = new ArrayList<String>();
		String TEMP_PATH = workingDir + File.separator 
				+ compId + File.separator 
				+ testId + File.separator  
				+ BILD_ATTRIBUTES_ARRAY[SCREEN_PICTURES];		 
		File[] res = new File(TEMP_PATH).listFiles();
		////
		try {
			for(int i =0;i<res.length;i++){						
				BufferedReader reader = new BufferedReader(new FileReader(res[0]));
				outResult.add(reader.readLine());			
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return outResult;
	}
	////
	@Override
	public boolean deleteCompany(long compId) {		
		boolean result = false;		
		try {
			recurrCleanFolder(new File(workingDir + File.separator + compId));
			result = true;
		} catch (Exception e) {			
			e.printStackTrace();
		}
		return result;
	}
	////
	@Override
	public boolean deleteTest(long compId, long testId) {
		boolean result = false;		
		try {
			recurrCleanFolder(new File(workingDir + File.separator + compId + File.separator + testId));
			result = true;
		} catch (Exception e) {			
			e.printStackTrace();
		}
		return result;
	}
	////
	private void recurrCleanFolder (File folder) {
		File[] files = folder.listFiles();
		if(files!=null) {
			for(File f: files) {
				if(f.isDirectory()) {
					recurrCleanFolder(f);
				} else {
					f.delete();
				}
			}
		}
		folder.delete();
	}

	@Override
	public String getPathToCode(long compId, long testId, long questionID) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void saveCode(long compId, long testId, long questionID, String[] code) {
		// TODO Auto-generated method stub
		
	}
}	