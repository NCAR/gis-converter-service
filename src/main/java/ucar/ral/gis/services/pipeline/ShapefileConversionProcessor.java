package ucar.ral.gis.services.pipeline;

import java.io.File;

import ucar.ral.gis.services.messages.ConversionRequestMessage;
import edu.ucar.gis.ipcc.ConversionRequest;
import edu.ucar.gis.ipcc.model.netcdf2gis.CCSM2ShapeFileConverter;
import edu.ucar.gis.ipcc.model.netcdf2gis.ExtractionResults;

public class ShapefileConversionProcessor implements Processor {
	
	public ShapefileConversionProcessor() {
		super();
	}

	public void process(ConversionRequestMessage conversionRequest) {
		
		try {
		
			File outputFile = conversionRequest.getConversionOutput().getOutputFile();
			
			CCSM2ShapeFileConverter converter = new CCSM2ShapeFileConverter((ConversionRequest) conversionRequest, outputFile);
			
			ExtractionResults results = converter.execute();
		
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 

	}

}
