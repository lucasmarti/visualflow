package model;


public class Minus implements Computable 
{
	Computable a;
	Computable b;
	
	public Minus(Computable a, Computable b)
	{
		this.a = a;
		this.b = b;
	}
	
	public double compute() 
	{
		return a.compute() - b.compute();
	}

}
