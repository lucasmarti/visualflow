package selector;
import core.*;
import graph.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import javax.swing.DefaultListModel;
public class Selector 
{
	Kernel kernel;
	Selector_GUI gui;
	LinkedList<Entry> entries;
	LinkedList<Entry> temp_selection;
	LinkedList<Entry> final_selection;
	DefaultListModel models, compartments, runs, selections;
	
	String selected_model;
	String selected_compartment;
	String selected_run;
		
	public Selector(Kernel kernel, String directory)
	{
		
		this.kernel = kernel;
		entries = new LinkedList<Entry>();
		final_selection = new LinkedList<Entry>();
		File f = new File(directory);
		File[] files = f.listFiles(new HeadFileFilter());
		if(files!=null)
		{
			for(int i=0; i<files.length; i++)
			try {
				addFile(files[i]);
			} catch (SelectorException e) 
			{
				e.printMsg();			
			}
		}
		models = new DefaultListModel();
		compartments = new DefaultListModel();
		runs = new DefaultListModel();
		selections = new DefaultListModel();
		gui= new Selector_GUI(this);
		listFilenames();
	}
	
	private void addFile(File f) throws SelectorException
	{
		File file = f;
		StringTokenizer stk;
		try 
		{
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			while(br.ready())
			{
				stk = new StringTokenizer(br.readLine());
				String s1="";
				if(stk.countTokens()>3)
				{
					for(int i=0; 0<=stk.countTokens()-4; i++)
						s1 = s1+stk.nextToken()+" ";
					s1 = s1 + stk.nextToken();
				}
				else
					s1 = stk.nextToken();
				entries.add(new Entry(s1,stk.nextToken(),stk.nextToken()));
			}
		} catch (Exception e) 
		{
			throw new SelectorException("Error in file "+f.getName());
		}
	}
	public LinkedList<Entry> listCompartments()
	{
		compartments.clear();
		runs.clear();
		
		LinkedList<Entry> result = new LinkedList<Entry>();
		for(int i=0; i<entries.size(); i++)
		{
			if((!containsCompartment(result, entries.get(i)))&&entries.get(i).filename.compareTo(selected_model)==0)
			{
				result.add(entries.get(i));
				compartments.addElement(entries.get(i).compartment);
			}
		}
		return result;		
	}

	public LinkedList<Entry> listFilenames()
	{
		models.clear();
		compartments.clear();
		runs.clear();
		LinkedList<Entry> result = new LinkedList<Entry>();
		for(int i=0; i<entries.size(); i++)
		{
			if(!containsFilename(result, entries.get(i)))
			{
				result.add(entries.get(i));
				models.addElement(entries.get(i).filename);
			}
			
		}
		return result;
	}
	public LinkedList<Entry> listRuns()
	{
		runs.clear();
		LinkedList<Entry> result = new LinkedList<Entry>();
		for(int i=0; i<entries.size(); i++)
		{
			if(!containsRun(result, entries.get(i))
					&&entries.get(i).filename.compareTo(selected_model)==0
					&&entries.get(i).compartment.compareTo(selected_compartment)==0)
			{
				result.add(entries.get(i));
				runs.addElement(entries.get(i).run);
			}
		}
		return result;
	}	
	private boolean containsFilename(LinkedList<Entry> list, Entry e)
	{
		for(int i=0; i<list.size(); i++)
		{
			if(list.get(i).filename.compareTo(e.filename)==0)
				return true;
		}
		return false;
	}
	private boolean containsCompartment(LinkedList<Entry> list, Entry e)
	{
		for(int i=0; i<list.size(); i++)
		{
			if(list.get(i).compartment.compareTo(e.compartment)==0)
				return true;
		}
		return false;
	}

	private boolean containsRun(LinkedList<Entry> list, Entry e)
	{
		for(int i=0; i<list.size(); i++)
		{
			if(list.get(i).run.compareTo(e.run)==0)
				return true;
		}
		return false;
	}
	public void selectedModel(String s)
	{
		selected_model = s;
		listCompartments();
	}

	public void addToSelection(String s1, String s2, String s3)
	{
		Entry e = null;
		//---- find the element in the entries list
		for(int i=0; i<entries.size(); i++)
		{
			if(entries.get(i).filename.compareTo(s1)==0&&
					entries.get(i).compartment.compareTo(s2)==0&&
					entries.get(i).run.compareTo(s3)==0)
			{
				e = entries.get(i);
			}
		}
		if(!final_selection.contains(e)&&e!=null)
			final_selection.add(e);
		selections.clear();
		for(int i=0; i<final_selection.size(); i++)
			selections.addElement(final_selection.get(i).filename+","+final_selection.get(i).compartment+","+final_selection.get(i).run);
	}
	public void selectedCompartment(String comp) 
	{
		selected_compartment = comp;
		selected_run = null;
		listRuns();
	}
	
	public void selectedRun(String run)
	{
		selected_run = run;
	}
	
	public void selectedSelection(String s)
	{
		System.out.println(s);
	}
	
	public void removeFromSelection(int i)
	{
		
		selections.clear();
		final_selection.remove(i);
		for(int j=0; j<final_selection.size(); j++)
			selections.addElement(final_selection.get(j).filename+","+final_selection.get(j).compartment+","+final_selection.get(j).run);
	}

	public void ok() 
	{
		this.gui.dispose();
		if(!final_selection.isEmpty())
			kernel.newGraph(final_selection);
		else
			this.gui.dispose();
	}

	public void cancel() {
		this.gui.dispose();
	}

}
