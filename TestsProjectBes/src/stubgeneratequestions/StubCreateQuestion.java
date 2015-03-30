package stubgeneratequestions;

import java.util.ArrayList;
import java.util.List;

public class StubCreateQuestion implements IGenerationQuestionsService{

	@Override
	public List<String[]> methodToCreateQuestionsByCategory(String category, int nQuestion) {
		//question text|| image link || category of question || level of difficulty || correct answer char || number of question text if exist question in db witch that text || number answers on image ( A,B or A,B,C,D and ...) |
		/*Sample - String[] question = {"What Wrong witch Code:","E11842F520AE11842F520AA24589A2458992AE532883CFA45EE4.png","logical","1","E","0","2"}; //,"a51","a52","a53","a54" ??*/
		////		
		//--------------- stub methods -------------------///
		List<String[]> outResult = new ArrayList<String[]>();
		String[] question1 = {"What Wrong witch Code:","82D39EDAtAD39EDAtA6AE8F76AE8F7201706D430E824FD2F0.png","logical","1","A","0","2"};
		String[] question2 = {"Solve the The equation of","8F7201706D4382D39EDAtAD39EDAtA6AE8F76AE0E824FD2F0.png","logical","1","B","0","2"};
		String[] question3 = {"Find the sequence and continue:","8F7201706D4382D9EDAtA6AE8F76A39EDAtAD3E0E824FD2F0.png","logical","1","C","0","4"};
		String[] question4 = {"Solve anything:","8F7201706D4382D9EDAtA6AE8F76A39EDAtAD3E0E824FD2F0.png","logical","1","D","0","4"};
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
// proga data sql text data file