package model;


public class Times implements Computable 
{
	Computable a;
	Computable b;
	public Times(Computable a, Computable b)
	{
		this.a = a;
		this.b = b;
	}
	public double compute() 
	{
		return a.compute()*b.compute();
	}

}
