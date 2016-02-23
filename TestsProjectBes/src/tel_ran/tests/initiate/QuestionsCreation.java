package tel_ran.tests.initiate;

import java.io.File;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;


import tel_ran.tests.categoryLists.CategoriesList;
import tel_ran.tests.categoryLists.GeneratedCategoryList;
import tel_ran.tests.dao.IDataTestsQuestions;
import tel_ran.tests.entitys.Category;
import tel_ran.tests.entitys.Company;
import tel_ran.tests.entitys.GeneratedQuestion;
import tel_ran.tests.entitys.Question;
import tel_ran.tests.services.fields.Role;
import tel_ran.tests.services.utils.FileManagerService;
import tel_ran.tests.testGeneration.TelRanTestGenerator;
import tel_ran.tests.testGeneration.TestGeneration;


public class QuestionsCreation extends DataCreation implements Runnable {
	
	int numberOfQuestions;
	int level;
	public static final String NAME = "QUESTIONS";

//	@Autowired
//	TaskExecutor taskExecutor;
	
	public QuestionsCreation(IDataTestsQuestions tp) {
		this.persistence = tp;	
	}

	@Override
	void fill() {		
		Thread td = new Thread(this);
		td.start();
	}
	
	@Override
	public void run() {	
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e1) {			
			e1.printStackTrace();
		}
		
		CategoriesList categories = new GeneratedCategoryList((IDataTestsQuestions) this.persistence, true);
		
		int count = categories.fullSize();
		int numQuestionsForEachCategory = numberOfQuestions / count;
	
		Iterator<Category> it = categories.iterator();
		
		while(it.hasNext()) {
			try {
				questionsCreation(it.next(), level, numQuestionsForEachCategory);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}						
		}
		
				
	}
	
	private void questionsCreation(Category category, int level, int numQuestionsForEachCategory) throws Exception {
		
				
		String workingDir = FileManagerService.BASE_DIR_IMAGES;		
		Files.createDirectories(Paths.get(workingDir));	
		
		TestGeneration generator = new TelRanTestGenerator();
		generator.setWorkingDir(workingDir+ File.separator);
		
		Company adminCompany = this.persistence.getCompanyById(-1, Role.ADMINISTRATOR);
		
		
		String metaCategory = null;
		String category1 = null;
		if(category.getParentCategory()!=null) {
			metaCategory = category.getParentCategory().getCategoryName();
			category1 = category.getCategoryName();
		} else {
			metaCategory = category.getCategoryName();
		}

		
		GeneratedQuestion question = GeneratedQuestion.getQuestionByCategory(category);
		List<Question> questions = generator.createQuestions(question, metaCategory, category1, numQuestionsForEachCategory, level);

		
		if(questions != null){

			for(Question qstn : questions){	

				((IDataTestsQuestions)this.persistence).saveNewQuestion(qstn, category, adminCompany, generator.getTexts());
								
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
		
		return ((IDataTestsQuestions)this.persistence).isNoQuestions();		
	}



	@Override
	public String getName() {		
		return NAME;
	}


	

}
