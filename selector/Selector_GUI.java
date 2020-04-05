package selector;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Selector_GUI extends JDialog implements ListSelectionListener, ActionListener
{
	Selector db;
	JList list_1;
	JList list_2;
	JList list_3;
	JList list_4;
	
	JButton add_button;
	JButton remove_button;
	JButton ok;
	JButton cancel;
	
	DefaultListModel models;
	DefaultListModel compartments;
	DefaultListModel runs;
	DefaultListModel selection;
	
	Container cp;

	
	public Selector_GUI(Selector d)
	{
		db = d;
		this.models = db.models;
		this.compartments = db.compartments;
		this.runs = db.runs;
		this.selection = db.selections;
		this.setSize(740,400);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle("Compartment Selector");
		cp = getContentPane();
		cp.setLayout(null);
		
		//--- MODEL
		JLabel file_label = new JLabel("Model");
		file_label.setBounds(20,20,140,30);
		cp.add(file_label);
		initModels();
		
		//--- COMPARTMENT
		JLabel compartment_label = new JLabel("Compartment");
		compartment_label.setBounds(180,20,140,30);
		cp.add(compartment_label);
		initCompartments();
		
		//--- RUN
		JLabel run_label = new JLabel("Run");
		run_label.setBounds(340,20,140,30);
		cp.add(run_label);
		initRuns();
		
		//--- BUTTONS
		add_button = new JButton(">");
		remove_button = new JButton("<");
		
		add_button.addActionListener(this);
		remove_button.addActionListener(this);
		
		add_button.setBounds(500,120,45,25);
		remove_button.setBounds(500,160,45,25);
		cp.add(add_button);
		cp.add(remove_button);
		
		//--- SELECTION
		JLabel selection_label = new JLabel("Selection");
		selection_label.setBounds(575,20,140,30);
		cp.add(selection_label);
		initSelection();
		
		//--- OK AND CANCEL BUTTON
		ok 		= new JButton("ok");
		cancel 	= new JButton("cancel");
		ok.addActionListener(this);
		cancel.addActionListener(this);
		
		ok.setBounds(575,300,50,25);
		cancel.setBounds(635,300,80,25);
		cp.add(ok);
		cp.add(cancel);

		this.setResizable(false);
		this.setVisible(true);
	}
	public void valueChanged(ListSelectionEvent arg0) 
	{
		if(arg0.getSource()==list_1)
		{
			String s = (String)list_1.getSelectedValue();
			db.selectedModel(s);
		}
		if(arg0.getSource()==list_2 && list_2.getSelectedValue() != null)
		{
			db.selectedCompartment((String)list_2.getSelectedValue());
		}
		if(arg0.getSource()==list_3 && list_3.getSelectedValue() != null)
			db.selectedRun((String)list_3.getSelectedValue());
		/*
		if(arg0.getSource()==list_4)
			db.selectedSelection((String)list_4.getSelectedValue());
		*/		
	}
	public void actionPerformed(ActionEvent arg0) 
	{
		if(arg0.getSource()==add_button
				&&list_1.getSelectedValue()!= null
				&&list_2.getSelectedValue()!= null
				&&list_3.getSelectedValue()!= null)
		{
			db.addToSelection((String)list_1.getSelectedValue(),(String)list_2.getSelectedValue(),(String)list_3.getSelectedValue());
		}
		
		
		if(arg0.getSource()==remove_button && list_4.getSelectedValue() != null)
		{
			db.removeFromSelection(list_4.getSelectedIndex());
			
		}
		if(arg0.getSource()==ok)
			db.ok();
		if(arg0.getSource()==cancel)
			db.cancel();
	}
	public void initModels()
	{
		list_1 = new JList(models);
		list_1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_1.addListSelectionListener(this);
		
		JScrollPane list_1p = new JScrollPane(list_1);
		list_1p.setBounds(20,50,140,200);
		cp.add(list_1p);
		setVisible(true);
	}
	
	public void initCompartments()
	{
		list_2 = new JList(compartments);
		list_2.addListSelectionListener(this);
		list_2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane list_2p = new JScrollPane(list_2);
		list_2p.setBounds(180,50,140,200);
		cp.add(list_2p);
		setVisible(true);
	}
	public void initRuns()
	{
		list_3 = new JList(runs);
		list_3.addListSelectionListener(this);
		list_3.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane list_3p = new JScrollPane(list_3);
		list_3p.setBounds(340,50,140,200);
		cp.add(list_3p);
		setVisible(true);
	}
	
	public void initSelection()
	{
		list_4 = new JList(selection);
		list_4.addListSelectionListener(this);
		JScrollPane list_4p = new JScrollPane(list_4);
		list_4p.setBounds(575,50,140,200);
		cp.add(list_4p);
		setVisible(true);
	}
}
