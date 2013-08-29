package ucar.ral.gis.services.pipeline;

import ucar.ral.gis.services.messages.ConversionRequestMessage;

public interface Processor {
	
	void process(ConversionRequestMessage conversionRequest);

}
