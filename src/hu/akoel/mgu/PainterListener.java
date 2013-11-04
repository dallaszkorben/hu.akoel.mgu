package hu.akoel.mgu;



import java.awt.Graphics2D;

public interface PainterListener{
	public void paintByWorldPosition(MCanvas canvas, MGraphics g2 );
	
	public void paintByViewer( MCanvas canvas, Graphics2D g2 );
}