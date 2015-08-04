package tel_ran.tests.services.subtype_handlers.gradle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class TestTemplateHandler {
	
	private static final String innerFolder00 = "src";
	private static final String innerFolder01 = "src/main/java";
	private static final String innerFolder02 = "src/test/java";
	private static final String packageMain = "main.java";
		
	private static final String gradlePathJava = "gradleBuilds/java";
	private static final String buildName = "build.gradle";
	
	File workFolder = null;
	String testPath;
	String javaPath;
	String buildGradlePath;
	String baseFolder;
	
	
	public TestTemplateHandler(String workFolderPath) throws IOException{
		workFolder = getworkFolder(workFolderPath);
		copyBuild();
		createStructure();
	}
	
	private void copyBuild() throws IOException {
		String source = gradlePathJava;
		
		if(!File.separator.equals("/"))
			source = source.replace("/", File.separator);
		
		source = source.concat(File.separator).concat(buildName);
		buildGradlePath = baseFolder.concat(File.separator).concat(buildName);
		
		copyFiles(source, buildGradlePath);
			
	}


	private File getworkFolder(String workFolderPath) {				
		
		File result = null;		
		//create name for a new folder
		String temp = getUniquePath(workFolderPath);
		
		result = new File(temp);
		
		// if the directory exist, rename it
		// if the directory doesn't exist, create it
		if (result.exists()) {
			result = getworkFolder(workFolderPath);
		} else 			
			try{
				result.mkdir();
				baseFolder = temp;				
				
			} catch (Exception e) {
				//If the rights to access the folder are insufficient
				result = null;
				e.printStackTrace();
			}			
		
				
		return result;
	}
	
	
	private String getUniquePath(String workFolderPath) {
		StringBuffer path = new StringBuffer(workFolderPath);
		path.append(File.separator).append("test").append(System.currentTimeMillis());
		return path.toString();
		
	}
	
	private void createStructure() {
		StringBuffer tmp = new StringBuffer("");
		
		//folder src
		tmp.append(baseFolder).append(File.separator).append(innerFolder00);			
		File file = new File(tmp.toString());		
		file.mkdirs();
		
		//folder main and path
		tmp = new StringBuffer("");
		tmp.append(baseFolder).append(File.separator).append(innerFolder01);
		javaPath = tmp.toString();
		
		//folder test and path
		tmp = new StringBuffer("");
		tmp.append(baseFolder).append(File.separator).append(innerFolder02);
		testPath = tmp.toString();
		
		if (!File.separator.equals("/")) {
			javaPath = javaPath.replace('/', File.separatorChar);
			testPath = testPath.replace('/', File.separatorChar);
		}
		
		//creation folders
		file = new File(javaPath);
		file.mkdirs();
		file = new File(testPath);
		file.mkdirs();
	}


	public File getWorkFolder() {
		return workFolder;
	}

	public void cleanWorkFolder(){
		recurrCleanWorkFolder(workFolder);
	}
	
	private void recurrCleanWorkFolder (File folder) {
	    File[] files = folder.listFiles();
	    if(files!=null) {
	        for(File f: files) {
	            if(f.isDirectory()) {
	            	recurrCleanWorkFolder(f);
	            } else {
	                f.delete();
	            }
	        }
	    }
	    folder.delete();
	}
	
	
	public void fillTemplate(String codeFromPersonPath, String pathToAnswersZip) throws IOException {
					
		String destFromPerson = javaPath.concat(File.separator);
				
		renameCopyFiles(codeFromPersonPath, destFromPerson);		
						
		ZipFile zip = new ZipFile(pathToAnswersZip);		
		HashMap<String, String> files = archiveReading(zip);		
		extractFromZip(zip, files.get(ICodeTester.LinkToJUnitFile), testPath);
		extractFromZip(zip, files.get(ICodeTester.LinkToInterface), javaPath);		
			
	}
	
	
	/**
	 * This method calls getFileName for all files in the given folder
	 * and saves these files with correct names
	 * @param codeFromPerson - the folder with text files form person (user)
	 * @param destination - the folder to save files
	 */
	private void renameCopyFiles(String codeFromPerson, String destination) {
		
		File folder = new File(codeFromPerson);
		if(folder.isDirectory()) {
			File[] files = folder.listFiles();
			
			for (File f : files) {
				
				try {
					String name = getFileName(f);
					String newPath = destination.concat(name).concat(".java");
					copyFiles(f.getPath(), newPath);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}					
			}			
		} else {
			
			try {
				String name = getFileName(folder);
				String newPath = destination.concat(name).concat(".java");
				copyFiles(codeFromPerson, newPath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	/** 
	 * This function searches the name of the class from the text of the file.
	 * It finds the first word = "class" and return the next word (without {)
	 * It also finds a line with the word "package" and removes it.  	
	 * @param f - file from User (Person)
	 * @return String - name of the class
	 * @throws IOException if there're some problems with file reading
	 */
	private String getFileName(File f) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(f));
		
		String result = null;		
				
		boolean classFound = false;
		boolean isPackage = false;
		boolean isMainPackage = false;
		
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
						result = result.substring(0, result.length()-2);
					} 
					
					classFound = true;					
				}				
			}
		}
		
		reader.close();		
		
		//This part of the methods runs if there's a line with package in the file		
		if(!isMainPackage) {
			reader = new BufferedReader(new FileReader(f));
			LinkedList <String> fileLines = new LinkedList<String>();
			
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
			reader.close();			
			
			f.createNewFile();			
			PrintWriter writer = new PrintWriter(f);	
			
			for (String str : fileLines) {
				writer.println(str);
			}
			writer.close();			
		}
		
			
		return result;
	}

	private void extractFromZip(ZipFile zip, String fileName, String path) throws IOException {
		
		String destPath = path.concat(File.separator).concat(fileName);		
		ZipEntry file = zip.getEntry(fileName);
		Files.copy(zip.getInputStream(file), new File(destPath).toPath(), StandardCopyOption.REPLACE_EXISTING);
		
	}
	
	private HashMap<String,String> archiveReading(ZipFile zip) throws IOException {
		
		HashMap<String, String> result = new HashMap<String, String>();		
		ZipEntry readme = zip.getEntry(ICodeTester.ReadMeFile); 		
		InputStream in = zip.getInputStream(readme);		
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));				
		String line = null;		
		do {
			line = reader.readLine();			
			if (line!=null) {
				String[] lineContent = line.split(":");

				if (lineContent.length > 1) {
					result.put(lineContent[0], lineContent[1].replace(" ", ""));				
				}	
			}
		} while (line!=null);
		
		reader.close();
		in.close();
		
		
		return result;		
	}
	
		
	private String findName(String path) {
		
		String[] paths = path.split("[\\\\/]");

		return paths[paths.length-1];		
	}
	
	private void copyFiles(String source, String dest) throws IOException {
		Files.copy(new File(source).toPath(), new File(dest).toPath());	
	}
	
	

	
}