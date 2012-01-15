package ucar.ral.gis.services.pipeline;

import ucar.ral.gis.services.ConversionRequestImpl;

public interface Processor {
	
	void process(ConversionRequestImpl conversionRequest);

}
