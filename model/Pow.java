package model;


public class Pow implements Computable 
{
	Computable a;
	Computable b;
	
	public Pow(Computable a, Computable b)
	{
		this.a = a;
		this.b = b;
	}
	public double compute() 
	{
		return Math.pow(a.compute(),b.compute());
	}

}
