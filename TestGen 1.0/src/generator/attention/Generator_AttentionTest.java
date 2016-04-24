package generator.attention;

import java.util.*;
import dao.test.AttentionTest;
import dao.Randomizer;

public class Generator_AttentionTest
{
	private final Randomizer rand;
	private final int ANSWER_COUNT = 5;
	
	private String 	attentionTestQuestion;
	private Map<String, Boolean> attentionTestAnswers;
	
	public Generator_AttentionTest(Randomizer rand) 
	{
		this.rand = rand;
	}

	//level: 0 = easy, 1 = medium, 2 = hard
	public AttentionTest generateAttentionTest(int level)
	{
		this.attentionTestAnswers = new TreeMap<>();
		Generator_AttentionOneString attentionTest = new Generator_AttentionOneString(rand, level);
		if (rand.getRandomBoolean())
			pickCorrectOrder(attentionTest);
		else
			pickIncorrectOrder(attentionTest);
		return new AttentionTest(attentionTestQuestion, attentionTestAnswers);
	}

	private void pickCorrectOrder(Generator_AttentionOneString attentionStringGenerator)
	{
		attentionTestQuestion = "Which sequence is correct?";
		int i, correct = rand.getRandomInRange(0, 4);
		for (i = 0; i < correct; i++)
			attentionTestAnswers.put(attentionStringGenerator.createIncorrectString(), false);
		attentionTestAnswers.put(attentionStringGenerator.createCorrectString(), true);
		for (i++; i < ANSWER_COUNT; i++)
			attentionTestAnswers.put(attentionStringGenerator.createIncorrectString(), false);
	}

	private void pickIncorrectOrder(Generator_AttentionOneString attentionStringGenerator)
	{
		attentionTestQuestion = "Which sequence is incorrect?";
		int i, correct = rand.getRandomInRange(0, 4);
		for (i = 0; i < correct; i++)
			attentionTestAnswers.put(attentionStringGenerator.createCorrectString(), false);
		attentionTestAnswers.put(attentionStringGenerator.createIncorrectString(), true);
		for (i++; i < ANSWER_COUNT; i++)
			attentionTestAnswers.put(attentionStringGenerator.createCorrectString(), false);
	}
}
