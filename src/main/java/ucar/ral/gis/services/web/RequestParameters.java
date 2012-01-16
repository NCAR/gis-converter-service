package ucar.ral.gis.services.web;

import ucar.ral.gis.services.EnsembleMember;
import ucar.ral.gis.services.Resolution;
import ucar.ral.gis.services.TemporalResolution;
import edu.ucar.gis.ipcc.Month;
import edu.ucar.gis.ipcc.Months;


public class RequestParameters {
	
	private Resolution scale;
	private String variable;
	private String scenario;
	private EnsembleMember ensemble;
	
	private TemporalResolution temporalResolution;
	
	private Float xmin = -180.0F;
	private Float xmax = 180.0F;
	private Float ymin = -90.0F;
	private Float ymax = 90.0F;
	
	private Month month = Months.getMonth("0");
	
	private String startDate = "1870/01/01";
	private String endDate = "1870/12/31";
	
	

	public Resolution getScale() {
		return scale;
	}

	public void setScale(Resolution product) {
		this.scale = product;
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

	public Month getMonth() {
		return month;
	}

	public void setMonth(Month month) {
		this.month = month;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
		

	
	
}
