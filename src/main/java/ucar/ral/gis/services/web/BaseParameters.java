package ucar.ral.gis.services.web;

import ucar.ral.gis.services.OutputType;
import ucar.ral.gis.services.Resolution;
import ucar.ral.gis.services.TemporalResolution;

public class BaseParameters {

	protected Resolution scale;
	protected String variable;
	protected String scenario;
	protected TemporalResolution temporalResolution;
	protected OutputType outputType;
	protected Float xmin = -180.0F;
	protected Float xmax = 180.0F;
	protected Float ymin = -90.0F;
	protected Float ymax = 90.0F;

	public BaseParameters() {
		super();
	}

	public Resolution getScale() {
		return scale;
	}

	public void setScale(Resolution scale) {
		this.scale = scale;
	}

	public String getVariable() {
		return variable;
	}

	public void setVariable(String variable) {
		this.variable = variable;
	}

	public String getScenario() {
		return scenario;
	}

	public void setScenario(String scenario) {
		this.scenario = scenario;
	}

	public TemporalResolution getTemporalResolution() {
		return temporalResolution;
	}

	public void setTemporalResolution(TemporalResolution temporalResolution) {
		this.temporalResolution = temporalResolution;
	}

	public Float getXmin() {
		return xmin;
	}

	public void setXmin(Float xmin) {
		this.xmin = xmin;
	}

	public Float getXmax() {
		return xmax;
	}

	public void setXmax(Float xmax) {
		this.xmax = xmax;
	}

	public Float getYmin() {
		return ymin;
	}

	public void setYmin(Float ymin) {
		this.ymin = ymin;
	}

	public Float getYmax() {
		return ymax;
	}

	public void setYmax(Float ymax) {
		this.ymax = ymax;
	}

	public OutputType getOutputType() {
		return outputType;
	}

	public void setOutputType(OutputType outputType) {
		this.outputType = outputType;
	}
	
	

}