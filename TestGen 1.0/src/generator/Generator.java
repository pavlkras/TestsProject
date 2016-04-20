package generator;

import dao.Randomizer;
import dao.test.*;
import generator.attention.Generator_AttentionTest;
import generator.sequence.Generator_SequenceTest;

public class Generator
{
	Generator_SequenceTest sequenceTestGenerator;
	Generator_AttentionTest attentionTestGenerator;
	Randomizer rand;
	
	public Generator()
	{
		rand = new Randomizer();
		sequenceTestGenerator = new Generator_SequenceTest(rand);
		attentionTestGenerator = new Generator_AttentionTest(rand);
	}
	
	public AttentionTest generateAttentionTest(int level)
	{
		return attentionTestGenerator.generateAttentionTest(level);
	}
	
	public SequenceTest generateSequenceTest(int level)
	{
		return sequenceTestGenerator.generateSequenceTest(level);
	}
	
}
