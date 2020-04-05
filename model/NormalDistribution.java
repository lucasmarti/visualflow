package model;
import cern.jet.random.Normal;
import cern.jet.random.engine.DRand;
import cern.jet.random.engine.RandomSeedGenerator;

public class NormalDistribution implements Distribution 
{
	Normal normal;
	
	public NormalDistribution()
	{
		normal = new Normal(1,1, new DRand(new RandomSeedGenerator().nextSeed()));
	}
	public double nextDouble() 
	{
		return normal.nextDouble();
	}

	public void setState(double mean, double stdev) 
	{
		
		normal.setState(mean,stdev);
	}

}
