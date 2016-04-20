package dao.test.sequences;

import dao.Randomizer;

public class AB_GeometricProgression extends AbstractSequence
{
	private int START_MIN;
	private int START_MAX;
	private int DIFF_MIN;
	private int DIFF_MAX;
	
	public AB_GeometricProgression(Randomizer rand, int level) 
	{
		super(rand);
		configure(level);
	}
	
	@Override
	public Integer[] createSequence()
	{
		sequence[0] = rand.getRandomInRange(START_MIN, START_MAX);
		if (rand.getRandomBoolean()) sequence[0] *= -1;
		sequence[1] = rand.getRandomInRange(START_MIN, START_MAX);
		if (rand.getRandomBoolean()) sequence[1] *= -1;
		if (sequence[0] == sequence[1]) sequence[0] = - sequence[1] - 1;
		int q = rand.getRandomInRange(DIFF_MIN, DIFF_MAX);
		if (rand.getRandomBoolean()) q *= -1;
		for (int i = 2; i < this.ELEMENT_COUNT; i++)
			sequence[i] = sequence[i-2] * q;
		return sequence;
	}

	@Override
	protected void configure(int level)
	{
		switch (level)
		{
		case 0:
			START_MIN = 1; START_MAX = 5;
			DIFF_MIN = 2; DIFF_MAX = 5; 
			break;
		case 1:
			START_MIN = 1; START_MAX = 9;
			DIFF_MIN = 6; DIFF_MAX = 9; 
			break;
		case 2:
			START_MIN = 1; START_MAX = 9;
			DIFF_MIN = 11; DIFF_MAX = 14; 
			break;
		}
	}

}
