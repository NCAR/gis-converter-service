package ucar.ral.gis.services.converters;

import java.util.Map;

import org.springframework.core.convert.converter.Converter;

import ucar.ral.gis.services.ProductType;

// StringToObject

public class String2ScaleConverter implements Converter<String, ProductType> {
	
	private Map<String, ProductType> productTypeLookup;
	
	public String2ScaleConverter(Map<String, ProductType> productTypeLookup) {
		super();
		this.productTypeLookup = productTypeLookup;
	}

	public ProductType convert(String source) {
		
		ProductType productType = this.productTypeLookup.get(source);
		
		return productType;
	}

}
