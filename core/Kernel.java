package core;

import parser.*;
import selector.*;
//import sun.awt.WindowClosingListener;
import graph.*;
import help.*;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;
import java.net.URL;
import java.util.LinkedList;
import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.HyperlinkEvent;
import javax.swing.text.BadLocationException;

public class Kernel extends JFrame implements CaretListener, Keywords, WindowListener
{
	JTextArea editor_area;
	Console console_area;
	JScrollPane editor_pane, console_pane;
	JPanel south, msg_panel;
	JLabel line_label;
	Container root;
	Help help;
	Selector selector;
	FileSystemInterface filesystem;

	Menu menu;
	ToolBar toolbar;
	
	public Kernel() throws IOException
	{
		init();
		filesystem = new FileSystemInterface(this);
		help = new Help();
		menu = new Menu(this);
		toolbar = new ToolBar(this);
		Rectangle screensize = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		screensize.height -= 26;
		this.setBounds(screensize);
		this.setVisible(true);
		this.addWindowListener(this);
	}
	
	private void init()
	{
		this.setTitle(EDITOR_TITLE);
		root = this.getContentPane();
		
		
		
		editor_area = new JTextArea();
		editor_pane = new JScrollPane(editor_area);
		editor_area.addCaretListener(this);
		editor_area.setFont(new Font("Courier New",Font.PLAIN,16));
	
		console_area = new Console(this);
		console_area.setEditable(false);
		console_pane = new JScrollPane(console_area);
		console_pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		console_pane.setPreferredSize(new Dimension(100,100));
		south = new JPanel(new BorderLayout());
		msg_panel = new JPanel(new BorderLayout());
		line_label = new JLabel(" Line:");
		line_label.setPreferredSize(new Dimension(60,20));
		msg_panel.add(line_label, BorderLayout.WEST);
		
		root.add(editor_pane,BorderLayout.CENTER);
		root.add(south,BorderLayout.SOUTH);
		south.add(console_pane,BorderLayout.CENTER);
		south.add(msg_panel, BorderLayout.SOUTH);
		
	}
	
	public static void main(String[] args) throws IOException 
	{
		new Kernel();
	}

	public void run()
	{
		if(!filesystem.saveFile())
			return;
		console_area.append("\nComputing model...");
		String result = ModelComputationProcess.compute(filesystem.filename);
		
		if(result ==null)
			console_area.append("\ndone!");
		else
			console_area.append("\n"+result);		
	}



	public void addPow() 
	{
		String text = "POW(,)";
		int pos = editor_area.getCaretPosition();
		editor_area.insert(text,pos);
		editor_area.setCaretPosition(pos+4);
		editor_area.grabFocus();						
	}

	public void addLog() 
	{		
		String text = "LOG(,)";
		int pos = editor_area.getCaretPosition();
		editor_area.insert(text,pos);
		editor_area.setCaretPosition(pos+4);
		editor_area.grabFocus();
	}

	public void addExp()
	{
		String text = "EXP()";
		int pos = editor_area.getCaretPosition();
		editor_area.insert(text,pos);
		editor_area.setCaretPosition(pos+4);
		editor_area.grabFocus();				
	}

	public void addComment() 
	{
		String text = "# ";
		int pos = editor_area.getCaretPosition();
		editor_area.insert(text,pos);
		editor_area.setCaretPosition(pos+text.length());
		editor_area.grabFocus();				
	}

	public void addHeader() 
	{
		String text="MODEL=\"\"\n" +
					"VERSION=\"\"\n" +
					"OUTPUT=\"\"\n" +
					"TYPE=\n" +
					"RUNS=\n" +
					"STEPS=\n" +
					"PRECISION=\n" +
					"\n";
		int pos = editor_area.getCaretPosition();
		editor_area.insert(text,pos);
		editor_area.setCaretPosition(pos+7);
		editor_area.grabFocus();
	}

	public void addEquation() 
	{
		String text = "DELTA =";
		int pos = editor_area.getCaretPosition();
		editor_area.insert(text,pos);
		editor_area.setCaretPosition(pos+text.length()-1);
		editor_area.grabFocus();		
	}

	public void addCompartment()
	{
		String text = "COMPARTMENT =";
		int pos = editor_area.getCaretPosition();
		editor_area.insert(text,pos);
		editor_area.setCaretPosition(pos+text.length()-1);
		editor_area.grabFocus();
	}
	public void addSimpleParameter() 
	{
		String text = "PARAMETER =";
		int pos = editor_area.getCaretPosition();
		editor_area.insert(text,pos);
		editor_area.setCaretPosition(pos+text.length()-1);
		editor_area.grabFocus();
	}

	public void addComplexParameter() 
	{
		String text = "PARAMETER = STD=";
		int pos = editor_area.getCaretPosition();
		editor_area.insert(text,pos);
		editor_area.setCaretPosition(pos+10);
		editor_area.grabFocus();
	}
	public void caretUpdate(CaretEvent arg0) 
	{
		if(arg0.getSource()==editor_area)
		{
			printCaretLine();
		}
	}

	private void printCaretLine() 
	{
		int line=0;
		try {
			line = editor_area.getLineOfOffset(editor_area.getCaretPosition());
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		line_label.setText(" Line: "+(line+1));
		this.setVisible(true);
	}
	
	public void help(HyperlinkEvent arg0)
	{
		try{
			String c = Kernel.class.getResource(HELP_FILE).toString()+arg0.getDescription();
			help.setPage(new URL(c));
		} catch (Exception e) {
			console_area.append(HELP_ERR_MSG);
		}		
	}
	public void help() 
	{
		try {
			help.setDefaultPage();
		} catch (IOException e) {
			console_area.append(HELP_ERR_MSG);
		}
	}

	public void openSelector() 
	{
		selector = new Selector(this, RESULT_DIR);
	}
	public void newGraph(LinkedList<Entry> selection)
	{
		selector = null;
		GraphFactory.newGraph(selection,"");
	}

	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void windowClosing(WindowEvent arg0) 
	{
		System.out.println("closing window");
		filesystem.closeFile();
		System.exit(0);
		
	}

	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
