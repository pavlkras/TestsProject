package main.java.utils.logging;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;

public abstract class AbstractFileLogger {
	protected BufferedWriter bw;
	private static final String TIMESTAMP_FORMAT = "yyyy-MM-dd'T'HH:mm";
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(TIMESTAMP_FORMAT);
	/* format: "Timestamp userID ipAddr class method message */
	private static final String LOG_FORMAT = "%s\t%d%17s\t%s\t%s\t%s%n";
	
	
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
	
	protected String getFormattedLog(Long id, String ip, String clazz, String method, String message){
		String currDT = dateFormat.format(new Date(System.currentTimeMillis()));
		if (ip == null || ip.isEmpty())
			ip = "----------------";
		return String.format(LOG_FORMAT, currDT, id, ip, clazz, method, message);
	}
	
	@Override
	protected void finalize() throws Throwable {
		bw.close();
		super.finalize();
	}
	
	abstract public Object makeLog(ProceedingJoinPoint pjp) throws Throwable;
}
