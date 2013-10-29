package hu.akoel.mgu.mcrossline;

import hu.akoel.mgu.JGraphics;
import hu.akoel.mgu.MCanvas;
import hu.akoel.mgu.PainterListener;
import hu.akoel.mgu.MCanvas.Level;
import hu.akoel.mgu.values.Value2D;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;


public class MCrossLine {
	
	public static enum PainterPosition{
		DEEPEST,
		MIDDLE,
		HIGHEST
	}
	
	private MCanvas canvas;
	private Value2D position;
	private Color color;
	private int widthInPixel;
	private Value2D length;
	private PainterPosition painterPosition;
	private PainterListener painterListener;

	public MCrossLine( MCanvas canvas, Value2D position, Color color, int widthInPixel, Value2D length, PainterPosition painterPosition ){
		this.canvas = canvas;
		this.position = position;
		this.color = color;
		this.length = length;  
		this.widthInPixel = widthInPixel;
		this.painterPosition = painterPosition;
		
		painterListener = new CrossLinePainterListener( );
		
		if( painterPosition.equals(PainterPosition.DEEPEST ) ){
			canvas.addPainterListenerToDeepest(painterListener, Level.ABOVE);
		}else if( painterPosition.equals( PainterPosition.MIDDLE)){
			canvas.addPainterListenerToMiddle(painterListener, Level.ABOVE);
		}else if( painterPosition.equals(PainterPosition.HIGHEST)){
			canvas.addPainterListenerToHighest(painterListener, Level.ABOVE);
		}
	}
	
	public void turnOff(){
		if( painterPosition.equals(PainterPosition.DEEPEST ) ){
			canvas.removePainterListenerFromDeepest(painterListener);
		}else if( painterPosition.equals( PainterPosition.MIDDLE)){
			canvas.removePainterListenerFromMiddle(painterListener);
		}else if( painterPosition.equals(PainterPosition.HIGHEST)){
			canvas.removePainterListenerFromHighest(painterListener);
		}
	}
	
	public void turnOn(){
		if( painterPosition.equals(PainterPosition.DEEPEST ) ){
			canvas.addPainterListenerToDeepest(painterListener, Level.ABOVE);
		}else if( painterPosition.equals( PainterPosition.MIDDLE)){
			canvas.addPainterListenerToMiddle(painterListener, Level.ABOVE);
		}else if( painterPosition.equals(PainterPosition.HIGHEST)){
			canvas.addPainterListenerToHighest(painterListener, Level.ABOVE);
		}	
	}
	
	public void setColor( Color color ){
		this.color = color;
	}
	
	public double getPositionX(){
		return position.getX();
	}
	
	public double getPositionY(){
		return position.getY();
	}
	
	public void setPositionX( double positionX ){
		this.position.setX( positionX );
	}
	
	public void setPositionY( double positionY ){
		this.position.setY( positionY );
	}
	
	public double getLengthX(){
		return length.getX();
	}
	
	public double getLengthY(){
		return length.getY();
	}
	
	public void setLengthX( double lengthX ){
		this.length.setX( lengthX );
	}

	public void setLengthY( double lengthY ){
		this.length.setY( lengthY );
	}

	public void setWidthInPixel( int widthInPixel ){
		this.widthInPixel = widthInPixel;
	}
	
	class CrossLinePainterListener implements PainterListener{

		@Override
		public void paintByWorldPosition(MCanvas canvas, JGraphics g2) {
			g2.setColor( color );
			g2.setStroke(new BasicStroke(widthInPixel));
			
			g2.drawLine( (position.getX() - length.getX()/2.0), position.getY(), (position.getX() + length.getX()/2.0), position.getY() );
			g2.drawLine( position.getX(), (position.getY() - length.getY()/2.0), position.getX(), (position.getY() + length.getY()/2.0) );
		}

		@Override
		public void paintByViewer(MCanvas canvas, Graphics2D g2) {}
	}
	
}
