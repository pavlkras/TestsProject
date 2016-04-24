package dao.test.sequences;

import dao.Randomizer;

public class A_ArithmeticProgression extends AbstractSequence
{
	private final int START_MIN = -100;
	private final int START_MAX = 100;
	private int DIFF_MIN;
	private int DIFF_MAX;
	
	public A_ArithmeticProgression(Randomizer rand, int level)
	{
		super(rand);
		configure(level);
	}
	
	@Override
	public Integer[] createSequence()
	{
		sequence[0] = rand.getRandomInRange(START_MIN, START_MAX);
		int d = rand.getRandomInRange(DIFF_MIN, DIFF_MAX);
		if (rand.getRandomBoolean()) d *= -1;
		for (int i = 1; i < ELEMENT_COUNT; i++)
			sequence[i] = sequence[i-1] + d;
		return sequence;
	}

	@Override
	protected void configure(int level)
	{
		// level: 0 = [1,9], 1 = [11,19], 2 = [21,29]
		DIFF_MIN = level * 10 + 1;
		DIFF_MAX = DIFF_MIN + 8;
	}
}
