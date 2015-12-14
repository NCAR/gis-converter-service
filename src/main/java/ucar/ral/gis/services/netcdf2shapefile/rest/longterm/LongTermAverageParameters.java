package ucar.ral.gis.services.netcdf2shapefile.rest.longterm;

import ucar.ral.gis.services.netcdf2shapefile.rest.BaseParameters;


public class LongTermAverageParameters extends BaseParameters {

	private Integer startYear;
	private Integer endYear;
	
	private String month;
	
	private String period;
	private String term;
	private String season;
		
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

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}
	
	
}
