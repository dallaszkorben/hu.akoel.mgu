package hu.akoel.mgu.jcanvas.own;

import java.util.ArrayList;

public class JScale {

	public static enum UNIT{
		mm(0.001, "mm"),
		cm(0.01, "cm"),
		m(1, "m"),
		km(1000, "km");
		
		private double exchange;
		private String sign;
		
		private UNIT( double exchange, String sign ){
			this.exchange = exchange;
			this.sign = sign;
		}
		
		public double getExchange(){
			return exchange;
		}
		
		public String getSign(){
			return sign;
		}
	}
	
	private JCanvas canvas;
	private PixelPerCmValue pixelPerCm;
	private UnitValue unit;
	private ScaleValue startScale;
	private RateValue rate;
	private ScaleValue minScale;
	private ScaleValue maxScale;
	
	private ArrayList<ScaleChangeListener> scaleChangeListenerList = new ArrayList<ScaleChangeListener>();
	
	public JScale( JCanvas canvas, double pixelPerCm, UNIT unit, double startScale){
		commonConstructorForFreeScale(canvas, new PixelPerCmValue(pixelPerCm, pixelPerCm), new UnitValue(unit, unit), new ScaleValue(startScale, startScale), null, null, null );
	}
	
	public JScale( JCanvas canvas, double pixelPerCm, UNIT unit, double startScale, double rate ){
		commonConstructorForFreeScale(canvas, new PixelPerCmValue(pixelPerCm, pixelPerCm), new UnitValue(unit, unit), new ScaleValue(startScale, startScale), new RateValue(rate, rate), null, null );
	}
	
	public JScale( JCanvas canvas, double pixelPerCm, UNIT unit, double startScale, double rate, double minScale, double maxScale ){
		commonConstructorForFreeScale(canvas, new PixelPerCmValue(pixelPerCm, pixelPerCm), new UnitValue(unit, unit), new ScaleValue(startScale, startScale), new RateValue(rate, rate), new ScaleValue(minScale, minScale), new ScaleValue(maxScale, maxScale) );
	}
	
	
	
	
	public JScale( JCanvas canvas, PixelPerCmValue pixelPerCm, UnitValue unit, ScaleValue startScale){
		commonConstructorForFreeScale(canvas, pixelPerCm, unit, startScale, null, null, null );
	}
	
	public JScale( JCanvas canvas, PixelPerCmValue pixelPerCm, UnitValue unit, ScaleValue startScale, RateValue rate ){
		commonConstructorForFreeScale(canvas, pixelPerCm, unit, startScale, rate, null, null );		
	}
	
	public JScale( JCanvas canvas, PixelPerCmValue pixelPerCm, UnitValue unit, ScaleValue startScale, RateValue rate, ScaleValue minScale, ScaleValue maxScale ){
		commonConstructorForFreeScale(canvas, pixelPerCm, unit, startScale, rate, minScale, maxScale );		
	}
	
	private void commonConstructorForFreeScale( JCanvas canvas, PixelPerCmValue pixelPerCm, UnitValue unit, ScaleValue startScale, RateValue rate, ScaleValue minScale, ScaleValue maxScale ){ 
//JCanvas canvas, double pixelPerCmX, UNIT unitX, double startScaleX, double pixelPerCmY, UNIT unitY, double startScaleY, RateValue rate, ScaleValue minScale, ScaleValue maxScale ){
		this.canvas = canvas;
		this.pixelPerCm = pixelPerCm;
		this.unit = unit;
		this.startScale = startScale;
	
		this.rate = rate;
		this.minScale = minScale;
		this.maxScale = maxScale;
		
		double startPPUX = getPixelPerUnitByScale(pixelPerCm.getX(), unit.getUnitX(), startScale.getX());
		double startPPUY = getPixelPerUnitByScale(pixelPerCm.getY(), unit.getUnitY(), startScale.getX());

		//Ha adtam meg nagyitasi hatarokat
		if( null != minScale && null != maxScale ){
		
			double minPPUX = getPixelPerUnitByScale(pixelPerCm.getX(), unit.getUnitX(), maxScale.getX());
			double minPPUY = getPixelPerUnitByScale(pixelPerCm.getY(), unit.getUnitY(), maxScale.getY());
			
			double maxPPUX = getPixelPerUnitByScale(pixelPerCm.getX(), unit.getUnitX(), minScale.getX());
			double maxPPUY = getPixelPerUnitByScale(pixelPerCm.getY(), unit.getUnitY(), minScale.getY());

			canvas.setPossiblePixelPerUnits( new PossiblePixelPerUnits( 
				new PixelPerUnitValue(startPPUX, startPPUY), 
				rate, 
				new PixelPerUnitValue(minPPUX, minPPUY), 
				new PixelPerUnitValue(maxPPUX, maxPPUY)
			));
		
		//Ha nem voltak nagyitasi hatarok
		}else{
			canvas.setPossiblePixelPerUnits( new PossiblePixelPerUnits( 
					new PixelPerUnitValue(startPPUX, startPPUY), 
					rate					
				));
		}

		canvas.addPixelPerUnitChangeListener( new ScalePixelPerUnitChangeListener( pixelPerCm, unit ));

	}
	
	public JScale( JCanvas canvas, double pixelPerCm, UNIT unit, ArrayList<ScaleValue> possibleScaleList, int pointerForPossibleScaleList){
		commonConstructorForDiscrateScale(canvas, new PixelPerCmValue(pixelPerCm, pixelPerCm), new UnitValue( unit, unit ), possibleScaleList, pointerForPossibleScaleList);
	}
	
	public JScale( JCanvas canvas, PixelPerCmValue pixelPerCm, UnitValue unit, ArrayList<ScaleValue> possibleScaleList, int pointerForPossibleScaleList){
		commonConstructorForDiscrateScale(canvas, pixelPerCm, unit, possibleScaleList, pointerForPossibleScaleList);
	}
	
	private void commonConstructorForDiscrateScale( JCanvas canvas, PixelPerCmValue pixelPerCm, UnitValue unit, ArrayList<ScaleValue> possibleScaleList, int pointerForPossibleScaleList ){
		this.canvas = canvas;
		this.pixelPerCm = pixelPerCm;
		this.unit = unit;
		
		ArrayList<PixelPerUnitValue> possiblePPUList = new ArrayList<PixelPerUnitValue>();
		
		for( Value2D scale: possibleScaleList ){
			possiblePPUList.add(
					new PixelPerUnitValue(
							getPixelPerUnitByScale(pixelPerCm.getX(), unit.getUnitX(), scale.getX()), getPixelPerUnitByScale(pixelPerCm.getY(), unit.getUnitY(), scale.getY() )
					)				
			);
		}
		
		canvas.setPossiblePixelPerUnits( new PossiblePixelPerUnits(possiblePPUList, pointerForPossibleScaleList));
	
		canvas.addPixelPerUnitChangeListener( new ScalePixelPerUnitChangeListener( pixelPerCm, unit ));

	}
	
	public void addScaleChangeListener( ScaleChangeListener scaleChangeListener ){
		scaleChangeListenerList.add(scaleChangeListener);
	}	
	
	public double getPixelPerUnitByScale(double pixelPerCm, UNIT unit, double scale){
		return pixelPerCm * unit.getExchange() / UNIT.cm.getExchange() / scale;
	}
	
	public double getScaleByPixelPerUnit( double pixelPerCm, UNIT unit, double ppu){
		return (pixelPerCm * unit.getExchange() / UNIT.cm.getExchange() / ppu	);
	}
		
	public UNIT getUnitX(){
		return unit.getUnitX();
	}
	
	public UNIT getUnitY(){
		return unit.getUnitY();
	}
	
	class ScalePixelPerUnitChangeListener implements PixelPerUnitChangeListener{
		public PixelPerUnitValue pixelPerCm;
		public UnitValue unit;
		
		public ScalePixelPerUnitChangeListener( PixelPerCmValue pixelPerCm, UnitValue unit){
			this.pixelPerCm = new PixelPerUnitValue( pixelPerCm.getX(), pixelPerCm.getY() );
			
			this.unit = new UnitValue( unit.getUnitX(), unit.getUnitY() );
			
		}
		
		@Override
		public void getPixelPerUnit( Value2D pixelPerUnit ) {
			
			double scaleX = getScaleByPixelPerUnit(pixelPerCm.getX(), unit.getUnitX(), pixelPerUnit.getX());
			double scaleY = getScaleByPixelPerUnit(pixelPerCm.getY(), unit.getUnitY(), pixelPerUnit.getY());
			
			for( ScaleChangeListener listener : scaleChangeListenerList){
				listener.getScale( new ScaleValue( scaleX, scaleY ) );
			}
			
		}
	}
	
	public ScaleValue getScale(){
		double scaleX = getScaleByPixelPerUnit(pixelPerCm.getX(), unit.getUnitX(), canvas.getPixelPerUnit().getX());
		double scaleY = getScaleByPixelPerUnit(pixelPerCm.getY(), unit.getUnitY(), canvas.getPixelPerUnit().getY());
		return new ScaleValue( scaleX, scaleY );
	}
}

	
	
