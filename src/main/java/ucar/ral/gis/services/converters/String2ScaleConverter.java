package ucar.ral.gis.services.converters;

import java.util.Map;

import org.springframework.core.convert.converter.Converter;

import ucar.ral.gis.services.Scale;

// StringToObject

public class String2ScaleConverter implements Converter<String, Scale> {
	
	private Map<String, Scale> productTypeLookup;
	
	public String2ScaleConverter(Map<String, Scale> productTypeLookup) {
		super();
		this.productTypeLookup = productTypeLookup;
	}

	public Scale convert(String source) {
		
		Scale productType = this.productTypeLookup.get(source);
		
		return productType;
	}

}
