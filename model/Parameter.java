package model;

import parser.TokenTypes;

public class Parameter implements Computable, TokenTypes
{
	String name;
	double value;
	double variance;
	Distribution dist;
	
	public Parameter(String name, double value, double variance)
	{
		this.name = name;
		this.value = value;
		this.variance = variance;
		
		dist = new ConstantDistribution();
		dist.setState(value,variance);
	}
	
	public Parameter(String name, double value)
	{
		this.name = name;
		this.value = value;
		this.variance = 0;		
		dist = new ConstantDistribution();
		dist.setState(value,variance);
	}
	
	public void setType(int type)
	{
		if(type==NORMAL)
			dist = new NormalDistribution();
		else
			dist = new ConstantDistribution();
		dist.setState(value,variance);
	}

	public double compute() 
	{
		return dist.nextDouble();
	}
}
