package ucar.ral.gis.services.converters;

import org.springframework.core.convert.converter.Converter;

import edu.ucar.gis.ipcc.Month;
import edu.ucar.gis.ipcc.Months;

// StringToObject

public class String2MonthConverter implements Converter<String, Month> {
	

	public String2MonthConverter() {
		super();
	}

	public Month convert(String source) {
		
		Month month = Months.getMonth(source);
		
		return month;
	}

}
