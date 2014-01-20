package src.coco.view;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

import javax.swing.JButton;

import org.jfree.chart.ChartColor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import ppv.app.datamanager.PPProjectSet;
import src.coco.model.CCCompileErrorKind;
import clib.common.filesystem.CDirectory;

public class CCErrorElementButton2 extends JButton {

	/**
	 * minigraph��\������ chartPanel��ActionListener�ɑΉ����Ă��Ȃ��̂ŁAMouseListener�Ŏ���
	 */

	private static final long serialVersionUID = 1L;

	private CCCompileErrorKind list;

	private CDirectory libDir;
	private CDirectory base;
	private PPProjectSet ppProjectSet;

	private JFreeChart chart;
	private ChartPanel chartpanel;
	private ArrayList<CCGraphFrame> graphframes = new ArrayList<CCGraphFrame>();

	public CCErrorElementButton2(int buttonWidth, int buttonHeight,
			CCCompileErrorKind list, CDirectory libDir, CDirectory base,
			PPProjectSet ppProjectSet) {
		this.list = list;
		this.libDir = libDir;
		this.base = base;
		this.ppProjectSet = ppProjectSet;

		// windowsize �ύX���Ƀ{�^���T�C�Y��ύX����
		super.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
		super.setLayout(null);
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				chartpanel.setBounds(1, 1, getWidth(), getHeight());
				validate();
			}
		});

		makeGraph();
	}

	private void makeGraph() {
		// ���{�ꂪ�����������Ȃ��e�[�}(�t�H���g�w��Ŕ������̂Ŏg��Ȃ�)
		// ChartFactory.setChartTheme(StandardChartTheme.createLegacyTheme());

		// �O���t�f�[�^��ݒ肷��
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (int i = 0; i < list.getErrors().size(); i++) {
			dataset.addValue(list.getErrors().get(i).getCorrectionTime(),
					"�C������", Integer.toString(i + 1));
		}

		// TODO: �O���t�̐��� message����������ꍇ�A�Z�����鏈�����ǂ����邩
		// 10�������炢�ŋ�؂��āAToolTip�ŕ⊮�����i���l����
		String message = list.getMessage();
		if (list.getMessage().length() > 10) {
			message = message.substring(0, 9) + "...";
		}

		chart = ChartFactory.createLineChart(message, "�C����", "�C������", dataset,
				PlotOrientation.VERTICAL, false, false, false);
		// �t�H���g�w��i���������΍�j
		chart.getTitle().setFont(new Font("Font2DHandle", Font.PLAIN, 20));

		// �w�i�F�Z�b�g
		chart.setBackgroundPaint(new CCGraphBackgroundColor().graphColor(list
				.getRare()));

		// ���̐ݒ�̊֌W����C���Plot�N���X������
		CategoryPlot plot = chart.getCategoryPlot();

		// y���̐ݒ� �E ���͐����l�̂�
		NumberAxis numberAxis = (NumberAxis) plot.getRangeAxis();
		numberAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		numberAxis.setVerticalTickLabels(false);
		numberAxis.setAutoRangeStickyZero(true);
		numberAxis.setRangeWithMargins(0, 120);
		numberAxis.setLabelFont(new Font("Font2DHandle", Font.PLAIN, 16));

		// x���̐ݒ�
		CategoryAxis domainAxis = (CategoryAxis) plot.getDomainAxis();
		domainAxis.setLabelFont(new Font("Font2DHandle", Font.PLAIN, 16));

		// �v���b�g�̐ݒ�
		LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot
				.getRenderer();
		renderer.setSeriesPaint(0, ChartColor.RED);
		renderer.setSeriesStroke(0, new BasicStroke(1));
		renderer.setSeriesShapesVisible(0, true);

		// �O���t�pPanel�쐬
		chartpanel = new ChartPanel(chart);
		chartpanel.setBounds(1, 1, getWidth(), getHeight());

		// �}�E�X�N���b�N�ŐV�����E�B���h�E�ŃO���t���J��
		chartpanel.addChartMouseListener(new ChartMouseListener() {
			@Override
			public void chartMouseMoved(ChartMouseEvent arg0) {

			}

			@Override
			public void chartMouseClicked(ChartMouseEvent arg0) {
				CCGraphFrame graphframe = new CCGraphFrame(list, libDir, base,
						ppProjectSet);
				graphframes.add(graphframe);
				graphframe.setVisible(true);
			}
		});

		// TODO: ToolTip�\���ł��Ȃ����
		chartpanel.setToolTipText(list.getErrors().size() + " : "
				+ list.getMessage());
		chartpanel.setDisplayToolTips(true);

		add(chartpanel);
	}

	public void changeLockedRange() {
		CategoryPlot plot = chart.getCategoryPlot();
		NumberAxis numberAxis = (NumberAxis) plot.getRangeAxis();
		numberAxis.setRangeWithMargins(0, 120);
	}

	public void changeAutoRange() {
		CategoryPlot plot = chart.getCategoryPlot();
		NumberAxis numberAxis = (NumberAxis) plot.getRangeAxis();
		numberAxis.setAutoRange(true);
	}

	public void closeGraphFrames() {
		for (CCGraphFrame frame : graphframes) {
			frame.projectViewerFrameClose();
			frame.dispose();
		}
	}
}