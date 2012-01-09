package ucar.ral.gis.services.converters;

import java.util.Map;

import org.springframework.core.convert.converter.Converter;

import ucar.ral.gis.services.TemporalResolution;

// StringToObject

public class String2TemporalResolutionConverter implements Converter<String, TemporalResolution> {
	
	private Map<String, TemporalResolution> resolutionLookup;
	
	public String2TemporalResolutionConverter(Map<String, TemporalResolution> productTypeLookup) {
		super();
		this.resolutionLookup = productTypeLookup;
	}

	public TemporalResolution convert(String source) {
		
		TemporalResolution productType = this.resolutionLookup.get(source);
		
		return productType;
	}

}
