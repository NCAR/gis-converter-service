package ucar.ral.gis.services.converters;

import org.springframework.core.convert.converter.Converter;

import ucar.ral.gis.services.ProjectionTerm;

// StringToObject

public class String2ProjectionTermConverter implements Converter<String, ProjectionTerm> {
	

	public String2ProjectionTermConverter() {
		super();
	}

	public ProjectionTerm convert(String source) {
		
		ProjectionTerm result = ProjectionTerm.valueOf(source.toUpperCase());
		
		return result;
	}

}
