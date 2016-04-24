package dao.test.sequences;

import dao.Randomizer;

public abstract class AbstractSequence
{
	protected Randomizer rand;
	protected final int ELEMENT_COUNT = 6;
	protected Integer[] sequence;
	
	public AbstractSequence(Randomizer rand)
	{
		this.rand = rand;
		sequence = new Integer[ELEMENT_COUNT];
	}
	
	public abstract Integer[] createSequence();
	
	protected abstract void configure(int level);
}
