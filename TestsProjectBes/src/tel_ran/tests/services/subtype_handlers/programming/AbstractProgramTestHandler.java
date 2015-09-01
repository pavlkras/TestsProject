package tel_ran.tests.services.subtype_handlers.programming;

import java.io.File;
import java.io.IOException;
import java.util.zip.ZipFile;

import tel_ran.tests.services.utils.FileManagerService;

abstract public class AbstractProgramTestHandler implements IProgramTestHandler {

	protected File workFolder;
	protected String pathToProgramFiles;
	protected String pathToTestFiles;
	
	public AbstractProgramTestHandler() {
		workFolder = FileManagerService.getUniqueFolder(FileManagerService.BASE_CODE_TEST, "test"); 		
		createStructure(workFolder.getPath());			
	}

	@Override
	public void fillTemplate(String codeFromPersonPath, String pathToAnswersZip) {
		
		String destFromPerson = this.pathToProgramFiles.concat(File.separator);		
		renameCopyFiles(codeFromPersonPath, destFromPerson);		
						
		ZipFile zip;
		try {
			zip = new ZipFile(pathToAnswersZip);
			archiveReading(zip);	
			
		} catch (IOException e) {			
			e.printStackTrace();
		}		
				
	}
	
	protected abstract void archiveReading(ZipFile zip) throws IOException;
	
	/**
	 * This method calls getFileName for all files in the given folder
	 * and saves these files with correct names
	 * @param codeFromPerson - the folder with text files form person (user)
	 * @param destination - the folder to save files
	 */
	protected void renameCopyFiles(String codeFromPerson, String destination) {
		
		File folder = new File(codeFromPerson);
		if(folder.isDirectory()) {
			File[] files = folder.listFiles();
			
			for (File f : files) {				
				renameCopyOneFile(f, destination);					
			}			
		} else {			
				renameCopyOneFile(folder, destination);			
		}
		
	}
	
	abstract protected void renameCopyOneFile(File codeFromPerson, String destination);
	
	
	@Override
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
}
