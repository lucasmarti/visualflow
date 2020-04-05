package model;

public interface Distribution 
{
	public double nextDouble();
	public void setState(double mean, double variance);
}
