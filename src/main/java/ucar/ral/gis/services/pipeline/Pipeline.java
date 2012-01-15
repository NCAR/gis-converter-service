package ucar.ral.gis.services.pipeline;

import java.util.List;

import ucar.ral.gis.services.ConversionRequestImpl;

public class Pipeline implements Processor {
	
	private List<Processor> stages;
	
	public Pipeline(List<Processor> stages) {
		super();
		this.stages = stages;
	}

	public void process(ConversionRequestImpl conversionRequest) {
		
		for (Processor processor : this.stages) {
			
			processor.process(conversionRequest);
		}

	}

}
