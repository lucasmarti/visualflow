package model;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Quantil975 
{
	class Payload{
		int key;
		double value;
		public Payload(int k, double v)
		{
			key = k;
			value = v;
		}
	}
	
	LinkedList<Payload> table;
	StringTokenizer stk;
	
	
	public Quantil975()
	{
		table = new LinkedList<Payload>();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("model/_.quantils")));
			while(br.ready())
			{
				stk = new StringTokenizer(br.readLine());
				table.add(new Payload(new Integer(stk.nextToken()).intValue(),new Double(stk.nextToken()).doubleValue()));
			}
			// add the infinity value
			table.add(new Payload(-1,1.960));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public double getValue(int n)
	{
		for(int i=0; i<table.size()-1;i++)
		{
			if(table.get(i).key>=n)
				return table.get(i-1).value;
		}
		return table.getLast().value;
	}
	  
}
