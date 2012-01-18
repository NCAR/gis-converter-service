package ucar.ral.gis.services.web;

import ucar.ral.gis.services.Period;
import ucar.ral.gis.services.ProjectionSeason;
import ucar.ral.gis.services.ProjectionTerm;
import edu.ucar.gis.ipcc.Month;
import edu.ucar.gis.ipcc.Months;


public class DerivedProductParameters extends BaseParameters {

	private Month month = Months.getMonth("0");
	
	private Integer startYear = 1870;
	private Integer endYear = 1870;
	
	private Period period;
	private ProjectionTerm term;
	private ProjectionSeason season;
	
	

	public Period getPeriod() {
		return period;
	}

	public void setPeriod(Period period) {
		this.period = period;
	}

	public ProjectionTerm getTerm() {
		return term;
	}

	public void setTerm(ProjectionTerm term) {
		this.term = term;
	}

	public ProjectionSeason getSeason() {
		return season;
	}

	public void setSeason(ProjectionSeason season) {
		this.season = season;
	}


	public String getScenario() {
		return scenario;
	}

	public void setScenario(String scenario) {
		this.scenario = scenario;
	}
	

	public Month getMonth() {
		return month;
	}

	public void setMonth(Month month) {
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
