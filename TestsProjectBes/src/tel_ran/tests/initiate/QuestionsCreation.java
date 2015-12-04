package tel_ran.tests.initiate;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;

import tel_ran.tests.dao.IDataTestsQuestions;
import tel_ran.tests.processor.TestProcessor;
import tel_ran.tests.services.CommonAdminServices;
import tel_ran.tests.services.MaintenanceService;
import tel_ran.tests.services.fields.Role;
import tel_ran.tests.services.utils.FileManagerService;
import tel_ran.tests.services.utils.SpringApplicationContext;


public class QuestionsCreation extends DataCreation implements Runnable {
	
	int numberOfQuestions;
	int level;
	public static final String NAME = "QUESTIONS";

//	@Autowired
//	TaskExecutor taskExecutor;
	
	public QuestionsCreation(IDataTestsQuestions tp) {
		this.persistence = tp;	
//		this.taskExecutor = (TaskExecutor) SpringApplicationContext.getBean("taskExecutor");
	}

	
	

	@Override
	void fill() {
//		taskExecutor.execute(this);	
		System.out.println("I am HERE");
		Thread td = new Thread(this);
		td.start();
	}
	
	@Override
	public void run() {	
		
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		List<String> mCategories = TestProcessor.getMetaCategory();
		int count = mCategories.size();
	
		
		for(String mc : mCategories) {
			List<String> categories = TestProcessor.getCategoriesList(mc);
			if(categories == null || categories.size() == 0) {
				try {
					questionsCreation(mc, null, level, numberOfQuestions/count);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				int subCount = categories.size();
				for(String cat : categories) {
					try {
						questionsCreation(mc, cat, level, numberOfQuestions/count/subCount);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}				
			}
		}
		
	}
	
	private void questionsCreation(String metaCategory, String category1, int maxLevel, int count) throws Exception {
				
		List<String[]> listQuestions = null;
		String workingDir = FileManagerService.BASE_DIR_IMAGES;			
		Files.createDirectories(Paths.get(workingDir));	
		TestProcessor proc = new TestProcessor();	
		if(category1==null) {
			listQuestions = proc.processStart(metaCategory, count, workingDir + File.separator, maxLevel);
		} else {
			listQuestions = proc.processStart(metaCategory, category1, count, workingDir + File.separator, maxLevel);
		}
		List<String> answers = null;
		if(listQuestions != null){
			for(String[] fres :listQuestions){	
				//for(int i=0;i<fres.length;i++){	System.out.println(i+ " - "+fres[i]+"\n");}// -------------------------------------------- test - susout
	
				answers = null;		
				
				////
				/* 0 - question text  ("Реализуйте интерфейс")
				 * 1 - description (текст интерфейса и траляля) - тут был прежде линк на картинку( this field may bee null!!!)
				 * 2 - category (маленькой. тут - Калькулятор)
				 * 3 - level of difficulty = 1-5
				 * 4 - char right answer = ( this field may bee null!!!)
				 * 5 - number responses on pictures = 1-...
				 * 6 - file location linc (for all saving files)  ( this field may bee null!!!)
				 * 7 - languageName	(bee as meta category for question witch code example)  ( this field may bee null!!!)
				 * 8 - code pattern	 (pattern code for Person)	( this field may bee null!!!)
				 */				
	
				// 0
				String questionText = fres[0].replace("'", "");					
				// 1
				String description = fres[1];
				// 2
				String category2 = fres[2];					
				// 3
				int levelOfDifficulty = Integer.parseInt(fres[3]);				
				//4
				String correctAnswer = fres[4];				
				// 5
				int numberOfResponsesInThePicture;
				if(fres[5]==null)
					numberOfResponsesInThePicture = 0;
				else
					numberOfResponsesInThePicture = Integer.parseInt(fres[5]);	
				
				// 6
				String fileLocationLink = fres[6];
				// 7
				String cat1 = fres[7];
				// 8
								
				if(fres[8]!=null && fres[8].length() > 1) {					
					answers = new ArrayList<String>();					
					answers.add(fres[8]);
				}
								
				boolean result = ((IDataTestsQuestions)this.persistence).saveNewQuestion(fileLocationLink, metaCategory, cat1, category2, levelOfDifficulty,
						answers, correctAnswer, numberOfResponsesInThePicture, description, questionText, -1, Role.ADMINISTRATOR); 
				
			}
		}
	}



	@Override
	void setProperties(Properties properties) {
		this.numberOfQuestions = Integer.parseInt(properties.getProperty("auto.questions.number"));
		this.level = Integer.parseInt(properties.getProperty("auto.questions.maxlevel"));
	}



	@Override
	public boolean isNeedToFill() {
		if(this.persistence==null) System.out.println("NULLLL");
		return ((IDataTestsQuestions)this.persistence).isNoQuestions();		
	}



	@Override
	public String getName() {		
		return NAME;
	}


	

}
