package tel_ran.tests.services.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

@SuppressWarnings("resource")
@Repository
public class FileManagerService {
//	private String workingDir = "D:\\Programming\\IDE\\EclipseKepler\\TESTS_FILES_DATABASE";
	
	public static final String WORKING_DIR = System.getProperty("user.home");
	public static final String NAME_FOLDER_FOR_SAVENG_TESTS_FILES = "TESTS_FILES_DATABASE";
	public static final String NAME_TEST_INNER_FOLDERS = File.separator + "TESTS" + File.separator
			+ "IDE" + File.separator + "EclipseKepler" + File.separator;
	public static final String testSaveWorkingDir = WORKING_DIR + NAME_TEST_INNER_FOLDERS;
	public static final String NAME_FOLDER_FOR_SAVENG_QUESTIONS_FILES = "QUESTION_FILES_DATABASE";	
	public static final String BASE_DIR_IMAGES = WORKING_DIR + File.separator + NAME_FOLDER_FOR_SAVENG_QUESTIONS_FILES;	
		
	public static final String TEST_JSON_FILE = "test.json";
	public static final String NO_COMPANY = "admin";
	
	
	public FileManagerService(){
//		workingDir = System.getProperty("user.dir") + File.separator + NAME_FOLDER_FOR_SAVENG_TESTS_FILES; //  this global directory for saving files
	}
	////
	static final String[] BILD_ATTRIBUTES_ARRAY = {"PersonPictures","ScreenPictures","PersonCodeText"};
	static final int PERSON_PICTURES = 0;
	static final int SCREEN_PICTURES = 1;
	static final int PERSON_CODE_TEXT = 2;
//	static final int JSON_FILE = 3;
	////
	
	public static boolean initializeTestFileStructure(long compId, long testId) {
				
		boolean result = false;		
		try {		
			for(int i=0;i<BILD_ATTRIBUTES_ARRAY.length;i++){
				Files.
				createDirectories(
						Paths.get(
								testSaveWorkingDir + File.separator 
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
	
	public static void saveImage(long compId, long testId, String image) {
		Date d = new Date(System.currentTimeMillis());
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd_hhmmss");
		String path = testSaveWorkingDir + File.separator 
				+ compId + File.separator 
				+  testId + File.separator
				+ BILD_ATTRIBUTES_ARRAY[PERSON_PICTURES] + File.separator
				+ sf.format(d) + ".txt";
		saveImageByPath(path, image);		
		
	}
	
	private static void saveImageByPath(File fl, String image) {		
		try {			
			BufferedWriter writer = new BufferedWriter(new FileWriter(fl));
			writer.write(image);			 
			writer.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	private static void saveImageByPath(String path, String image) {		
		try {			
			BufferedWriter writer = new BufferedWriter(new FileWriter(path));
			writer.write(image);			 
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	public static String saveImageForUserTests(String metaCategory, String compId, String image) throws IOException {		
		if(compId == null)
			compId = NO_COMPANY;
		long name = System.currentTimeMillis();
		
		String base2 = File.separator + metaCategory + File.separator
				+ compId + File.separator;
		String basePath = BASE_DIR_IMAGES + base2;
		
		File dir = new File(basePath);
		dir.mkdirs();
		
		String nameInner = "ti" + name;
		String path = basePath + nameInner + ".jpg";
		String nameFile = nameInner + ".jpg";
		
		File fl = new File(path);
		while(fl.exists()) {
			nameInner = nameInner + "a";
			nameFile = nameInner + ".jpg";
			path = basePath + nameFile;
			fl = new File(path);			
		}
		
		fl.createNewFile();
		System.out.println(path);
		
		saveImageByPath(fl, ImageCoders.decode64(image));
		return base2 + nameFile;
						
	}
	
	public static String getImageForTests(String path) {
		String res = null;
		byte[] bytes = null;
		FileInputStream file;
		try {
			String workingDir = FileManagerService.BASE_DIR_IMAGES;
			file = new FileInputStream(workingDir+path);
			bytes = new byte[file.available()];
			file.read(bytes);
			file.close();
			res = "data:image/jpeg;base64,"+Base64.getEncoder().encodeToString(bytes);
		} catch (FileNotFoundException e) {	} 
		catch (IOException e) {
		} 
		catch (NullPointerException e) {
		}
		return res;		
		
	}
		
	public static void saveScreen(long compId, long testId, String screen) {
		try {
			Date d = new Date(System.currentTimeMillis());
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd_hhmmss");
			BufferedWriter writer = new BufferedWriter(new FileWriter(
					testSaveWorkingDir + File.separator 
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
	
	public static void saveJson(long compId, long testId, String json) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(
					testSaveWorkingDir + File.separator 
					+ compId + File.separator 
					+  testId + File.separator
					+ TEST_JSON_FILE));
			writer.write(json);			 
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getJson(long compId, long testId) {
		String outResult = "";
		String TEMP_PATH = testSaveWorkingDir + File.separator 
				+ compId + File.separator 
				+ testId + File.separator  
				+ TEST_JSON_FILE;		 
		File res = new File(TEMP_PATH);			
		
		if(res.exists()) {
			try {		
				BufferedReader reader = new BufferedReader(new FileReader(TEMP_PATH));
				outResult += reader.readLine() + ",";		
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return outResult;
	}
	////
	
	public static List<String> getImage(long compId, long testId) {
		List<String> outResult = new ArrayList<String>();
		String TEMP_PATH = testSaveWorkingDir + File.separator 
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
	
	public static List<String> getScreen(long compId, long testId) {
		List<String> outResult = new ArrayList<String>();
		String TEMP_PATH = testSaveWorkingDir + File.separator 
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
	
	public static boolean deleteCompany(long compId) {		
		boolean result = false;		
		try {
			recurrCleanFolder(new File(testSaveWorkingDir + File.separator + compId));
			result = true;
		} catch (Exception e) {			
			e.printStackTrace();
		}
		return result;
	}
	
	public static boolean deleteTest(long compId, long testId) {
		boolean result = false;		
		try {
			recurrCleanFolder(new File(testSaveWorkingDir + File.separator + compId + File.separator + testId));
			result = true;
		} catch (Exception e) {			
			e.printStackTrace();
		}
		return result;
	}
	
	private static void recurrCleanFolder (File folder) {
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

	
	public static String getPathToCode(long compId, long testId, long questionID) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static String saveCode(long compId, long testId, long questionID, String[] code) throws IOException {
				
		boolean ready = false;
		int index = 0;
		String path = null;
		String basePath = testSaveWorkingDir + File.separator + compId + File.separator + testId + File.separator
				+ BILD_ATTRIBUTES_ARRAY[PERSON_CODE_TEXT] + File.separator;
		
		File dir = new File(basePath);
		dir.mkdirs();
		File fl = null;
		
		while(!ready) {
			String fileName = "code" + System.currentTimeMillis() + index + ".txt";
			path = basePath	+ fileName;
			index++;
			fl = new File(path);
			
			if(!fl.exists()) {
				ready=true;				
			}			
		}
		
		fl.createNewFile();		
		
		String lineSeparator = System.getProperty("line.separator");
		
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(path));
			for(String str : code) {
				writer.write(str + lineSeparator);
			}				 
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return path;
	}
	
}	