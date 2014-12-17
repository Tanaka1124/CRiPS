package edu.inf.shizuoka.drawingobjects;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;

import javax.swing.JComponent;

import edu.mit.blocks.workspace.Workspace;





public class ArrowObject extends JComponent {

	private static final long serialVersionUID = -1745361279120477995L;
	private Point startPoint;// 起点
	private Point endPoint;// 終点
	private Color arrowCollor = Color.RED;
	private Workspace workspace;
	
	public ArrowObject(Point startPoint, Point endPoint, Workspace ws) {
		setBounds(ws.getBlockCanvas().getCanvas().getBounds());
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		this.workspace = ws;
		setDoubleBuffered(true);
	}
	
	public void resetPoint(Point startPoint, Point endPoint){
		this.startPoint = startPoint;
		this.endPoint = endPoint;
	}
	
	public Point getStartPoint(){
		return this.startPoint;
	}
	
	public void setColor(Color color){
		this.arrowCollor = color;
	}
	
	public Color getColor(){
		return this.arrowCollor;
	}
	
	public Point getEndPoint(){
		return this.endPoint;
	}
	
	public void addStartPoint(int dx, int dy) {
		this.startPoint.x += dx;
		this.startPoint.y += dy;
	}

	public void addEndPoint(int dx, int dy) {
		this.endPoint.x += dx;
		this.endPoint.y += dy;
	}

	public void setEndPoint(Point p) {
		this.endPoint = p;
	}

	public void setStartPoint(Point p) {
		this.startPoint = p;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawArrow((Graphics2D) g);
	}

	public void paint(Graphics g) {
		super.paint(g);
		//boundsの再設定
		drawArrow((Graphics2D) g);
	}

	public void update(Graphics g) {
		super.update(g);
		paint(g);
	}

	public void drawArrow(Graphics2D graphic) {
		setBounds(0, 0, workspace.getBlockCanvas().getCanvas().getHeight(), workspace.getBlockCanvas().getCanvas().getWidth());
		graphic.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		ArrowPointCalcUtil util = new ArrowPointCalcUtil(getStartPoint(), getEndPoint());
		BasicStroke stroke = new BasicStroke(3.0f);
		graphic.setStroke(stroke);

		Point2D p2 = util.getPoint2();
		Point2D p3 = util.getPoint3();

		graphic.setColor(arrowCollor);
		// arrowLengthの変更（ベクトルの向きに応じて変更）
		graphic.drawLine(getStartPoint().x, getStartPoint().y, getEndPoint().x, getEndPoint().y);
		graphic.drawLine(getEndPoint().x, getEndPoint().y, (int) p2.getX(),
				(int) p2.getY());
		graphic.drawLine(getEndPoint().x, getEndPoint().y, (int) p3.getX(),
				(int) p3.getY());
		
		workspace.getBlockCanvas().getCanvas().repaint();
	}
	
	public static Container getOrigin(JComponent cmp){
		Container component = cmp.getParent();
		
		if(component == null){
			return cmp;
		}
		
		while(component.getParent() != null){
			component = component.getParent();
		}
		
		return component;
	}

}
