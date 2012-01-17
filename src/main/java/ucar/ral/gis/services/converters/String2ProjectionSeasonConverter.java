package ucar.ral.gis.services.converters;

import org.springframework.core.convert.converter.Converter;

import ucar.ral.gis.services.ProjectionSeason;

// StringToObject

public class String2ProjectionSeasonConverter implements Converter<String, ProjectionSeason> {
	

	public String2ProjectionSeasonConverter() {
		super();
	}

	public ProjectionSeason convert(String source) {
		
		ProjectionSeason result = ProjectionSeason.valueOf(source.toUpperCase());
		
		return result;
	}

}
