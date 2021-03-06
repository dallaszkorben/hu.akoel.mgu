package hu.akoel.mgu.sprite.elements;

import hu.akoel.mgu.MGraphics;
import hu.akoel.mgu.sprite.Appearance;

public class OvalElement  extends ASpriteElement{
	
	private double x;
	double y;
	double radius;
	private Appearance normalAppearance;
	private Appearance focusAppearance;
	private Appearance connectedAppearance;
	private Appearance selectedAppearance;
	
	public OvalElement( double x, double y, double radius, Appearance normalAppearance ){
		this.normalAppearance = normalAppearance;
		this.focusAppearance = normalAppearance;
		this.connectedAppearance = normalAppearance;
		this.selectedAppearance = normalAppearance;
		
		this.x = x;
		this.y = y;
		this.radius = radius;
		
	}

	public void setRadius( double radius ){
		this.radius = radius;
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

	@Override
	public void draw(MGraphics g2) {
		g2.setColor(normalAppearance.getColor());
		g2.setStroke(normalAppearance.getStroke());
		g2.drawOval( x + getPositionX(), y + getPositionY(), radius );		
	}

	@Override
	public void drawFocus(MGraphics g2) {
		g2.setColor( focusAppearance.getColor() );
		g2.setStroke( focusAppearance.getStroke() );
		g2.drawOval( x + getPositionX(), y + getPositionY(), radius );		
	}

	@Override
	public void drawConnected(MGraphics g2) {
		g2.setColor( connectedAppearance.getColor() );
		g2.setStroke( connectedAppearance.getStroke() );
		g2.drawOval( x + getPositionX(), y + getPositionY(), radius );				
	}

	@Override
	public void drawSelected(MGraphics g2) {
		g2.setColor( selectedAppearance.getColor() );
		g2.setStroke( selectedAppearance.getStroke() );
		g2.drawOval( x + getPositionX(), y + getPositionY(), radius );		
	}
}