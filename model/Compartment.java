package model;
import java.util.LinkedList;

public class Compartment implements Computable
{
	String name;
	double value;
	double[][] data;
	double[] mean;
	double[] stdev;
	double[] _95up;
	double[] _95down;
	int current_run;
	int current_step;
	
	int nb_runs;
	int nb_steps;
	
	
	public Compartment(String name, double value)
	{
		this.name = name;
		this.value = value;
	}
	
	public void analyze()
	{
		if(nb_runs>1)
		{
			computeMean();
			computeStdev();
			computeConfidence();
		}
	}

	private void computeConfidence() 
	{
		double[] temp = new double[nb_runs];
		for(int i=0; i<nb_steps; i++)
		{
			for(int j=0; j<nb_runs; j++)
				temp[j]=data[j][i];
			double[] r = Confidence.compute95percentConfInt(temp);
			_95up[i]=r[1];
			_95down[i]=r[0];
		}
	}

	private void computeStdev() 
	{
		double m;
		for(int i=0; i<nb_steps; i++)
		{
			m = 0.0;
			for(int j=0; j<nb_runs; j++)
			{
				m = m + Math.abs(mean[i] - data[j][i])*Math.abs(mean[i] - data[j][i]);
			}
			m = m /nb_runs;
			stdev[i] = Math.sqrt(m);
		}		
	}

	private void computeMean() 
	{
		double m;
		for(int i=0; i<nb_steps; i++)
		{
			m = 0.0;
			for(int j=0; j<nb_runs; j++)
			{
				m = m + data[j][i];
			}
			mean[i] = m/nb_runs;
		}
	}

	public void initData(int runs, int steps)
	{
		data = new double[runs][steps+1];
		for(int i=0; i<runs; i++)
			data[i][0] = value;
		nb_steps = steps+1;
		nb_runs = runs;
		mean = new double[steps+1];
		stdev = new double[steps+1];
		_95down = new double[steps+1];
		_95up = new double[steps+1];
	}
	
	public void setPosition(int r, int s)
	{
		current_run = r;
		current_step= s;
	}
	public void setValue(double d)
	{
		//System.out.println("setting value "+d+" at run "+current_run+" step "+(current_step+1));
		data[current_run][current_step+1] = d; 
	}
	public double compute() 
	{
		return data[current_run][current_step];
	}
}
