package dao.test;

import java.util.*;

public class SequenceTest
{
	final String description = "Pick the correct answer";
	Integer[] sequence;
	Map<Integer, Boolean> answers;
	
	public SequenceTest(Integer[] sequence, Map<Integer, Boolean> answers)
	{
		this.sequence = sequence;
		this.answers = answers;
	}

	public String getDescription() {
		return description;
	}

	public Integer[] getSequence() {
		return sequence;
	}

	public Map<Integer, Boolean> getAnswers() {
		return answers;
	}

	@Override
	public String toString()
	{
		StringBuilder strBuilder = new StringBuilder("[");
		for (Integer i : sequence)
			strBuilder.append( (i == null ? "?" : i) + ", ");
		strBuilder.delete(strBuilder.length()-2,strBuilder.length()).append("]\n");
		
		char[] answersL = {'A','B','C','D','E'};
		int i = 0;
		for (Integer in : answers.keySet())
			strBuilder.append(answersL[i++] + ": " + in + ", ");
		return strBuilder.deleteCharAt(strBuilder.length()-2).append("\n").toString();
	}
}
