package _start;

import generator.Generator;

public class AppTest
{

	public static void main(String[] args)
	{
		Generator gen = new Generator();
		System.out.println(gen.generateAttentionTest(0));
		System.out.println(gen.generateAttentionTest(1));
		System.out.println(gen.generateAttentionTest(2));
		
		System.out.println("EASY");
		System.out.println(gen.generateSequenceTest(0));
		System.out.println(gen.generateSequenceTest(0));
		System.out.println(gen.generateSequenceTest(0));
		System.out.println(gen.generateSequenceTest(0));
		System.out.println(gen.generateSequenceTest(0));
		System.out.println("MEDIUM");
		System.out.println(gen.generateSequenceTest(1));
		System.out.println(gen.generateSequenceTest(1));
		System.out.println(gen.generateSequenceTest(1));
		System.out.println(gen.generateSequenceTest(1));
		System.out.println(gen.generateSequenceTest(1));
		System.out.println("HARD");
		System.out.println(gen.generateSequenceTest(2));
		System.out.println(gen.generateSequenceTest(2));
		System.out.println(gen.generateSequenceTest(2));
		System.out.println(gen.generateSequenceTest(2));
		System.out.println(gen.generateSequenceTest(2));
	}

}
