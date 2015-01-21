/*
 * PPMetricsPane.java
 * Created on 2011/06/09
 * Copyright(c) 2011 Yoshiaki Matsuzawa, Shizuoka University. All rights reserved.
 */
package ppv.view.parts;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import pres.loader.model.IPLUnit;
import pres.loader.utils.PLLogSelecters;
import clib.view.panels.CVerticalFlowLayout;
import clib.view.timeline.model.CTimeModel;

/**
 * @author macchan
 */
public class PPUtilitiesPane extends JPanel {

	private static final long serialVersionUID = 1L;

	private CTimeModel timeModel;
	private IPLUnit unit;
	String[] combodata = { "ALL", "CONNECTED",
			"DISCONNECTED", "CONNECT_MISSED",
			"ADDED" };
	JComboBox<String> combo = new JComboBox<String>(combodata);
	private JPanel northPanel;
	JPanel bEPointPane;
	CardLayout layout;
	PPCheckPointPane blockEditPointPane;
	PPCheckPointPane blockEditPointPaneConnected;
	PPCheckPointPane blockEditPointPaneDisconnected;
	PPCheckPointPane blockEditPointPaneConnectmissed;
	PPCheckPointPane blockEditPointPaneAdded;

	/**
	 * @param timePane
	 * @param project
	 */
	public PPUtilitiesPane(CTimeModel timeModel, IPLUnit unit) {
		this.timeModel = timeModel;
		this.unit = unit;
		initialize();
	}

	public void initialize() {
		// setLayout(new CVerticalFlowLayout());
		setLayout(new BorderLayout());
		JSplitPane splitter = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		splitter.setResizeWeight(0.5);
		add(splitter);

		// LeftComponent
		northPanel = new JPanel();
		splitter.setLeftComponent(new JScrollPane(northPanel));
		northPanel.setLayout(new CVerticalFlowLayout());

		// PPCheckPointPane allEventPane = new PPCheckPointPane(timeModel, unit,
		// PLLogSelecters.ALL);
		// allEventPane.setBorder(BorderFactory
		// .createTitledBorder("AllEventPoint"));
		// allEventPane.setCurrentComponent(new PPRunPane(timeModel, unit));
		// northPanel.add(allEventPane);

		// PPCheckPointPane eventPane = new PPCheckPointPane(timeModel, unit,
		// PLLogSelecters.COMPILE_RUN);
		// eventPane.setBorder(BorderFactory.createTitledBorder("EventPoint"));
		// eventPane.setCurrentComponent(new PPRunPane(timeModel, unit));
		// northPanel.add(eventPane);

		PPCheckPointPane runpointPane = new PPCheckPointPane(timeModel, unit,
				"START_RUN");
		runpointPane.setBorder(BorderFactory.createTitledBorder("RunPoint"));
		northPanel.add(runpointPane);
		runpointPane.setCurrentComponent(new PPRunPane(timeModel, unit));

		PPCheckPointPane compilepointPane = new PPCheckPointPane(timeModel,
				unit, "COMPILE");
		compilepointPane.setBorder(BorderFactory
				.createTitledBorder("CompilePoint"));
		northPanel.add(compilepointPane);

		PPCheckPointPane editPointPane = new PPCheckPointPane(timeModel, unit,
				PLLogSelecters.TEXTEDIT);
		editPointPane.setBorder(BorderFactory.createTitledBorder("EditPoint"));
		northPanel.add(editPointPane);

		
		
		blockEditPointPane = new PPCheckPointPane(timeModel,
				unit, PLLogSelecters.BLOCKEDIT_BIVI);
		blockEditPointPane.setBorder(BorderFactory
				.createTitledBorder("BlockEditPoint"));
		
		blockEditPointPaneConnected = new PPCheckPointPane(
				timeModel, unit, PLLogSelecters.BLOCKEDIT_BIVI_CONNECTED);
		blockEditPointPaneConnected.setBorder(BorderFactory
				.createTitledBorder("BlockEditPoint-Connected"));
		
		blockEditPointPaneDisconnected = new PPCheckPointPane(
				timeModel, unit, PLLogSelecters.BLOCKEDIT_BIVI_DISCONNECTED);
		blockEditPointPaneDisconnected.setBorder(BorderFactory
				.createTitledBorder("BlockEditPoint-Disconnected"));
		
		blockEditPointPaneConnectmissed = new PPCheckPointPane(
				timeModel, unit, PLLogSelecters.BLOCKEDIT_BIVI_CONNECT_MISSED);
		blockEditPointPaneConnectmissed.setBorder(BorderFactory
				.createTitledBorder("BlockEditPoint-ConnectMissed"));
		
		blockEditPointPaneAdded = new PPCheckPointPane(
				timeModel, unit, PLLogSelecters.BLOCKEDIT_BIVI_ADDED);
		blockEditPointPaneAdded.setBorder(BorderFactory
				.createTitledBorder("BlockEditPoint-Added"));

		combo.setMaximumRowCount(5);
		bEPointPane = new JPanel();
		layout = new CardLayout();

		bEPointPane.setLayout(layout);
		
		bEPointPane.add(blockEditPointPane,"all");
		bEPointPane.add(blockEditPointPaneConnected,"connected");
		bEPointPane.add(blockEditPointPaneDisconnected,"disconneced");
		bEPointPane.add(blockEditPointPaneConnectmissed,"connectMissed");
		bEPointPane.add(blockEditPointPaneAdded,"add");
		
		combo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(combo.getSelectedIndex());
				if (combo.getSelectedIndex() != -1) {
					switch (combo.getSelectedIndex()) {
					case 0:
						layout.show(bEPointPane,"all");
						break;
					case 1:
						layout.show(bEPointPane,"connected");
						break;
					case 2:
						layout.show(bEPointPane,"disconneced");
						break;
					case 3:
						layout.show(bEPointPane,"connectMissed");
						break;
					case 4:
						layout.show(bEPointPane,"add");
						break;

					}
				}

			}
		});
		northPanel.add(combo);

		northPanel.add(bEPointPane);

		// RightComponent
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new CVerticalFlowLayout());
		splitter.setRightComponent(new JScrollPane(southPanel));

		PPMetricsPane metricsPane = (new PPMetricsPane(timeModel, unit));
		metricsPane.setBorder(BorderFactory.createTitledBorder("Metrics"));
		metricsPane.setPreferredSize(new Dimension(50, 320));
		southPanel.add(metricsPane);

		PPCompileResultPane compileResultPane = new PPCompileResultPane(
				timeModel, unit);
		compileResultPane.setBorder(BorderFactory
				.createTitledBorder("CompileResult"));
		compileResultPane.setPreferredSize(new Dimension(50, 150));
		southPanel.add(new JScrollPane(compileResultPane));

	}
}
