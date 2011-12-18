package ucar.ral.gis.services;

import java.beans.PropertyEditor;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

public class GlobalBindingInitializer implements WebBindingInitializer {
	
	private Map<Class, PropertyEditor> editors;
	
	public GlobalBindingInitializer(Map<Class, PropertyEditor> editors) {
		super();
		this.editors = editors;
	}


	public void initBinder(WebDataBinder binder, WebRequest request) {
		
		for (Entry<Class, PropertyEditor> entry : this.editors.entrySet()) {
			
			binder.registerCustomEditor(entry.getKey(), entry.getValue());
		
		}

	}

}
