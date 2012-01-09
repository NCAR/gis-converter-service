package ucar.ral.gis.services;

public class RequestDetails {
	
	private Float xmin;
	private Float xmax;
	private Float ymin;
	private Float ymax;
	
	private Integer month;
	
	private Integer startDate;
	private Integer endDate;
	
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
