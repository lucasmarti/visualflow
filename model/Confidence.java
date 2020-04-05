package model;
public class Confidence 
{
	public static double[] compute95percentConfInt(double[] values)
	{
		double[] bounds = new double[2];
		
		bounds[0] = x_bar(values)-quantil(values.length)*s(values)/Math.sqrt(values.length);
		bounds[1] = x_bar(values)+quantil(values.length)*s(values)/Math.sqrt(values.length);
		return bounds;
	}
	private static double x_bar(double[] values)
	{
		int n = values.length;
		double x = 0.0;
		for(int i=0; i<values.length; i++)
			x = x + values[i];
		return x / n;
	}
	
	private static double s(double[] values)
	{
		double x_bar = x_bar(values);
		double x = 0.0;
		for(int i=0; i<values.length; i++)
			x = x + (values[i]-x_bar)*(values[i]-x_bar);
		x = x / (values.length-1);
		return Math.sqrt(x);
	}
	
	private static double quantil(int n)
	{
		Quantil975 quant = new Quantil975();
		return quant.getValue(n);
	}
}
