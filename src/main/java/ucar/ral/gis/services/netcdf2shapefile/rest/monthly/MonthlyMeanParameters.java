package ucar.ral.gis.services.netcdf2shapefile.rest.monthly;

import ucar.ral.gis.services.EnsembleMember;
import ucar.ral.gis.services.netcdf2shapefile.rest.BaseParameters;
import edu.ucar.gis.ipcc.Month;


public class MonthlyMeanParameters extends BaseParameters {
	
	private EnsembleMember ensemble;
	
	private String month = null;
	
	private Integer startYear = 1870;
	private Integer endYear = 1870;
	
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

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public Integer getStartYear() {
		return startYear;
	}

	public void setStartYear(Integer startYear) {
		this.startYear = startYear;
	}

	public Integer getEndYear() {
		return endYear;
	}

	public void setEndYear(Integer endYear) {
		this.endYear = endYear;
	}
	
	
}
