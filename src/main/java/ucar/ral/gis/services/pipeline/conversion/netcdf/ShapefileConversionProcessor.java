package ucar.ral.gis.services.pipeline.conversion.netcdf;

import java.io.File;

import ucar.ral.gis.services.messages.ConversionRequestMessage;
import ucar.ral.gis.services.pipeline.Processor;
import edu.ucar.gis.ipcc.ConversionRequest;
import edu.ucar.gis.ipcc.converter.Converter;
import edu.ucar.gis.ipcc.model.netcdf2gis.transform.ShapeFileTransformer;
import edu.ucar.gis.ipcc.model.netcdf2gis.transform.Transformer;

public class ShapefileConversionProcessor implements Processor {
	
	public ShapefileConversionProcessor() {
		super();
	}

	public void process(ConversionRequestMessage conversionRequest) {
		
		try {
		
			File outputFile = conversionRequest.getConversionOutput().getOutputFile();
			
			Transformer transformer = new ShapeFileTransformer();
	    	
	    	Converter converter = new Converter((ConversionRequest) conversionRequest, outputFile, transformer);
			
			converter.execute();
		
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 

	}

}
