package ppv.view.parts;

import java.io.File;

import javax.swing.JLabel;
import javax.swing.JPanel;

import pres.loader.model.IPLFileProvider;
import pres.loader.model.IPLUnit;
import clib.common.model.ICModelChangeListener;
import clib.common.time.CTime;
import clib.view.timeline.model.CTimeModel;

public class PPBlockPane extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IPLFileProvider model;
	private CTimeModel timeModel;
	private CTime current;
	private File blockPrintDir;
	private JLabel text = new JLabel();
	
	private static String BLOCKPRINT_DIR = "BlockPrint";
	
	public PPBlockPane(IPLFileProvider model, CTimeModel timeModel) {
		this.model = model;
		this.timeModel = timeModel;
		initialize();
	}

	private void initialize() {
		timeModel.addModelListener(new ICModelChangeListener() {
			public void modelUpdated(Object... args) {
				refresh();
			}
		});
		
		current = timeModel.getTime();
		
		blockPrintDir = new File(new File(model.getFile(current).getDir()
				.getAbsolutePath().toString()).getParentFile(),BLOCKPRINT_DIR);

		add(text);

	}

	public void refresh() {
		current = timeModel.getTime();

		// PLFile target = model.getFile(current);
		// if (target == null) {
		// return;
		// }
		// String sourceText = target.getSource(current);
		// System.out.println(current.getAsLong());
		// System.out.println("current = " + current);
		// System.out.println("  getName = " + unit.getName());
		// System.out.println("  getProject = " + unit.getProject());
		// System.out.println("  getPath = " + unit.getPath());
		// System.out.println("  getRange = " + unit.getRange());
		// System.out.println("  getStart = " + unit.getStart());
		// System.out.println("  getEnd = " + unit.getEnd());
		// System.out.println("  getMaxLineCount = " + unit.getMaxLineCount());
		// System.out.println("  getCurrentLineCount = "
		// + unit.getLineCount(current));

		System.out.println("path = " + blockPrintDir);

		text.setText(current.getAsLong() + "");
	}
}
