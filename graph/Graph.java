package graph;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import JSci.awt.*;
import JSci.swing.*;

public class Graph extends JFrame {
	JPanel lineGraphPanel;
	JPanel rightPanel;
	Container root;

	String title;
	double[][] values;
	Color[] series_colors;
	String[] legend_names;
	Color[] legend_colors;
	double[] x_axis;

	Random rand;
	DefaultGraph2DModel model;

	public Graph(double[][] values, String[] names, String title) {
		this.title = title;
		this.values = values;
		series_colors = new Color[names.length];
		legend_names = names;
		legend_colors = new Color[names.length];

		int m = 0;
		for (int i = 0; i < values.length; i++) {
			if (values[i].length > m) {
				m = values[i].length;
			}
		}
		x_axis = new double[m];
		for (int i = 0; i < x_axis.length; i++) {
			x_axis[i] = i + 1;
		}
		initColors();
		model = new DefaultGraph2DModel();
		model.setXAxis(x_axis);
		for (int i = 0; i < values.length; i++)
			model.addSeries(values[i]);
		initUI();
	}

	private void initUI() {
		this.setTitle("Visual Flow Graph");
		root = this.getContentPane();
		root.setLayout(new BorderLayout());

		initLineGraphPanel();
		initLegend();

		root.add(lineGraphPanel, BorderLayout.CENTER);
		root.add(rightPanel, BorderLayout.EAST);
		this.setSize(400, 400);
		this.setVisible(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

	}

	private void initLineGraphPanel() {
		JLineGraph lineGraph = new JLineGraph(model);
		lineGraph.setGridLines(true);
		lineGraph.setMarker(new Graph2D.DataMarker.Circle(5));
		for (int i = 0; i < values.length; i++) {
			lineGraph.setColor(i, series_colors[i]);
		}

		Label title_lable;
		final Font titleFont = new Font("Default", Font.BOLD, 14);
		lineGraphPanel = new JPanel(new JGraphLayout());
		title_lable = new Label(title, Label.CENTER);
		title_lable.setFont(titleFont);
		lineGraphPanel.add(title_lable, JGraphLayout.TITLE);
		lineGraphPanel.add(lineGraph, JGraphLayout.GRAPH);
	}

	private void initLegend() {
		int ratio = 2;
		rightPanel = new JPanel();
		rightPanel.setLayout(new GridLayout(ratio, 1));

		JPanel legend = new JPanel();
		legend.setLayout(new BoxLayout(legend, BoxLayout.Y_AXIS));
		legend.setBorder(new TitledBorder("Legend"));

		for (int i = 0; i < legend_names.length; i++) {
			JLabel label = new JLabel(legend_names[i]);
			label.setForeground(legend_colors[i]);
			legend.add(label);
		}
		JLabel l = new JLabel("                 ");
		legend.add(l);
		rightPanel.add(legend);
		for (int i = 1; i < ratio; i++)
			rightPanel.add(new JPanel());

	}

	private void setColor(int position, Color color) {
		if (position >= 0 && position < series_colors.length) {
			series_colors[position] = color;
		}
		if (position >= 0 && position < legend_colors.length) {
			legend_colors[position] = color;
		}
	}

	private void initColors() {
		Random rand = new Random();
		rand.setSeed(System.currentTimeMillis());
		int r, g, b;

		setColor(0, Color.RED);
		setColor(1, Color.BLUE);
		setColor(2, Color.GREEN);
		setColor(3, Color.YELLOW);
		setColor(4, Color.BLACK);
		setColor(5, Color.ORANGE);

		for (int i = 6; i < series_colors.length; i++) {
			do {
				r = rand.nextInt(255);
				g = rand.nextInt(255);
				b = rand.nextInt(255);
			} while (r + g + b < 300);
			setColor(i, new Color(r, g, b));
		}
	}
}
