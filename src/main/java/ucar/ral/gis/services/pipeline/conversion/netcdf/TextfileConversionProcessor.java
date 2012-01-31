package ucar.ral.gis.services.pipeline.conversion.netcdf;

import java.io.File;

import ucar.ral.gis.services.messages.ConversionRequestMessage;
import ucar.ral.gis.services.pipeline.Processor;
import edu.ucar.gis.ipcc.ConversionRequest;
import edu.ucar.gis.ipcc.model.netcdf2gis.CCSM2TextFileConverter;
import edu.ucar.gis.ipcc.model.netcdf2gis.ExtractionResults;

public class TextfileConversionProcessor implements Processor {
	
	public TextfileConversionProcessor() {
		super();
	}

	public void process(ConversionRequestMessage conversionRequest) {
		
		try {
		
			File outputFile = conversionRequest.getConversionOutput().getOutputFile();
			
			CCSM2TextFileConverter converter = new CCSM2TextFileConverter((ConversionRequest) conversionRequest, outputFile);
			
			ExtractionResults results = converter.execute();
		
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 

	}

}
