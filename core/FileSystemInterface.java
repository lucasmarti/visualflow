package core;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class FileSystemInterface implements Keywords
{
	Kernel kernel;
	String filename;
	
	public FileSystemInterface(Kernel kernel)
	{
		this.kernel = kernel;
	}
	public void openFile()
	{
		JFileChooser jf = new JFileChooser(MODEL_DIR);
		jf.setFileFilter(new ModFileFilter());
		int i = jf.showOpenDialog(kernel);
		if(i==JFileChooser.CANCEL_OPTION)
			return;
		else if(i==JFileChooser.ERROR_OPTION)
			return;
		else
			filename = jf.getSelectedFile().getAbsolutePath();

		String line = "";
		String tmp;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
			while((tmp=br.readLine())!=null)
				line = line + tmp + "\n";
			kernel.editor_area.setText(line);
		}catch(Exception e)
		{
			kernel.console_area.append("Can not open file: "+filename);
		}
	}
	public void closeFile() 
	{
		int n = JOptionPane.showConfirmDialog(kernel,"Close without saving?","",1);
		if(n==0)
			kernel.editor_area.setText("");
		else if(n==1)
		{
			if(filename==null)
			{
				JFileChooser jf = new JFileChooser(MODEL_DIR);
				jf.setFileFilter(new ModFileFilter());
				int i = jf.showSaveDialog(kernel);
				if(i==JFileChooser.CANCEL_OPTION)
					return;
				else if(i==JFileChooser.ERROR_OPTION)
					return;
				else
					filename = jf.getSelectedFile().getAbsolutePath();
			}
			try {
				PrintStream p = new PrintStream(filename);
				p.print(kernel.editor_area.getText());
			} catch (IOException e) {
					kernel.console_area.append("Can not write to file: "+filename);
			}
			kernel.editor_area.setText("");
		}
		else
			return;
	}

	public void saveAsFile() 
	{
		JFileChooser jf = new JFileChooser(MODEL_DIR);
		jf.setFileFilter(new ModFileFilter());
		int i = jf.showSaveDialog(kernel);
		if(i==JFileChooser.CANCEL_OPTION)
			return;
		else if(i==JFileChooser.ERROR_OPTION)
			return;
		else
			filename = jf.getSelectedFile().getAbsolutePath();
		try {
			PrintStream p = new PrintStream(filename);
			p.print(kernel.editor_area.getText());
		} catch (IOException e) {
			kernel.console_area.append("Can not write to file: "+filename);
		}
	}

	public void newFile() 
	{
		int n = JOptionPane.showConfirmDialog(kernel,"Save current file?","",1);
		if(n==1)
			kernel.editor_area.setText("");
		else if(n==0)
		{
			if(filename==null)
			{
				JFileChooser jf = new JFileChooser(MODEL_DIR);
				jf.setFileFilter(new ModFileFilter());
				int i = jf.showSaveDialog(kernel);
				if(i==JFileChooser.CANCEL_OPTION)
					return;
				else if(i==JFileChooser.ERROR_OPTION)
					return;
				else
					filename = jf.getSelectedFile().getAbsolutePath();
			}
			try {
				PrintStream p = new PrintStream(filename);
				p.print(kernel.editor_area.getText());
			} catch (IOException e) {
				kernel.console_area.append("Can not write to file: "+filename);
			}
			kernel.editor_area.setText("");
		}
		else
			return;
		
	}

	public boolean saveFile()
	{
		if(filename==null)
		{
			JFileChooser jf = new JFileChooser(MODEL_DIR);
			jf.setFileFilter(new ModFileFilter());
			int i = jf.showSaveDialog(kernel);
			if(i==JFileChooser.CANCEL_OPTION)
				return false;
			else if(i==JFileChooser.ERROR_OPTION)
				return false;
			else
				filename = jf.getSelectedFile().getAbsolutePath();
		}
		try {
			PrintStream p = new PrintStream(filename);
			p.print(kernel.editor_area.getText());
			return true;
		} catch (IOException e) {
			kernel.console_area.append("Can not write to file: "+filename);
			return true;
		}
	}

}
