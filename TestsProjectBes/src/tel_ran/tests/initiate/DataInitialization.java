package tel_ran.tests.initiate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import tel_ran.tests.data_loader.AutorizationData;
import tel_ran.tests.data_loader.IData;
import tel_ran.tests.data_loader.IDataLoader;
import tel_ran.tests.data_loader.IDataTestsQuestions;



/**
 * DataInitialization is started by Spring each time when the application is starting.
 * It checks if the DB is empty (it's the first start of the application). If true it initializes DB with some info:
 * - some number of auto-generated questions
 * - new object in company-table that has ID = 1 (ADMIN)
 * - new object in user-table that has ID = 1. It's a root user
 * All settings are in the file DataProperties 
 * @author Rusya
 */
public class DataInitialization implements BeanPostProcessor {
	
	GetData getData;
	
	
	private static final String FILE_PROPERTIES = "data.properties";
	
	
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) {
		System.out.println("HERE");
		if(beanName.equals("autoData")) {
			
			checkAndFill(new UserCreation((IDataLoader) bean));									
			checkAndFill(new CompanyCreation((IDataLoader) bean));			
			
		} else if(beanName.equals("testQuestsionsData")) {
			checkAndFill(new QuestionsCreation((IDataTestsQuestions) bean));
		}
		
		return bean;
	}
	
	private void checkAndFill(DataCreation dataCreator) {
		if(dataCreator.isNeedToFill()) {
			System.out.println("TABLE OF "+ dataCreator.getName() + " IS EMPTY");
			dataCreator.setProperties(getDataInstance().properties);
			dataCreator.fill();
			System.out.println("TABLE OF " + dataCreator.getName() + " WAS INITIALIZED");
		}
	}
	
	private GetData getDataInstance() {
		if(this.getData==null) {
			synchronized(this) {
				if(this.getData==null) {
					this.getData = new GetData();
				}
			}
		}
		return getData;		
	}
	
	@Override
	public Object postProcessBeforeInitialization(Object arg0, String arg1)
			throws BeansException {
		// TODO Auto-generated method stub
		return arg0;
	}
	
	class GetData {
		
		BufferedReader reader = null;
		Properties properties;		
		
		
		public GetData() {
			
			File f = null;
			try {
				f = new File(DataInitialization.class.getResource(FILE_PROPERTIES).toURI());
				this.reader = new BufferedReader (new FileReader(f));			
				this.properties = new Properties();
				this.properties.load(reader);				
				
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					reader.close();					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
								
		}
				
		
		

		
	}
	

}


