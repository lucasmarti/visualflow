package model;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.LinkedList;

import core.Keywords;

import parser.ParseException;
import parser.TokenTypes;


public class Model implements TokenTypes, Keywords
{
	public String name;
	public String version;
	public String output;
	public int runs;
	public int steps;
	public double precision;
	public int type;
	
	
	public LinkedList<Compartment> compartments;
	public LinkedList<Parameter> parameters;
	public LinkedList<Equation> equations;
	
	public Model()
	{
		compartments = new LinkedList<Compartment>();
		parameters = new LinkedList<Parameter>();	
		equations = new LinkedList<Equation>();
	}
	
	public void compute()
	{
		setParameterTypes();
		for(int c=0; c<compartments.size();c++)
			compartments.get(c).initData(runs,steps);
		for(int i=0; i<runs; i++)
		{
			for(int j=0; j<steps; j++)
			{
				for(int c=0; c<compartments.size(); c++)
				{
					compartments.get(c).setPosition(i,j);
				}
				for(int c=0; c<equations.size(); c++)
				{
					equations.get(c).compute(precision);
				}
			}
		}
		for(int i=0; i<compartments.size(); i++)
			compartments.get(i).analyze();
	}
	private void setParameterTypes() 
	{
		for(int i=0; i<parameters.size(); i++)
			parameters.get(i).setType(type);
	}

	public Compartment getCompartment(String name)
	{
		Compartment c = null;
		for(int i=0; i<compartments.size(); i++)
			if(compartments.get(i).name.compareTo(name)==0)
				c = compartments.get(i);
		return c;
	}
	
	public Parameter getParameter(String name)
	{
		Parameter p = null;
		for(int i=0; i<parameters.size(); i++)
			if(parameters.get(i).name.compareTo(name)==0)
				p = parameters.get(i);
		return p;		
	}
	
	public void writeHeadFile() throws ParseException
	{
		PrintStream outp;
		try {
			outp = new PrintStream(new File(RESULT_DIR+output+".head"));
		} catch (FileNotFoundException e) {
			throw new ParseException("cannot write file: "+output+".head");
		}
		for(int i=0; i<compartments.size(); i++)
		{
			for(int j=0; j<runs; j++)
			{
				outp.println(output+" "+compartments.get(i).name+" "+(j+1));
			}
			if(runs>1)
			{
				outp.println(output+" "+compartments.get(i).name+" mean");
				outp.println(output+" "+compartments.get(i).name+" std");
				outp.println(output+" "+compartments.get(i).name+" 95up");
				outp.println(output+" "+compartments.get(i).name+" 95down");
			}
		}
		outp.close();
	}
	public void writeDataFile() throws ParseException
	{
		PrintStream outp;
		try {
			outp = new PrintStream(new File(RESULT_DIR+output+".data"));
		} catch (FileNotFoundException e) {
			throw new ParseException("cannot write file: "+output+".data");
		}
		if(runs==1)
			outp.println(compartments.size()+";"+runs+";"+steps);
		else
			outp.println(compartments.size()+";"+(runs+4)+";"+steps);
		for(int i=0; i<compartments.size(); i++)
			outp.print(compartments.get(i).name+";");
		outp.println();
		for(int i=0; i<compartments.size(); i++)
		{
			for(int j=0; j<runs; j++)
			{
				for(int k=0; k<steps; k++)
					outp.print(compartments.get(i).data[j][k]+";");
			}
			if(runs>1)
			{
				for(int k=0; k<steps; k++)
					outp.print(compartments.get(i).mean[k]+";");
				for(int k=0; k<steps; k++)
					outp.print(compartments.get(i).stdev[k]+";");
				for(int k=0; k<steps; k++)
					outp.print(compartments.get(i)._95up[k]+";");
				for(int k=0; k<steps; k++)
					outp.print(compartments.get(i)._95down[k]+";");
			}
		}
		outp.close();
	}
	public void writeXLSFile() throws ParseException
	{
		
		PrintStream outp;
		try {
			outp = new PrintStream(new File(RESULT_DIR+output+".xls"));
		} catch (FileNotFoundException e) {
			throw new ParseException("cannot write file: "+output+".xls");
		}
		outp.println("Model:\t"+name);
		outp.println("Version:\t"+version);
		outp.println("Output File:\t"+output);
		outp.println("Steps:\t"+steps);
		outp.println("Runs:\t"+runs);
		outp.println("Precision:\t"+precision);
		if(type==CONSTANT)
			outp.println("Type:\tGauss");
		if(type==NORMAL)
			outp.println("Type:\tNormal");
		
		outp.println();
		outp.print("Compartment\t");
		for(int i=1; i<=runs; i++)
			outp.print("run "+i+"\t");
		//---- OPTIONAL MEAN, VAR, 95
		if(runs>1)
		{
			outp.print("mean\tstandard deviation\t95 up\t95 down\t");
		}
		outp.println();
		
		for(int i=0; i<compartments.size(); i++)
		{
			outp.print(compartments.get(i).name+"\t");
			for(int r=0; r<runs;r++)
				outp.print(compartments.get(i).data[r][0]+"\t");
			outp.print(compartments.get(i).mean[0]+"\t");
			outp.print(compartments.get(i).stdev[0]+"\t");
			outp.print(compartments.get(i)._95up[0]+"\t");
			outp.print(compartments.get(i)._95down[0]+"\t");

			outp.println();
			for(int s=1;s<=steps;s++)
			{
				outp.print("\t");
				for(int r=0; r<runs;r++)
					outp.print(compartments.get(i).data[r][s]+"\t");
				outp.print(compartments.get(i).mean[s]+"\t");
				outp.print(compartments.get(i).stdev[s]+"\t");
				outp.print(compartments.get(i)._95up[s]+"\t");
				outp.print(compartments.get(i)._95down[s]+"\t");
				outp.println();
	
			}
			outp.println();
		}
		outp.close();
		
		
	}
}
