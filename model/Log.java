package model;


public class Log implements Computable 
{
	Computable a;
	Computable b;
	
	public Log(Computable a, Computable b)
	{
		this.a = a;
		this.b = b;
	}
	
	public double compute() 
	{
		return Math.log10(b.compute())/Math.log10(a.compute());
	}

}
