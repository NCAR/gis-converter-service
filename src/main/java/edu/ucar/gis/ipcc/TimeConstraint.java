package edu.ucar.gis.ipcc;

import org.joda.time.DateTime;

import ucar.ma2.Range;
import ucar.nc2.dataset.CoordinateAxis1DTime;

public abstract class TimeConstraint {

	public abstract Range getTimeRange(CoordinateAxis1DTime timeAxis);
		
	public abstract boolean includes(DateTime date);
	
}