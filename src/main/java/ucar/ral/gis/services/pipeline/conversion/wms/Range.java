package ucar.ral.gis.services.pipeline.conversion.wms;

public class Range {
	
	private Float min;
	private Float max;
	
	public Range() {
		super();
	}
	
	public Range(Float min, Float max) {
		super();
		this.min = min;
		this.max = max;
	}
	
	public Float getMin() {
		return min;
	}
	
	public void setMin(Float min) {
		this.min = min;
	}
	
	public Float getMax() {
		return max;
	}
	
	public void setMax(Float max) {
		this.max = max;
	}
	
}
