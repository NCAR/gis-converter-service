package edu.ucar.gis.ipcc;

import java.util.List;

import org.joda.time.DateTime;

import ucar.ma2.InvalidRangeException;
import ucar.ma2.Range;
import ucar.nc2.dataset.CoordinateAxis1DTime;
import ucar.nc2.time.CalendarDate;

import com.google.common.collect.Lists;

public class YearMonthTimeConstraint extends TimeConstraint {
	
	private int startYear;
	private int endYear;
	
	private int month;
	
	public YearMonthTimeConstraint(int startYear, int endYear, int month) {
		super();
		this.startYear = startYear;
		this.endYear = endYear;
		this.month = month;
	}

	@Override
	public Range getTimeRange(CoordinateAxis1DTime timeAxis) {
		
		List<CalendarDate> calendarDates = timeAxis.getCalendarDates();
		
		int startIndex = this.getStartIndex(timeAxis, calendarDates);
		int endIndex = this.getEndIndex(timeAxis, calendarDates);
		
		Range range;
		try {
			
			range = new Range(startIndex, endIndex , 12);
			
		} catch (InvalidRangeException e) {

			throw new RuntimeException(e);
		}
		
		return range;
	}
	
	protected int getStartIndex(CoordinateAxis1DTime timeAxis, List<CalendarDate> dates) {
		
		//of(Calendar cal, int year, int monthOfYear, int dayOfMonth, int hourOfDay, int minuteOfHour, int secondOfMinute) {
		CalendarDate firstDayOfStartYear = CalendarDate.of(dates.get(0).getCalendar(), this.startYear, this.month, 1, 0, 0, 0);
		
		CalendarDate firstDateOfYear = null;
		
		for (CalendarDate calendarDate : dates) {
			
			if (calendarDate.isAfter(firstDayOfStartYear)) {
				firstDateOfYear = calendarDate;
				break;
			}
 			
		}
		
		int startIndex = timeAxis.findTimeIndexFromCalendarDate(firstDateOfYear);
		
		return startIndex;
	}
	
	protected int getEndIndex(CoordinateAxis1DTime timeAxis, List<CalendarDate> dates) {
		
		CalendarDate firstDayAfterEndYear = CalendarDate.of(dates.get(0).getCalendar(), this.endYear + 1, this.month, 1, 0, 0, 0);
		
		CalendarDate lastDateOfYear = null;
		
		for (CalendarDate calendarDate : Lists.reverse(dates)) {
			
			if (calendarDate.isBefore(firstDayAfterEndYear)) {
				lastDateOfYear = calendarDate;
				break;
			}
 			
		}
		
		int endIndex = timeAxis.findTimeIndexFromCalendarDate(lastDateOfYear);
		
		return endIndex;
	}
	
	@Override
	public boolean includes(DateTime date) {
		
		boolean result = false;
		
		int yearToTest = date.getYear();
		
		if ((this.startYear <= yearToTest) && (yearToTest <= this.endYear)) {
			
			// WARNING the datetime uses a zero based index for months.
			result = (this.month == (date.getMonthOfYear() + 1));
			
		}
		
		return result;
	}

}
