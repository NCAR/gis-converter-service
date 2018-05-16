package edu.ucar.gis.ipcc;


public interface ConversionRequest {

	String getOutputFileName();
	
	String getDataFileName();

	String getVariableName();
	
	TimeConstraint getTimeConstraint();
	
	AxisConstraint2<Latitude> getLatitudeConstraint();
	
	AxisConstraint2<Longitude> getLongitudeConstraint();

}