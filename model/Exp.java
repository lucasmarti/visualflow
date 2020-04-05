package model;


public class Exp implements Computable 
{
	Computable a;
	public Exp(Computable a)
	{
		this.a = a;
	}
	
	public double compute() 
	{
		return Math.exp(a.compute());
	}

}
