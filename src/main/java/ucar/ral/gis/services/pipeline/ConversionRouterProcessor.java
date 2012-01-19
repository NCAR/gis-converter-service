package ucar.ral.gis.services.pipeline;

import ucar.ral.gis.services.OutputType;
import ucar.ral.gis.services.messages.ConversionRequestMessage;

public class ConversionRouterProcessor implements Processor {
	
	private Processor shapeFileProcessor;
	private Processor textFileProcessor;
	
	public ConversionRouterProcessor(Processor shapeFileProcessor,
			Processor textFileProcessor) {
		super();
		this.shapeFileProcessor = shapeFileProcessor;
		this.textFileProcessor = textFileProcessor;
	}

	public void process(ConversionRequestMessage conversionRequest) {
		
		if(conversionRequest.getParameters().getOutputType() == OutputType.SHAPE) {
			this.shapeFileProcessor.process(conversionRequest);
		} 
		else {
			this.textFileProcessor.process(conversionRequest);
		}

	}

}
