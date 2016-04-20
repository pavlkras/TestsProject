package generator.sequence;

import java.util.*;

import dao.Randomizer;
import dao.test.SequenceTest;
import dao.test.sequences.*;

public class Generator_SequenceTest
{
	private final Randomizer rand;
	private final int ANSWER_COUNT = 5;
	private final Integer FAKE_ELEMENT = null;
	private final int WRONG_ANSWER_DISPERSSION = 30;
	
	private Integer[] sequenceTestQuestion;
	private Map<Integer, Boolean> sequenceTestAnswers;
	
	public Generator_SequenceTest(Randomizer rand)
	{	this.rand = rand;	}
	
	public SequenceTest generateSequenceTest(int level)
	{
		fillCorrectSequence(level);
		// 0, 1, sequenceTestQuestion.length - 2, sequenceTestQuestion.length - 1
		int randomIndex_Question = rand.getRandomInRange(0, 1);
		if (rand.getRandomBoolean()) 
			randomIndex_Question = sequenceTestQuestion.length - 1 - randomIndex_Question;
		
		int randomIndex_Answers = rand.getRandomInRange(0, ANSWER_COUNT-1);
		
		int correctAnswer = sequenceTestQuestion[randomIndex_Question];
		sequenceTestQuestion[randomIndex_Question] = FAKE_ELEMENT;
		
		int[] answerDisperssion = getAnswerDispersion(correctAnswer);
		
		sequenceTestAnswers = new TreeMap<>();
		for (int i = 0; i < ANSWER_COUNT; i++)
		{
			// shuffle answersDisperssion
			swap(answerDisperssion, i, rand.getRandomInRange(i, answerDisperssion.length-1));
			if (i == randomIndex_Answers)
				sequenceTestAnswers.put(correctAnswer, true);
			else
				sequenceTestAnswers.put(correctAnswer + answerDisperssion[i], false);
		}
		return new SequenceTest(sequenceTestQuestion, sequenceTestAnswers);
	}

	private int[] getAnswerDispersion(int correctAnswer)
	{
		int[] answersDisperssion = new int[WRONG_ANSWER_DISPERSSION * 2];
		for (int i = 0; i < WRONG_ANSWER_DISPERSSION; i++)
		{
			answersDisperssion[i] = i+1;
			answersDisperssion[WRONG_ANSWER_DISPERSSION+i] = - answersDisperssion[i];
		}
		return answersDisperssion;
	}

	// 1 = A, A+d, A+2d, A+3d, A+4d, A+5d
	// 2 = A, B, A+d, B+d, A+2d, B+2d
	// 3 = A, B, A*q, B*q, A*q*q, B*q*q
	// 4 = A, B, A*A, B*B, A*A*A, B*B*B
	// 5 = A, B, A+B, A+2B, 2A+3B, 3A+5B
	// 6 = A, B, A-B, 2B-A, 2A-3B, 5B-3A
	private final int SEQUENCE_SET = 6;
	
	private void fillCorrectSequence(int level)
	{
		switch (rand.getRandomInRange(1, SEQUENCE_SET))
		{
		case 1: sequenceTestQuestion = (new A_ArithmeticProgression(rand, level)).createSequence(); break;
		case 2: sequenceTestQuestion = (new AB_ArithmeticProgression(rand, level)).createSequence(); break;
		case 3: sequenceTestQuestion = (new AB_GeometricProgression(rand, level)).createSequence(); break;
		case 4: sequenceTestQuestion = (new AB_Power123(rand, level)).createSequence(); break;
		case 5: sequenceTestQuestion = (new AB_Fibonacci(rand, level)).createSequence(); break;
		case 6: sequenceTestQuestion = (new AB_AntiFibonacci(rand, level)).createSequence(); break;
		default: throw new IllegalArgumentException("Don't have such sequence");
		}
	}

	private void swap(int[] array, int i, int j)
	{
		int temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}
}
