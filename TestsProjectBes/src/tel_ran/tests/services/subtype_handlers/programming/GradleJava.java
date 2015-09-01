package tel_ran.tests.services.subtype_handlers.programming;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import tel_ran.tests.services.utils.FileManagerService;

public class GradleJava extends AbstractGradleTest {
	
	private static String PATH_TO_GRADLE_BUILD = BASE_PATH_FOR_BUILDS + "java";
	
	private static String GRADLE_TEXT = "apply plugin: 'java'\n\n" +
										"repositories {\nmavenCentral()\n}\n\n" +
										"dependencies{\n" +
										"testCompile group: 'junit', name: 'junit', version: '4.+'\n}";
	
	private static final String innerFolder00 = "src";
	private static final String innerFolder01 = "src/main/java";
	private static final String innerFolder02 = "src/test/java";
	
	private static final String packageMain = "main.java";
	private static final String READ_ME_FILE = "Readme.txt";
	private static final String LinkToJUnitFile = "JUnit";
	private static final String LinkToInterface = "Interface";
	
	
	public GradleJava() {
		super();
		pathToSimpeBuild = FileManagerService.BASE_DIR_SIMPLE_FILES + PATH_TO_GRADLE_BUILD + File.separator + STANDARD_BUILD_NAME;
	}

	
	@Override
	protected String getBuildText() {			
		return GRADLE_TEXT;
	}

	@Override
	protected void createBuildStructure(String path) {
		
		StringBuffer tmp = new StringBuffer("");		
		
		//folder src
		tmp.append(path).append(File.separator).append(innerFolder00);			
		File file = new File(tmp.toString());		
		file.mkdirs();
		
		//folder main and path
		tmp = new StringBuffer("");
		tmp.append(path).append(File.separator).append(innerFolder01);
		this.pathToProgramFiles = tmp.toString();
		
		//folder test and path
		tmp = new StringBuffer("");
		tmp.append(path).append(File.separator).append(innerFolder02);
		this.pathToTestFiles = tmp.toString();
				
		//creation folders
		file = new File(this.pathToProgramFiles);
		file.mkdirs();
		file = new File(this.pathToTestFiles);
		file.mkdirs();
		
	}


	 
	 // This function searches the name of the class from the text of the file.
	 // It finds the first word = "class" and return the next word (without {)
	 // It also finds a line with the word "package" and removes it.  
	@Override
	protected String getFileName(File f) throws IOException {
		BufferedReader reader = null;
		
		String result = null;		
		
		boolean classFound = false;
		boolean isPackage = false;
		boolean isMainPackage = false;
		
		try {
			reader = new BufferedReader(new FileReader(f));
		
			while(!classFound) {			
				String line = reader.readLine();
			
				if(line != null) {
					if(line.contains("package ")) {
						isPackage = true;
						if(line.contains(packageMain)) {
							isMainPackage = true;
						}
					} else if (line.contains("public class ")) {
						String[] words = line.split(" ");
						int len = words.length;
						for (int i = 0; i < len; i++) {
							if (words[i].equals("class"))
								result = words[i+1];
						}
								
						if (result.contains("{")) {
							String[] www = result.split("{");
							result = www[0];
						} 
					
						classFound = true;					
					}				
				}
			}
		} finally {
			reader.close();		
		}
		
		
		//This part of the methods runs if there's a line with package in the file		
		if(!isMainPackage) {
			LinkedList <String> fileLines = new LinkedList<String>();
			try {
				reader = new BufferedReader(new FileReader(f));			
				while (true) {
					String line = reader.readLine();
					if(line==null)
						break;
					fileLines.add(line);
				}
			
				if(isPackage) {
					for (String str : fileLines) {
						if(str.contains("package ")) { 
							fileLines.remove(str);
							break;
						}
					}
				}
			
				fileLines.addFirst("package " + packageMain + ";");
			} finally {
				reader.close();
			}
			
			f.createNewFile();			
			PrintWriter writer = null;
			try {
				writer = new PrintWriter(f);				
				for (String str : fileLines) {
					writer.println(str);
				}
			} finally {
				writer.close();	
			}
		}
					
		return result;
	}


	@Override
	protected void renameCopyOneFile(File codeFromPerson, String destination) {		
		try {
			// new name of class in destination folder
			String name = getFileName(codeFromPerson);
			String newPath = destination.concat(name).concat(".java");
			
			FileManagerService.copyFiles(codeFromPerson.getPath(), newPath);
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}


	@Override
	protected void archiveReading(ZipFile zip) throws IOException {	
			
			Map<String, String> files = new HashMap<String, String>();		
			ZipEntry readme = zip.getEntry(GradleJava.READ_ME_FILE); 		
			InputStream in = zip.getInputStream(readme);		
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));				
			String line = null;		
			do {
				line = reader.readLine();			
				if (line!=null) {
					String[] lineContent = line.split(":");

					if (lineContent.length > 1) {
						files.put(lineContent[0], lineContent[1].replace(" ", ""));				
					}	
				}
			} while (line!=null);
			
			reader.close();
			in.close();	
			
			FileManagerService.extractFromZip(zip, files.get(GradleJava.LinkToJUnitFile), this.pathToTestFiles);
			FileManagerService.extractFromZip(zip, files.get(GradleJava.LinkToInterface), this.pathToProgramFiles);					
			
	}


		
	
	
	

}
