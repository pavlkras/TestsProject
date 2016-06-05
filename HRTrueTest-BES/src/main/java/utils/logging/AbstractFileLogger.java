package main.java.utils.logging;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import org.aspectj.lang.ProceedingJoinPoint;

public abstract class AbstractFileLogger {
	protected BufferedWriter bw;
	private static final String TIMESTAMP_FORMAT = "yyyy-MM-dd'T'HH:mm";
	protected static final SimpleDateFormat dateFormat = new SimpleDateFormat(TIMESTAMP_FORMAT);
	
	public AbstractFileLogger(String fileName){
		try {
			File file = new File(System.getProperty("user.dir") + "/" + fileName);
			if (!file.exists())
				file.createNewFile();
			bw = new BufferedWriter(new FileWriter(file, true));
		}
		catch (IOException e){
			bw = new BufferedWriter(System.console().writer());
			try {
				bw.write(e.getMessage());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	@Override
	protected void finalize() throws Throwable {
		bw.close();
		super.finalize();
	}
	
	abstract public Object makeLog(ProceedingJoinPoint pjp) throws Throwable;
}
