package stubgeneratequestions;

import java.util.List;

public interface IGenerationQuestionsService {
List<String[]> methodToCreateQuestionsByCategory(String category, int nQuestions); // returns the list of arrays string
}
