package ucar.ral.gis.services.web;

import ucar.ral.gis.services.EnsembleMember;
import ucar.ral.gis.services.Scale;
import ucar.ral.gis.services.TemporalResolution;


public class ProductRequest {
	
	private Scale product;
	private String variable;
	private String modelSim;
	private EnsembleMember ensemble;
	
	private TemporalResolution temporalResolution;
	
	private Float xmin;
	private Float xmax;
	private Float ymin;
	private Float ymax;
	
	private Integer month;
	
	private Integer startDate;
	private Integer endDate;
	
	

	public Scale getProduct() {
		return product;
	}

	public void setProduct(Scale product) {
		this.product = product;
	}

	public String getVariable() {
		return variable;
	}

	public void setVariable(String variable) {
		this.variable = variable;
	}

	public String getModelSim() {
		return modelSim;
	}

	public void setModelSim(String modelSim) {
		this.modelSim = modelSim;
	}

	public EnsembleMember getEnsemble() {
		return ensemble;
	}

	public void setEnsemble(EnsembleMember ensemble) {
		this.ensemble = ensemble;
	}

	public TemporalResolution getTemporalres() {
		return temporalResolution;
	}

	public void setTemporalres(TemporalResolution temporalResolution) {
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

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Integer getStartDate() {
		return startDate;
	}

	public void setStartDate(Integer startDate) {
		this.startDate = startDate;
	}

	public Integer getEndDate() {
		return endDate;
	}

	public void setEndDate(Integer endDate) {
		this.endDate = endDate;
	}

	
	
}
