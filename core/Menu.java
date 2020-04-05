package core;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class Menu implements ActionListener
{
	Kernel kernel;
	JMenuItem open_file, new_file, save_file, save_as_file, close_file, run, graph, help;
	public Menu(Kernel kernel)
	{
		this.kernel = kernel;
		init();
	}
	
	public void init()
	{
		JMenuBar menubar = new JMenuBar();
		kernel.setJMenuBar(menubar);
		
		//--- FILE MENU
		JMenu file_menu = new JMenu("File");
		menubar.add(file_menu);
		open_file = new JMenuItem("Open");
		open_file.addActionListener(this);
		file_menu.add(open_file);
		open_file.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		
		new_file = new JMenuItem("New");
		new_file.addActionListener(this);
		file_menu.add(new_file);
		
		save_file = new JMenuItem("Save");
		save_file.addActionListener(this);
		file_menu.add(save_file);
		save_file.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		
		save_as_file= new JMenuItem("Save as");
		save_as_file.addActionListener(this);
		file_menu.add(save_as_file);

		close_file= new JMenuItem("Close");
		close_file.addActionListener(this);
		file_menu.add(close_file);
		
		JMenu model = new JMenu("Model");
		menubar.add(model);
		run = new JMenuItem("Run");
		run.addActionListener(this);
		model.add(run);
		
		graph = new JMenuItem("Graph");
		graph.addActionListener(this);
		model.add(graph);
		run.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_R, ActionEvent.CTRL_MASK));
		
		JMenu help_menu = new JMenu("Help");
		menubar.add(help_menu);
		help = new JMenuItem("Help");
		help.addActionListener(this);
		help_menu.add(help);
		help.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, ActionEvent.CTRL_MASK));

	}

	public void actionPerformed(ActionEvent arg0) 
	{
		if(arg0.getSource()==new_file)
			kernel.filesystem.newFile();
		if(arg0.getSource()==open_file)
			kernel.filesystem.openFile();
		if(arg0.getSource()==save_file)
			kernel.filesystem.saveFile();
		if(arg0.getSource()==save_as_file)
			kernel.filesystem.saveAsFile();
		if(arg0.getSource()==close_file)
			kernel.filesystem.closeFile();
		if(arg0.getSource()==run)
			kernel.run();
		if(arg0.getSource()==help)
			kernel.help();
		if(arg0.getSource()==graph)
			kernel.openSelector();
	}

	
}
