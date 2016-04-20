package generator.attention;

import dao.Randomizer;

public class Generator_AttentionOneString
{
	final char[] chars = {'1','2','3','4','5','6','7','8','9','0'};	
/*
  	// Make some FUN
  	{0x00FC, 0x00E9, 0x00E2, 0x00C7, 0x00E4, 0x00E0, 0x00E5, 0x00E7, 0x00EA, 0x00EB, 0x00E8, 0x00EF,
    0x00EE, 0x00EC, 0x00C4, 0x00C5, 0x00C9, 0x00E6, 0x00C6, 0x00F4, 0x00F6, 0x00F2, 0x00FB, 0x00F9, 0x00FF,
    0x00D6, 0x00DC, 0x00A2, 0x00A3, 0x00A5, 0x20A7, 0x0192, 0x00E1, 0x00ED, 0x00F3, 0x00FA, 0x00F1, 0x00D1};	
*/
	
	//level: 0 = 4, 1 = 5, 2 = 6	
	private final int orderLength;
	//level: 0 = 4, 1 = 5, 2 = 6	
	private final int orderCount;
	
	private final Randomizer rand;
	
	//level: 0 = easy, 1 = medium, 2 = hard	
	public Generator_AttentionOneString(Randomizer rand, int level) {
		this.rand = rand;
		this.orderLength = level + 4;
		this.orderCount = 5;//level + 4;
	}

	// pick random chars from alphabet without using any char more than twice
	private char[] pickRandomChars()
	{
		char[] charArr = new char[orderLength];
		int i, index;
		for (i = 0, index = chars.length-1; i < orderLength / 2; i++)
		{
			rand.swapChars(chars, index, rand.getRandomInRange(0, index));
			charArr[i] = chars[index--];
		}
		for (index = chars.length-1; i < orderLength; i++)
		{
			rand.swapChars(chars, index, rand.getRandomInRange(0, index));
			charArr[i] = chars[index--];
		}
		return charArr;
	}
	
	private String createCorrectOrder(char[] charArr)
	{	return new String(charArr);	}
	
	// swap random neighbor if they are not equal
	private String createIncorrectOrder(char[] charArr)
	{
		int index = rand.getRandomInRange(1, charArr.length-2);
		if (charArr[index] == charArr[index-1])
			rand.swapChars(charArr, index, index+1);
		else
			rand.swapChars(charArr, index, index-1);
		return new String(charArr);
	}
	
	// all combinations are correct
	public String createCorrectString()
	{
		char[] charArr = pickRandomChars();
		String correctOrder = createCorrectOrder(charArr);
		StringBuilder strBuilder = new StringBuilder(correctOrder);
		for (int i = 1; i < orderCount; i++)
			strBuilder.append(correctOrder);
		return strBuilder.toString();
	}
	
	// One combination is not correct. It's not the first or last combination.
	public String createIncorrectString()
	{
		char[] charArr = pickRandomChars();
		String correctOrder = createCorrectOrder(charArr);
		String incorrectOrder = createIncorrectOrder(charArr);
		StringBuilder strBuilder = new StringBuilder(correctOrder);
		int count = rand.getRandomInRange(1, orderCount-2);
		int i;
		for (i = 1; i < count; i++)
			strBuilder.append(correctOrder);
		strBuilder.append(incorrectOrder);
		for (i++; i < orderCount; i++)
			strBuilder.append(correctOrder);
		return strBuilder.toString();
	}

}
