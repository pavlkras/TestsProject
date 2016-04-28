package dao.test;

import java.util.Map;

public class AttentionTest
{
	String description;
	Map<String, Boolean> answers;
	
	public AttentionTest(String description, Map<String, Boolean> answers)
	{
		this.description = description;
		this.answers = answers;
	}

	public String getDescription() {
		return description;
	}

	public Map<String, Boolean> getAnswers() {
		return answers;
	}

	@Override
	public String toString()
	{
		StringBuilder str = new StringBuilder(description + "\n");
		for (String s : answers.keySet())
			str.append(s + "\n");
		return str.toString();
		/*return "AttentionTest [description=" + description + 
								"\nanswers=" + answers + "]";*/
	}
	
	
}
