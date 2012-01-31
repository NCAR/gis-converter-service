package ucar.ral.gis.services.pipeline;

import ucar.ral.gis.services.messages.ConversionRequestMessage;
import ucar.ral.gis.services.messages.MonthlyMeanConversionRequestImpl;

public interface Processor {
	
	void process(ConversionRequestMessage conversionRequest);

}
