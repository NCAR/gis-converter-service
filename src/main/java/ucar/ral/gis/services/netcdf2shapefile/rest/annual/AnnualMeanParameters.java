package ucar.ral.gis.services.netcdf2shapefile.rest.annual;

import ucar.ral.gis.services.netcdf2shapefile.rest.BaseParameters;


public class AnnualMeanParameters extends BaseParameters {

	private Integer startYear = 1870;
	private Integer endYear = 1870;
		
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
