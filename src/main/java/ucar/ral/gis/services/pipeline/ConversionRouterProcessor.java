package ucar.ral.gis.services.pipeline;

import ucar.ral.gis.services.ConversionRequestImpl;
import ucar.ral.gis.services.OutputType;

public class ConversionRouterProcessor implements Processor {
	
	private Processor shapeFileProcessor;
	private Processor textFileProcessor;
	
	public ConversionRouterProcessor(Processor shapeFileProcessor,
			Processor textFileProcessor) {
		super();
		this.shapeFileProcessor = shapeFileProcessor;
		this.textFileProcessor = textFileProcessor;
	}

	public void process(ConversionRequestImpl conversionRequest) {
		
		if(conversionRequest.getOutputType() == OutputType.SHAPE) {
			this.shapeFileProcessor.process(conversionRequest);
		} 
		else {
			this.textFileProcessor.process(conversionRequest);
		}

	}

}
