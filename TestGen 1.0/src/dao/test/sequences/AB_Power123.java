package dao.test.sequences;

import dao.Randomizer;

public class AB_Power123 extends AbstractSequence
{
	private int START_MIN;
	private int START_MAX;

	public AB_Power123(Randomizer rand, int level) 
	{
		super(rand);
		configure(level);
	}
	
	@Override
	public Integer[] createSequence()
	{
		int A = rand.getRandomInRange(START_MIN, START_MAX);
		if (rand.getRandomBoolean()) A *= -1;
		int B = rand.getRandomInRange(START_MIN, START_MAX);
		if (rand.getRandomBoolean()) B *= -1;
		if (A == B) A = - B - 1;
		sequence[0] = A;
		sequence[1] = B;
		for (int i = 2; i < this.ELEMENT_COUNT; i++)
			sequence[i] = sequence[i-2] * (i % 2 == 0 ? A : B);
		return sequence;
	}
	
	@Override
	protected void configure(int level)
	{
		switch (level)
		{
		case 0:
			START_MIN = 2; START_MAX = 6;
			break;
		case 1:
			START_MIN = 7; START_MAX = 11;
			break;
		case 2:
			START_MIN = 12; START_MAX = 19;
			break;
		}
	}
}
