package model;


public class Equation 
{
	Compartment dest;
	Computable root;
	double delta;
	
	public Equation(Compartment dest, Computable e)
	{
		this.dest = dest;
		root = e;
	}
	
	public void compute(double precision)
	{
		delta = root.compute();
		double x = delta+dest.compute();
		x = round(precision,x);
		dest.setValue(x);
	}
	private double round(double precision, double d)
	{
		d = d * 1 / precision;
		d = Math.round(d);
		d = d * precision;
		return d;
	}
}
