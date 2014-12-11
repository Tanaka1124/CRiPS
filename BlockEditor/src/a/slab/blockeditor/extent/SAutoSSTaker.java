package a.slab.blockeditor.extent;

import java.awt.Rectangle;
import java.io.File;

import javax.swing.JComponent;

import renderable.RenderableBlock;
import clib.view.screenshot.CScreenShotTaker;
import workspace.BlockCanvas;
import workspace.Workspace;
import workspace.WorkspaceEvent;
import workspace.WorkspaceListener;

public class SAutoSSTaker implements WorkspaceListener {
	File javaFilePath;
	File saveDir;
	long pictureName;
	String saveDirName = "BlockPrint";
	
	public void workspaceEventOccurred(WorkspaceEvent event) {
		System.out.println(event.getEventType());
		if (event.getEventType() == 5 || event.getEventType() == 6) { //BLOCKS_CONNECTED || BLOCKS_DISCONNECTED
			javaFilePath = new File(Workspace.getInstance()
					.getWorkSpaceController().getSelectedJavaFile());
			saveDir = new File(
					new File(javaFilePath.getParent(), ".pres2"), saveDirName);//.pres2->BlockPrint->pictureName.jpg

			if (!saveDir.exists()) {  //無ければ生成
				saveDir.mkdirs();
			}

			pictureName = System.currentTimeMillis();//pictureName is TimeMillis

			while (new File(saveDir, String.valueOf(pictureName + ".jpg"))//もし被ったら困るので一応 被ったら1ミリ秒ズラす
					.exists()) {
				pictureName++;
			}
			
			createSSTaker().takeToFile(new File(saveDir, String.valueOf(pictureName)));
		}
	}

	public CScreenShotTaker createSSTaker() { //WorkspaceControllerから拝借
		Workspace ws = Workspace.getInstance();
		BlockCanvas canvas = ws.getBlockCanvas();
		JComponent comp = canvas.getCanvas();

		Rectangle r = new Rectangle(0, 0, 100, 100);
		int i = 0;
		for (RenderableBlock block : canvas.getBlocks()) {
			if (!block.isVisible()) {
				continue;
			}
			if (i == 0) {
				r = block.getBounds();
			} else {
				r.add(block.getBounds());
			}
			i++;
		}
		r.grow(10, 10);// margin
		r = r.intersection(comp.getBounds());// マイナスにはみ出さない

		CScreenShotTaker taker = new CScreenShotTaker(comp);
		taker.setClipbounds(r);
		return taker;
	}
}
