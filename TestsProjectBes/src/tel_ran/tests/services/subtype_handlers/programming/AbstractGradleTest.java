package tel_ran.tests.services.subtype_handlers.programming;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.zip.ZipFile;

import org.gradle.tooling.BuildException;
import org.gradle.tooling.BuildLauncher;
import org.gradle.tooling.CancellationToken;
import org.gradle.tooling.GradleConnector;
import org.gradle.tooling.ProjectConnection;

import tel_ran.tests.services.utils.FileManagerService;

public abstract class AbstractGradleTest extends AbstractProgramTestHandler {
	
	private static final String LOG = AbstractGradleTest.class.getSimpleName();
	
	protected static final String STANDARD_BUILD_NAME = "build.gradle";
	private String buildGradlePath;
	private String projectPath;
	
	protected String pathToSimpeBuild;	
	protected String pathToSimpleFolder;
	
	
	private void copyBuild(String path) throws IOException {
		String sourceFile = getPathToBuild();		
		buildGradlePath = path.concat(File.separator).concat(STANDARD_BUILD_NAME);
		projectPath = path;
		FileManagerService.copyFiles(sourceFile, buildGradlePath);			
	}
	
	@Override
	public void createStructure(String path) {
		
		try {
			copyBuild(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		createBuildStructure(path);		

	}
	
	protected abstract void createBuildStructure(String path);
	
	
	@Override
	public String getPathToBuild() {	
		String result = pathToSimpeBuild; 
		System.out.println(LOG + " 59 result = " + pathToSimpeBuild);
		File fl = new File(result);
		
		if(!fl.exists())
			try {
				createBuildFile(fl);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return fl.getPath();
	}
	
	abstract protected String getBuildText();

	protected void createBuildFile(File fl) throws IOException {
		File dirs = new File(pathToSimpleFolder);
		if(!dirs.exists())
			dirs.mkdirs();
		fl.createNewFile();	
		
		String[] lines = getBuildText().split("\\n");
		
		FileManagerService.saveFileByLines(lines, fl);		
	}
	
	
	
	/** 
	 * This function searches the name of the class from the text of the file.
	 * It finds the first word = "class" and return the next word (without {)
	 * It also finds a line with the word "package" and removes it.  	
	 * @param f - file from User (Person)
	 * @return String - name of the class
	 * @throws IOException if there're some problems with file reading
	 */
	abstract protected String getFileName(File f) throws IOException;
	
	@Override
	public boolean test() {
		
		GradleConnector connector = GradleConnector.newConnector();				
		connector.forProjectDirectory(new File(projectPath));
		ProjectConnection connection = null; 
		ByteArrayOutputStream stream = null;
		ByteArrayOutputStream err = null;

		boolean result = false;
		
		try {
			
			connection = connector.connect();
			
			BuildLauncher launcher = connection.newBuild();			
			stream = new ByteArrayOutputStream();
			err = new ByteArrayOutputStream();
			launcher.forTasks("build");
			launcher.setStandardOutput(stream);
			launcher.setStandardError(err);

			try {
				launcher.run();
			} catch (BuildException e) {
				e.printStackTrace();
			} 

		if (err != null) {			
			String res2 = err.toString();
			if (res2.length() > 0) {
				result = false;
				System.out.println(err);
			} else {
				result = true;
			}
		}
		
		
		

	} finally {
		try {
			stream.close();
			err.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		connection.close();
		
		
	}
	
		
		
//		Runtime rt = Runtime.getRuntime();
//	    try {
//	        rt.exec(new String[]{"cmd.exe","/c","graddle --stop"});
//
//	    } catch (IOException e) {
//	        // TODO Auto-generated catch block
//	        e.printStackTrace();
//	    }

		
		
	return result;
	}
		

	
}
