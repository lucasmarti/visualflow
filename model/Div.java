package model;


public class Div implements Computable 
{
	Computable a;
	Computable b;
	
	public Div(Computable a, Computable b)
	{
		this.a = a;
		this.b = b;
	}
	
	public double compute() 
	{
		return a.compute() / b.compute();
	}

}
