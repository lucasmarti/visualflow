package core;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JToolBar;

public class ToolBar implements ActionListener
{
	Kernel kernel;
	JToolBar toolbar;
	JButton pa_simple, pa_complex, compartment, equation, header, comment, pow, log, exp;
	
	public ToolBar(Kernel kernel)
	{
		this.kernel = kernel;
		init();
	}
	public void init()
	{
		toolbar = new JToolBar();
		toolbar.setRollover(true);
		kernel.root.add(toolbar,BorderLayout.NORTH);
		
		//--- SIMPLE PARAMETER
		pa_simple = new JButton("PARAMETER");
		pa_simple.addActionListener(this);
		pa_simple.setToolTipText("add a new Parameter without variance");
		toolbar.add(pa_simple);
		
		//--- COMPLEX PARAMETER
		pa_complex = new JButton("PARAMETER+");
		pa_complex.addActionListener(this);
		pa_complex.setToolTipText("add a new Parameter with Standard Deviation");
		toolbar.add(pa_complex);
		
		//--- COMPARTMENT
		compartment = new JButton("COMPARTMENT");
		compartment.addActionListener(this);
		compartment.setToolTipText("add a new Compartment");
		toolbar.add(compartment);

		//--- EQUATION
		equation = new JButton("EQUATION");
		equation.addActionListener(this);
		equation.setToolTipText("add a new Equation");
		toolbar.add(equation);

		//--- MODEL
		header = new JButton("MODEL");
		header.addActionListener(this);
		header.setToolTipText("add a new Model Skeleton");
		toolbar.add(header);

		//--- COMMENT
		comment = new JButton("# Comment");
		comment.addActionListener(this);
		comment.setToolTipText("add a Commentline");
		toolbar.add(comment);

		toolbar.addSeparator();
		//--- POW
		pow = new JButton("POW");
		pow.addActionListener(this);
		pow.setToolTipText("add a Power Operation");
		toolbar.add(pow);

		//--- EXP
		exp = new JButton("EXP");
		exp.addActionListener(this);
		exp.setToolTipText("add a Exponential Operation");
		toolbar.add(exp);

		//--- LOG
		log = new JButton("LOG");
		log.addActionListener(this);
		log.setToolTipText("add a Logarithm Operation");
		toolbar.add(log);

	}
	public void actionPerformed(ActionEvent arg0) 
	{
		if(arg0.getSource()==pa_simple)
			kernel.addSimpleParameter();
		if(arg0.getSource()==pa_complex)
			kernel.addComplexParameter();
		if(arg0.getSource()==compartment)
			kernel.addCompartment();
		if(arg0.getSource()==equation)
			kernel.addEquation();
		if(arg0.getSource()==header)
			kernel.addHeader();
		if(arg0.getSource()==comment)
			kernel.addComment();
		if(arg0.getSource()==pow)
			kernel.addPow();
		if(arg0.getSource()==log)
			kernel.addLog();
		if(arg0.getSource()==exp)
			kernel.addExp();
	}

}
