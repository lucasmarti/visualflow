package graph;
public class Data 
{
	String[] names;
	double[][][] data;
	int nb_compartments;
	int nb_runs;
	int nb_steps;
	
	
	public Data(int compartments, int runs, int steps)
	{
		this.names = new String[compartments];
		this.nb_compartments = compartments;
		this.nb_runs = runs;
		this.nb_steps = steps;
		data = new double[nb_compartments][nb_runs][nb_steps];
		for(int c=0; c<nb_compartments;c++)
			for(int r=0; r<nb_runs;r++)
				for(int s=0; s<nb_steps; s++)
					data[c][r][s]=0;
	}
	
	public double[] get(String comp, String run)
	{
		double[] d = new double[nb_steps];
		int c = getIndex(comp);
		int r = getRun(run)-1;
		for(int i=0; i<nb_steps; i++)
		{
			d[i]=data[c][r][i];
		}
		return d;
	}
	
	private int getRun(String run)
	{
		int r;
		if(run.compareTo("mean")==0)
			r = nb_runs -3;
		else if(run.compareTo("std")==0)
			r = nb_runs -2;
		else if(run.compareTo("95up")==0)
			r = nb_runs -1;
		else if(run.compareTo("95down")==0)
			r = nb_runs -0;
		else
			r = new Integer(run).intValue();
		return r;
	}
	public void set(int c, int r, int s, double  value)
	{
		data[c][r][s]=value;
	}
	
	private int getIndex(String s)
	{
		for(int i=0; i<names.length; i++)
			if(s.compareTo(names[i])==0)
				return i;
		return 0;
	}
	public double get(int c, int r, int s)
	{
		return data[c][r][s];
	}
}
