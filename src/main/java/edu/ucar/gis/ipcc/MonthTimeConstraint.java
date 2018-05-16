package edu.ucar.gis.ipcc;

import java.util.List;

import org.joda.time.DateTime;

import ucar.ma2.InvalidRangeException;
import ucar.ma2.Range;
import ucar.nc2.dataset.CoordinateAxis1DTime;
import ucar.nc2.time.CalendarDate;

public class MonthTimeConstraint extends TimeConstraint {
	
	private int month;
	
	public MonthTimeConstraint(int month) {
		super();

		this.month = month;
	}
	
	@Override
	public boolean includes(DateTime dateTime) {
		
		boolean result = false;
		
		int monthToTest = dateTime.getMonthOfYear() + 1;
		
		result = (this.month == monthToTest);
		
		return result;
	}
	
	/**
	 * 
	 * Assumes monthly data, this one is tough, since we can't get the year or month...
	 * 
	 */
	@Override
	public Range getTimeRange(CoordinateAxis1DTime timeAxis) {
	
		List<CalendarDate> calendarDates = timeAxis.getCalendarDates();
		
		Range result;
		try {
			
			result = new Range(this.month - 1, calendarDates.size() - 1, 12);
		
		} catch (InvalidRangeException e) {
			
			throw new RuntimeException(e);
		}
		
		return result;
	}
	
	
	

}
