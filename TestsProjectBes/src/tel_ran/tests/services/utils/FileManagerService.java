package tel_ran.tests.services.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;

import java.util.LinkedList;
import java.util.List;



import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.springframework.stereotype.Repository;




@SuppressWarnings("resource")
@Repository
public class FileManagerService {
//	private String workingDir = "D:\\Programming\\IDE\\EclipseKepler\\TESTS_FILES_DATABASE";
	
	private static final String LOG = FileManagerService.class.getSimpleName();
	
	//base path
	private static final String WORKING_DIR = System.getProperty("user.home") + File.separator + "HRTrueTests" + File.separator;
	//saving into the server:
	// request.getSession().getServletContext().getRealPath("/WEB-INF/");
	// or @Autowired ServletContext servletContext;
	// but context can be each for each virtual machine for "distributed" application
	
	// main folders
	private static final String DATA_FILES = WORKING_DIR + "data" + File.separator;
	private static final String TEST_FILES = WORKING_DIR + "tests" + File.separator;;
	private static final String TO_PROGRAM_COMPILE = WORKING_DIR + "check_program" + File.separator;
	
	//folders for data 	
	public static final String BASE_DIR_IMAGES = DATA_FILES + "QUESTION_FILES_DATABASE"; //images for questions in DB
	public static final String BASE_DIR_FILES = DATA_FILES + "files"; //gradle builds and other
	public static final String BASE_DIR_SIMPLE_FILES = BASE_DIR_FILES + File.separator + "simples"; 
	
	//folders for personal tests
	private static final String testSaveWorkingDir = TEST_FILES + "IDE" + File.separator + "EclipseKepler";
	private static final String[] BILD_ATTRIBUTES_ARRAY = {"PersonPictures","ScreenPictures","PersonCodeText"};
	private static final int PERSON_PICTURES = 0;
	private static final int SCREEN_PICTURES = 1;
	private static final int PERSON_CODE_TEXT = 2;
	
	//folders for check programs
	public static final String BASE_CODE_TEST = TO_PROGRAM_COMPILE + "check";
	
	
	private static final String TEST_JSON_FILE = "test.json";
	private static final String NO_COMPANY = "admin";
			
	
	//monitors
	private static final Object MONITOR_PHOTO = new Object();
	private static final Object MONITOR_IMAGES = new Object();
	private static final Object MONITOR_INITIALIZE = new Object();
	private static final Object MONITOR_DELETE = new Object();
	private static final Object	MONITOR_CODE = new Object();
	
	public static boolean initializeTestFileStructure(long compId, long testId) {
				
		boolean result = false;		
		try {		
			
			synchronized (MONITOR_INITIALIZE) {			
			
				for(int i=0;i<BILD_ATTRIBUTES_ARRAY.length;i++){
					Files.
					createDirectories(
						Paths.get(
								testSaveWorkingDir + File.separator 
								+ compId + File.separator 
								+ testId + File.separator 
								+ BILD_ATTRIBUTES_ARRAY[i]));// creating a new directory for person test 
				}
			}
			result = true;
		} catch (IOException e) {
			e.printStackTrace();  
		}	
		return result;
	}
	////
	
	public static void saveImage(long compId, long testId, String image) throws IOException {
				
		Date d = new Date(System.currentTimeMillis());
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd_hhmmss");		
			
		String basePath = getBasePathForPhoto(compId, testId) + File.separator;
				
		String nameFile = sf.format(d);
		String extension = ".txt";
		String path;
		int index = 0;
		File fl = null;
		boolean flag = false;
		
		synchronized (MONITOR_PHOTO) {
			while(!flag) {
				path = basePath + nameFile + extension;
			
				fl = new File(path);
				if(fl.exists()) {
					nameFile = nameFile + "-" + index;
					index++;
				} else {										
					fl.createNewFile();
					flag = true;
					System.out.println(LOG + " - 92: path to image, try="+index+" : " + path);
				}
			
			}
		}		
		saveImageByPath(fl, image);		
		
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
		
	

	private static void saveImageByPath(File fl, byte[] image) {		
		try {			
			FileOutputStream writer = new FileOutputStream(fl.getAbsolutePath());
			writer.write(image);			 
			writer.close();
			System.out.println(LOG + " - 117 - in method saveImageByPath");
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
	
	public static String saveImageForUserTests(String metaCategory, String uniqueName, String image) throws IOException {				
		long name = System.currentTimeMillis();
		
		String base2 = File.separator + metaCategory + File.separator
				+ uniqueName + File.separator;
		String basePath = BASE_DIR_IMAGES + base2;
		
		File dir = new File(basePath);
		dir.mkdirs();
		
		String nameInner = "ti" + name;
		String path = basePath + nameInner + ".jpg";
		String nameFile = nameInner + ".jpg";
		
		File fl = new File(path);
		
		synchronized (MONITOR_IMAGES) {
				
			while(fl.exists()) {
				nameInner = nameInner + "a";
				nameFile = nameInner + ".jpg";
				path = basePath + nameFile;
				fl = new File(path);			
			}
		
			fl.createNewFile();
		}
		
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
		System.out.println(LOG + " - 239 - in method getImage");
		List<String> outResult = new ArrayList<String>();
		
		String basePath = getBasePathForPhoto(compId, testId);
		System.out.println(LOG + " - 243 - M: getImage: basePath to search files = " + basePath);
		File[] res = new File(basePath).listFiles();
		System.out.println(LOG + " - 245 - M: getImage: number of files in this directory = " + res.length);
		////
		
		try {
			for(int i =0;i<res.length;i++){
				
				BufferedReader reader = new BufferedReader(new FileReader(res[i]));
				StringBuffer currentFile = new StringBuffer();
				String sCurrentLine = "";
				while ((sCurrentLine = reader.readLine()) != null) {
					currentFile.append(sCurrentLine);
				}
				
				outResult.add(currentFile.toString());			
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
		
		synchronized (MONITOR_DELETE) {
		
		try {
			recurrCleanFolder(new File(testSaveWorkingDir + File.separator + compId));
			result = true;
		} catch (Exception e) {			
			e.printStackTrace();
		
		}
		}
		return result;
	}
	
	public static boolean deleteTest(long compId, long testId) {
		boolean result = false;	
		
		synchronized (MONITOR_DELETE) {
			
		
		try {
			recurrCleanFolder(new File(testSaveWorkingDir + File.separator + compId + File.separator + testId));
			result = true;
		} catch (Exception e) {			
			e.printStackTrace();
		}
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

	
	public static String getPathToCode(long compId, long testId, String link) {
		
		return getBasePathForCode(compId, testId) + link;
	}
	
	public static String saveCode(long compId, long testId, long questionID, String[] code) throws IOException {
				
		boolean ready = false;
		int index = 0;
		String path = null;
		String basePath = getBasePathForCode(compId, testId);
		
		File dir = new File(basePath);
		dir.mkdirs();
		File fl = null;
		
		synchronized(MONITOR_CODE) {
		
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
		}
		
		saveFileByLines(code, fl);
		
		return path;
	}
	
	public static void saveFileByLines(String[] lines, File fl) {
		String lineSeparator = System.getProperty("line.separator");
		
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(fl));
			for(String str : lines) {
				writer.write(str + lineSeparator);
			}				 
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static String getBasePathForPhoto(long companyId, long testId) {
		
		return 	testSaveWorkingDir + File.separator 
				+ companyId + File.separator 
				+  testId + File.separator
				+ BILD_ATTRIBUTES_ARRAY[PERSON_PICTURES];

	}
	
	public static String getBasePathForCode(long compId, long testId) {
		return testSaveWorkingDir + File.separator + compId + File.separator + testId + File.separator
				+ BILD_ATTRIBUTES_ARRAY[PERSON_CODE_TEXT] + File.separator;
	}
	
	public static void copyFiles(String source, String dest) throws IOException {
		System.out.println(source);
		System.out.println(dest);
		Files.copy(new File(source).toPath(), new File(dest).toPath());	
	}
	
	public static String readTheFile(String path) throws IOException {
		StringBuilder result = new StringBuilder();
		
		BufferedReader reader = new BufferedReader(new FileReader(path));
		String line = reader.readLine();
		while(line!=null) {
			result.append(line).append(System.getProperty("line.separator"));
			line = reader.readLine();
			System.out.println(LOG + " -403:M: readTheFile : line = " + line);
		}
		reader.close();
		return result.toString();
		
	}
	
	public static List<String> readFileToList(String path) {
		List<String> result = new LinkedList<>();
		
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(path));
			String line = reader.readLine();
			while(line!=null) {
				result.add(line);
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}
	
	public static File getUniqueFolder(String baseFolderPath, String basisName) {				
		
		File result = null;		
		
		//create name for a new folder
		String tempName = getUniquePath(basisName);
		String tempPath = baseFolderPath + File.separator + tempName;
		result = new File(tempPath);
		
		// if the directory exist, rename it
		// if the directory doesn't exist, create it
		if (result.exists()) {
			result = getUniqueFolder(baseFolderPath, basisName);
		} else 			
			try{
				result.mkdirs();
				
			} catch (Exception e) {
				//If the rights to access the folder are insufficient
				result = null;
				e.printStackTrace();
			}			
		
				
		return result;
	}
	
	
	private static String getUniquePath(String basisName) {		
		return basisName + System.currentTimeMillis();
		
	}
	
		
	/**
	 * Finds in the ZIP the file by its name and extracts it to the given folder
	 * @param zip - ZIP file
	 * @param fileNameInZip - name of the file that should be extract from the ZIP
	 * @param path - String - folder to save the extracted file. Important: the folder should exist. 
	 * @throws IOException
	 */
	public static void extractFromZip(ZipFile zip, String fileNameInZip, String path) throws IOException {
		
		String destPath = path.concat(File.separator).concat(fileNameInZip);		
		ZipEntry file = zip.getEntry(fileNameInZip);
		Files.copy(zip.getInputStream(file), new File(destPath).toPath(), StandardCopyOption.REPLACE_EXISTING);		
	}
	
}	