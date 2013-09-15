package ucar.ral.gis.services.pipeline.conversion.netcdf;

import java.io.File;

import ucar.ral.gis.services.messages.ConversionRequestMessage;
import ucar.ral.gis.services.pipeline.Processor;
import edu.ucar.gis.ipcc.ConversionRequest;
import edu.ucar.gis.ipcc.converter.CSVConverter;

public class TextfileConversionProcessor implements Processor {
	
	public TextfileConversionProcessor() {
		super();
	}

	public void process(ConversionRequestMessage conversionRequest) {
		
		try {
		
			File outputFile = conversionRequest.getConversionOutput().getOutputFile();
			
			CSVConverter converter = new CSVConverter((ConversionRequest) conversionRequest, outputFile);
			
			converter.execute();
		
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 

	}

}
