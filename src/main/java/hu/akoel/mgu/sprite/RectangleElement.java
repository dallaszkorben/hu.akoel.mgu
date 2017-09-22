package hu.akoel.mgu.sprite;

import hu.akoel.mgu.MGraphics;

import java.awt.Color;
import java.awt.Stroke;


public class RectangleElement extends SpriteElement{
	
	private double x;
	double y;
	double width;
	double height;
	private Appearance normalAppearance;
	private Appearance focusAppearance;
	private Appearance connectedAppearance;
	private Appearance selectedAppearance;
	private Appearance shadowAppearance;
	
	public RectangleElement( double x, double y, double width, double height, Appearance normalAppearance ){

		this.normalAppearance = normalAppearance;
		this.focusAppearance = normalAppearance;
		this.connectedAppearance = normalAppearance;
		this.selectedAppearance = normalAppearance;
		
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
	}

	public void setFocusAppearance( Appearance focusAppearance ){
		this.focusAppearance = focusAppearance;
	}

	public void seConnectedAppearance( Appearance connectedAppearance ){
		this.connectedAppearance = connectedAppearance;
	}
	
	public void setSelectedAppearance( Appearance selectedAppearance ){
		this.selectedAppearance = selectedAppearance;
	}

	public void setShadowAppearance( Appearance shadowAppearance ){
		this.shadowAppearance = shadowAppearance;
	}

	@Override
	public void draw(MGraphics g2) {
		g2.setColor(normalAppearance.getColor());
		g2.setStroke(normalAppearance.getStroke());
		g2.drawRectangle(x + getPositionX(), y + getPositionY(), x + getPositionX() + width, y + getPositionY() + height);		
	}

	@Override
	public void drawFocus(MGraphics g2) {
		g2.setColor( focusAppearance.getColor() );
		g2.setStroke( focusAppearance.getStroke() );
		g2.drawRectangle( x + getPositionX(), y + getPositionY(), x + getPositionX() + width, y + getPositionY() + height );		
	}

	@Override
	public void drawConnected(MGraphics g2) {
		g2.setColor( connectedAppearance.getColor() );
		g2.setStroke( connectedAppearance.getStroke() );
		g2.drawRectangle(x + getPositionX(), y + getPositionY(), x + getPositionX() + width, y + getPositionY() + height);				
	}

	@Override
	public void drawSelected(MGraphics g2) {
		g2.setColor( selectedAppearance.getColor() );
		g2.setStroke( selectedAppearance.getStroke() );
		g2.drawRectangle(x + getPositionX(), y + getPositionY(), x + getPositionX() + width, y + getPositionY() + height);		
	}

	@Override
	public void drawShadow(MGraphics g2) {
		if( null != shadowAppearance ){
			g2.setColor( shadowAppearance.getColor() );
			g2.setStroke( shadowAppearance.getStroke() );
			g2.drawRectangle(x + getPositionX(), y + getPositionY(), x + getPositionX() + width, y + getPositionY() + height);	
		}
		
	}	
}
