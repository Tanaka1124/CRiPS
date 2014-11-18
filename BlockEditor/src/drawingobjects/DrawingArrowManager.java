package drawingobjects;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import renderable.RenderableBlock;
import renderable.ScopeChecker;
import workspace.Workspace;
import workspace.WorkspaceEvent;
import workspace.WorkspaceListener;
import codeblocks.Block;
import codeblocks.BlockConnector;
import codeblocks.BlockStub;
import controller.WorkspaceController;

public class DrawingArrowManager implements WorkspaceListener {

	private static List<RenderableBlock> arrowPossesser = new ArrayList<RenderableBlock>();

	public static void addPossesser(RenderableBlock possesser) {
		arrowPossesser.add(possesser);
	}

	public static void clearPossessers() {
		for (RenderableBlock block : arrowPossesser) {
			block.clearArrows();
		}
		arrowPossesser.clear();
	}

	public static void updatePossessers() {
		for (RenderableBlock rb : arrowPossesser) {
			rb.updateEndArrowPoint();
		}
	}

	public static void clearPosesser(RenderableBlock posesser) {
		posesser.clearArrows();
		arrowPossesser.remove(posesser);
	}

	public static void setVisible(boolean active) {
		if (active) {
			Workspace.getInstance().getWorkSpaceController().showAllTraceLine();
		} else {
			Workspace.getInstance().getWorkSpaceController().disposeTraceLine();
		}
	}

	public static void resetArrowsPosition() {
		for (RenderableBlock rb : arrowPossesser) {
			rb.resetArrowPosition();
		}
	}

	public static void thinArrows(RenderableBlock rBlock, int concentration) {
		if (rBlock.getEndArrows().size() > 0) {
			Point p = new Point(rBlock.getLocation());

			p.x += rBlock.getWidth();
			p.y += rBlock.getHeight() / 2;

			if (rBlock.getBlock() instanceof BlockStub && hasEmptySocket(rBlock.getBlock())) {
				concentration = 30;
			}

			for (ArrowObject endArrow : rBlock.getEndArrows()) {
				endArrow.setStartPoint(p);
				endArrow.setColor(new Color(255, 0, 0, concentration));
			}
		}
	}

	public static boolean hasEmptySocket(Block block) {
		for (Iterator<BlockConnector> itarator = block.getSockets().iterator(); itarator
				.hasNext();) {
			if (!itarator.next().hasBlock()) {
				return true;
			}
		}
		return false;
	}

	public static Point calcCallerBlockPoint(RenderableBlock callerblock) {
		Point p1 = new Point(callerblock.getLocation());
		p1.x += callerblock.getWidth();
		p1.y += callerblock.getHeight() / 2;

		return p1;

	}

	public static Point calcDefinisionBlockPoint(RenderableBlock parentBlock) {
		Point p2 = new Point(parentBlock.getLocation());
		p2.y += parentBlock.getHeight() / 2;

		return p2;
	}

	public static void removeArrow(RenderableBlock block) {
		Workspace ws = Workspace.getInstance();
		WorkspaceController wc = ws.getWorkSpaceController();

		for (ArrowObject arrow : block.getEndArrows()) {
			ws.getPageNamed(wc.calcClassName()).clearArrow((Object) arrow);
		}

		for (ArrowObject arrow : block.getStartArrows()) {
			ws.getPageNamed(wc.calcClassName()).clearArrow((Object) arrow);
		}

		DrawingArrowManager.clearPosesser(block);

		ws.getPageNamed(wc.calcClassName()).getJComponent().repaint();

	}

	public void workspaceEventOccurred(WorkspaceEvent event) {
		if (event.getEventType() == WorkspaceEvent.BLOCKS_DISCONNECTED) {
			RenderableBlock socketBlock = RenderableBlock.getRenderableBlock(event.getSourceLink().getSocketBlockID());
			RenderableBlock plugBlock = RenderableBlock.getRenderableBlock(event.getSourceLink().getPlugBlockID());

//			System.out.println(socketBlock);
//			System.out.println(plugBlock);
			socketBlock.updateEndArrowPoints(socketBlock.getBlockID(),calcConcentration(ScopeChecker.isIndependentBlock(socketBlock.getBlock())));
			plugBlock.updateEndArrowPoints(plugBlock.getBlockID(), calcConcentration(ScopeChecker.isIndependentBlock(plugBlock.getBlock())));

		}

		if (event.getEventType() == WorkspaceEvent.BLOCKS_CONNECTED) {
			RenderableBlock socketBlock = RenderableBlock.getRenderableBlock(event.getSourceLink().getSocket().getBlockID());
			RenderableBlock plugBlock = RenderableBlock.getRenderableBlock(event.getSourceLink().getPlug().getBlockID());
			
			socketBlock.updateEndArrowPoints(socketBlock.getBlockID(),calcConcentration(ScopeChecker.isIndependentBlock(socketBlock.getBlock())));
			plugBlock.updateEndArrowPoints(plugBlock.getBlockID(), calcConcentration(ScopeChecker.isIndependentBlock(plugBlock.getBlock())));
		}
	}
	
	public int calcConcentration(boolean isAloneBlock){
		int concentration = 255;
		
		if(isAloneBlock){
			concentration = 30;
		}
		
		return concentration;
	}
	
}
