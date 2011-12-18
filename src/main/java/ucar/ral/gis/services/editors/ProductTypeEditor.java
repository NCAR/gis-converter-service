package ucar.ral.gis.services.editors;

import java.beans.PropertyEditorSupport;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.util.StringUtils;

import ucar.ral.gis.services.ProductType;


public class ProductTypeEditor extends PropertyEditorSupport {
	
	private Map<String, ProductType> productTypeLookup;
	
	public ProductTypeEditor() {
		super();
		//this.productTypeLookup = productTypeLookup;
	}
	
	public ProductTypeEditor(Map<String, ProductType> productTypeLookup) {
		super();
		this.productTypeLookup = productTypeLookup;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setAsText(final String text) throws IllegalArgumentException {
		
		if (StringUtils.hasText(text)) {
			
			ProductType productType = this.productTypeLookup.get(text);
			
			setValue(productType);
			
		} else {
			
			setValue(null);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getAsText() {
		
		ProductType currentValue = (ProductType) super.getValue();
		
		String result = "";
		
		if (currentValue != null) {
			
			for (Entry entry : this.productTypeLookup.entrySet()) {
				
				if (currentValue == entry.getValue()) {
					result = (String) entry.getKey();
					break;
				}
			}
			
		}
		
		return result;
	}

}
