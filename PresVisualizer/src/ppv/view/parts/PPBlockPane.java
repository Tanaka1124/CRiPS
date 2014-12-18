package ppv.view.parts;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

import pres.loader.model.IPLFileProvider;

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
	private JLabel imgLabel = new JLabel();
	private JScrollPane scrollpane = new JScrollPane();

	boolean canUpDownf = true;

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
				.getAbsolutePath().toString()).getParentFile(), BLOCKPRINT_DIR);

		imgLabel.setIcon(new ImageIcon(new File(blockPrintDir, current
				.getAsLong() + ".jpg").getAbsolutePath()));

		imgLabel.setVerticalAlignment(JLabel.TOP);
		imgLabel.setHorizontalAlignment(JLabel.LEFT);

		// TODO とりあえず表示はできるけど　Panelのサイズを取得してスクロールする部分がうまくいかないので固定値で．
		scrollpane.setPreferredSize(new Dimension(600, 400));

		JViewport view = new JViewport();
		view.add(imgLabel);

		scrollpane.setViewport(view);
		scrollpane.getViewport().setBackground(Color.BLACK);

		add(scrollpane, BorderLayout.PAGE_START);

	}

	public void refresh() {
		current = timeModel.getTime();
		System.out.println("blockpane refresh = " + current.getAsLong());

		File path = new File(blockPrintDir, current.getAsLong() + ".jpg");
		if (path.exists()) {
			imgLabel.setIcon(new ImageIcon(path.getAbsolutePath()));
		}

	}

	class HandScrollListener extends MouseAdapter {// TODO　ドラッグアンドドロップでの移動の実装
		private final Cursor defCursor = Cursor
				.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
		private final Cursor hndCursor = Cursor
				.getPredefinedCursor(Cursor.HAND_CURSOR);
		private final Point pp = new Point();

		@Override
		public void mouseDragged(MouseEvent e) {
			JViewport vport = (JViewport) e.getComponent();
			Point cp = e.getPoint();
			Point vp = vport.getViewPosition(); // =
												// SwingUtilities.convertPoint(vport,
												// 0, 0, label);
			vp.translate(pp.x - cp.x, pp.y - cp.y);
			if (canUpDownf) {
				imgLabel.scrollRectToVisible(new Rectangle(vp, vport.getSize()));
			} else {
				vport.setViewPosition(vp);
			}
			pp.setLocation(cp);
		}

		@Override
		public void mousePressed(MouseEvent e) {
			e.getComponent().setCursor(hndCursor);
			pp.setLocation(e.getPoint());
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			e.getComponent().setCursor(defCursor);
		}
	}

}
