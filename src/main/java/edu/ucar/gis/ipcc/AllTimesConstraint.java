package edu.ucar.gis.ipcc;

import org.joda.time.DateTime;

import ucar.ma2.Range;
import ucar.nc2.dataset.CoordinateAxis1DTime;

public class AllTimesConstraint extends TimeConstraint {
	
	@Override
	public boolean includes(DateTime date) {

		return true;
	}
	
	@Override
	public Range getTimeRange(CoordinateAxis1DTime timeAxis) {
		
		return null;
	}
}
