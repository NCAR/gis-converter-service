package ucar.ral.gis.services.pipeline;

import java.io.File;

import ucar.ral.gis.services.ConversionRequestImpl;
import edu.ucar.gis.ipcc.model.netcdf2gis.CCSM2ShapeFileConverter;
import edu.ucar.gis.ipcc.model.netcdf2gis.ExtractionResults;

public class ShapefileConversionProcessor implements Processor {
	
	public ShapefileConversionProcessor() {
		super();
	}

	public void process(ConversionRequestImpl conversionRequest) {
		
		try {
		
			File outputFile = new File(conversionRequest.getOutputFileName());
			
			CCSM2ShapeFileConverter converter = new CCSM2ShapeFileConverter(conversionRequest, outputFile);
			
			ExtractionResults results = converter.execute();
		
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 

	}

}
