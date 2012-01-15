package ucar.ral.gis.services.pipeline;

import java.io.File;

import ucar.ral.gis.services.ConversionRequestImpl;

public class OutputFileProcessor implements Processor {
	
	public OutputFileProcessor() {
		super();
	}

	public void process(ConversionRequestImpl conversionRequest) {
		
		// FIXME - Need to put the real code in here to create a temporary file.
		
		conversionRequest.setOutputDirectory(new File("/home/wilhelmi/test/test.shp"));
		

	}

}
