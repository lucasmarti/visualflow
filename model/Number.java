package model;


public class Number implements Computable 
{
	public double value;
	
	public Number(double e)
	{
		this.value = e;
	}
	public Number(String s)
	{
		value = new Double(s).doubleValue();
	}
	
	public double compute() 
	{
		return value;
	}

}
