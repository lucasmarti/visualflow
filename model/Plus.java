package model;


public class Plus implements Computable 
{
	Computable a;
	Computable b;
	
	public Plus(Computable a, Computable b)
	{
		this.a = a;
		this.b = b;
	}
	public double compute() 
	{
		return a.compute() + b.compute();
	}

}
