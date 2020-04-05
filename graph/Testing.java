package graph;
import java.io.IOException;
import java.util.LinkedList;

public class Testing 
{
	public static void main(String[] args) 
	{
		
		LinkedList<Entry> list = new LinkedList<Entry>();
		list.add(new Entry("test.model","alpha","1"));
		list.add(new Entry("test.model","alpha","mean"));
		Graph g = GraphFactory.newGraph(list,"Test");
		
		/*
		double[][] d = new double[2][];
		d[0]=new double[]{1,2,3,4};
		d[1]=new double[]{1,2};
		
		new Graph(d,new String[]{"A","B"},"5itle");
		*/
	}

}
