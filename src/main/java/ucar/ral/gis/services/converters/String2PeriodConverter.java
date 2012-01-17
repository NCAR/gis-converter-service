package ucar.ral.gis.services.converters;

import org.springframework.core.convert.converter.Converter;

import ucar.ral.gis.services.Period;

// StringToObject

public class String2PeriodConverter implements Converter<String, Period> {
	

	public String2PeriodConverter() {
		super();
	}

	public Period convert(String source) {
		
		Period result = Period.valueOf(source.toUpperCase());
		
		return result;
	}

}
