package dao.test.sequences;

import dao.Randomizer;

public class AB_ArithmeticProgression extends AbstractSequence
{
	private final int START_MIN = -100;
	private final int START_MAX = 100;
	private int DIFF_MIN;
	private int DIFF_MAX;
	
	public AB_ArithmeticProgression(Randomizer rand, int level) 
	{
		super(rand);
		configure(level);
	}
	
	@Override
	public Integer[] createSequence()
	{
		sequence[0] = rand.getRandomInRange(START_MIN, START_MAX);
		sequence[1] = rand.getRandomInRange(START_MIN, START_MAX);
		if (sequence[0] == sequence[1]) sequence[0] = - sequence[1] - 1;
		int d = rand.getRandomInRange(DIFF_MIN, DIFF_MAX);
		if (rand.getRandomBoolean()) d *= -1;
		for (int i = 2; i < ELEMENT_COUNT; i++)
			sequence[i] = sequence[i-2] + d;
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
