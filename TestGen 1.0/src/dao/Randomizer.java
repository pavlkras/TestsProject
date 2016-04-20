package dao;

import java.util.Random;

public class Randomizer
{
	private Random random;

	public Randomizer() {
		this.random = new Random();
	}
	
	public int getRandomInRange(int start, int end)
	{
		//if (start < end) return getRandomInRange(end, start);
		return start + random.nextInt(end-start+1);
	}
	
	public void shuffleChars(char[] array)
	{
		for (int n = array.length - 1; n > 0; n--)
			swapChars(array, n, getRandomInRange(0, n));
	}
	
	public void swapChars(char[] array, int index1, int index2)
	{
		char temp = array[index1];
		array[index1] = array[index2];
		array[index2] = temp;
	}

	public boolean getRandomBoolean()
	{
		return random.nextBoolean();
	}
}
