package ppv.view.parts;

import java.awt.BorderLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import pres.loader.logmodel.PLLog;
import pres.loader.model.IPLFileProvider;
import pres.loader.model.IPLUnit;
import pres.loader.model.PLFile;
import clib.common.model.ICModelChangeListener;
import clib.common.time.CTime;
import clib.common.time.CTimeOrderedList;
import clib.view.timeline.model.CTimeModel;

public class PPSourceMetricsPane extends JPanel {

	private static final long serialVersionUID = 1L;

	private IPLUnit unit;
	private CTimeModel timeModel;

	JSplitPane split = new JSplitPane();
	private PPSourcePane sourcePane;
	private IPLUnit selectedUnit;
	private PPBlockPane blockPane;

	/**
	 * @param timeModel
	 */
	public PPSourceMetricsPane(IPLUnit unit, CTimeModel timeModel) {
		this.unit = unit;
		this.timeModel = timeModel;
		initialize();
	}

	private void initialize() {
		setLayout(new BorderLayout());
		add(split);
		sourcePane = new PPSourcePane(new IPLFileProvider() {
			@Override
			public PLFile getFile(CTime time) {
				if (selectedUnit != null) {
					return selectedUnit.getFile(time);
				}
				return unit.getFile(time);
			}
		}, timeModel);

		blockPane = new PPBlockPane(new IPLFileProvider() {
			@Override
			public PLFile getFile(CTime time) {
				if (selectedUnit != null) {
					return selectedUnit.getFile(time);
				}
				return unit.getFile(time);
			}
		}, timeModel);

		timeModel.addModelListener(new ICModelChangeListener() {

			@Override
			public void modelUpdated(Object... args) {
//				System.out.println("blockpane current Img Stamp   = "
//						+ blockPane.getCurrentImgStamp());
//				System.out.println("sourcePane current text Stamp = "
//						+ sourcePane.getCurrentTextEditLogTimestamp()+"\n");

					if (sourcePane.getCurrentTextEditLogTimestamp() > blockPane.getCurrentImgStamp()) {
						split.setLeftComponent(sourcePane);
					} else {
						split.setLeftComponent(blockPane);
					}
			}
		});

		split.setLeftComponent(blockPane);

		PPUtilitiesPane utilitiesPane = new PPUtilitiesPane(timeModel, unit);
		split.setRightComponent(utilitiesPane);
		split.setResizeWeight(0.75);

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				split.setDividerLocation(0.75);
			}
		});
	}

	public void setSelectedUnit(IPLUnit selectedUnit) {
		if (this.selectedUnit != selectedUnit) {
			this.selectedUnit = selectedUnit;

			sourcePane.refresh();
			blockPane.refresh();
		}
	}

}
