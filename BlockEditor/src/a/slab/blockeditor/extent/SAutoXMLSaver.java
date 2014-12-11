package a.slab.blockeditor.extent;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import clib.view.dialogs.CErrorDialog;
import util.ChangeExtension;
import workspace.Workspace;
import workspace.WorkspaceEvent;
import workspace.WorkspaceListener;
import a.slab.blockeditor.SBlockEditor;

public class SAutoXMLSaver implements WorkspaceListener {
	public void workspaceEventOccurred(WorkspaceEvent event) {
		if (event.getEventType() == 5 || event.getEventType() == 6) { //BLOCKS_CONNECTED || BLOCKS_DISCONNECTED
		//		if (event.getEventType() == 5) {
			try {
				logging2Xml();
			} catch (Exception ex) {
				CErrorDialog.show(null, "エラーが発生しました．", ex);
			}

		}
	}

	private void logging2Xml() throws Exception { //convertToJava0から借用&改造
		String saveString = Workspace.getInstance().getWorkSpaceController()
				.getSaveString();//xmlファイルの内容取得
		String xmlFileName = ChangeExtension.changeToXmlExtension(Workspace
				.getInstance().getWorkSpaceController().getSelectedJavaFile());//XMLファイルのパス取得

		String[] s = xmlFileName.split("[/\\\\]");//ファイル名取得  //File をnewするより早いかも
		String xmlLogFileName = s[s.length - 1];

		File xmlLogFilePath = new File(new File(//.pres2の中にSelctedJavaFileName.xmlというフォルダを生成
				new File(Workspace.getInstance().getWorkSpaceController()
						.getSelectedJavaFile()).getParent(), ".pres2"),
				xmlLogFileName);

		if (!xmlLogFilePath.exists()) {//無ければ生成
			xmlLogFilePath.mkdirs();
		}

		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(String.valueOf(new File(xmlLogFilePath,
						String.valueOf(System.currentTimeMillis() + ".xml")))),
				SBlockEditor.ENCODING_BLOCK_XML));//timeMillis.xmlで保存

		bw.write(saveString);
		bw.flush();
		bw.close();

	}

}
