package ucar.ral.gis.services.pipeline.conversion.netcdf;

import java.io.File;

import ucar.ral.gis.services.messages.ConversionRequestMessage;
import ucar.ral.gis.services.pipeline.Processor;
import edu.ucar.gis.ipcc.ConversionRequest;
import edu.ucar.gis.ipcc.converter.ShapeConverter;

public class ShapefileConversionProcessor implements Processor {
	
	public ShapefileConversionProcessor() {
		super();
	}

	public void process(ConversionRequestMessage conversionRequest) {
		
		try {
		
			File outputFile = conversionRequest.getConversionOutput().getOutputFile();
			
			ShapeConverter converter = new ShapeConverter((ConversionRequest) conversionRequest, outputFile);
			
			converter.execute();
		
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 

	}

}
