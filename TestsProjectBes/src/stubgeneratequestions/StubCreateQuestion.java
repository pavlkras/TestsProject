package stubgeneratequestions;

import java.util.ArrayList;
import java.util.List;

public class StubCreateQuestion implements IGenerationQuestionsService{

	@Override
	public List<String[]> methodToCreateQuestionsByCategory(	String category, int nQuestion) {
		/*Sample - String[] question = {"What Wrong witch Code:","E11842F520AE11842F520AA24589A2458992AE532883CFA45EE4.jpg","logical","1","E","0"}; //,"a51","a52","a53","a54" ??*/
		////
		String[] description = {"What Wrong witch Code:","Solve the The equation of","Find the sequence and continue:","Solve anything:"};
		//--------------- stub methods -------------------///
		List<String[]> outResult = new ArrayList<String[]>();
		String[] question1 = {description[0],"82D39ED_QuestionIMAGE1_AEA6AE8F7201706D430E824FD2F0.jpg","logical","1","A","0"};
		String[] question2 = {description[1],"E11842F_QuestionIMAGE2_520AA2458992AE532883CFA45EE4.jpg","logical","1","B","0"};
		String[] question3 = {description[2],"82D39ED_QuestionIMAGE3_AtA6AE8F7201706D430E824FD2F0.jpg","logical","1","C","0"};
		String[] question4 = {description[3],"E11842F_QuestionIMAGE4_520AA2458992AE532883CFA45EE4.jpg","logical","1","D","0"};
		outResult.add(question1);
		outResult.add(question2);
		outResult.add(question3);
		outResult.add(question4);
		System.out.println("stub category-"+category);
		System.out.println("stub nQuestion-"+nQuestion);
		return outResult;	
		//-------------------end stubs -------------///
	}

}
