package ucar.ral.gis.services.converters;

import java.util.Map;

import org.springframework.core.convert.converter.Converter;

import ucar.ral.gis.services.Resolution;

// StringToObject

public class String2ScaleConverter implements Converter<String, Resolution> {
	
	private Map<String, Resolution> productTypeLookup;
	
	public String2ScaleConverter(Map<String, Resolution> productTypeLookup) {
		super();
		this.productTypeLookup = productTypeLookup;
	}

	public Resolution convert(String source) {
		
		Resolution productType = this.productTypeLookup.get(source);
		
		return productType;
	}

}
