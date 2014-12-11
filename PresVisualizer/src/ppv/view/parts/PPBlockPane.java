package ppv.view.parts;

import javax.swing.JLabel;
import javax.swing.JPanel;

import pres.loader.model.IPLFileProvider;
import pres.loader.model.PLFile;
import clib.common.time.CTime;
import clib.view.timeline.model.CTimeModel;

public class PPBlockPane extends JPanel{
	
	private IPLFileProvider model;
	private CTimeModel timeModel;
	
	private JLabel text = new JLabel();
	
	public PPBlockPane(IPLFileProvider model, CTimeModel timeModel) {
		this.model = model;
		this.timeModel = timeModel;
		
		initialize();
	}
	
	private void initialize(){
		add(text);
		
	}
	
	public void refresh() {
		CTime current = timeModel.getTime();

		PLFile target = model.getFile(current);
		if (target == null) {
			return;
		}
		String sourceText = target.getSource(current);
		System.out.println(sourceText);
		text.setText(sourceText);
	}
}
