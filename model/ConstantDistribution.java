package model;
public class ConstantDistribution implements Distribution 
{
	double value = 0;
	public double nextDouble() 
	{
		return value;
	}

	public void setState(double mean, double variance) 
	{
		value = mean;
	}

}
