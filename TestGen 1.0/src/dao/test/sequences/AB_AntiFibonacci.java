package dao.test.sequences;

import dao.Randomizer;

public class AB_AntiFibonacci extends AbstractSequence
	{
		private int START_MIN;
		private int START_MAX;
		
		public AB_AntiFibonacci(Randomizer rand, int level) 
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
			for (int i = 2; i < this.ELEMENT_COUNT; i++)
				sequence[i] = sequence[i-2] - sequence[i-1];
			return sequence;
		}
		
		@Override
		protected void configure(int level)
		{
			switch (level)
			{
			case 0:
				START_MIN = 1; START_MAX = 10;
				break;
			case 1:
				START_MIN = 11; START_MAX = 20;
				break;
			case 2:
				START_MIN = 21; START_MAX = 50;
				break;
			}
		}
}
