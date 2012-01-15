package ucar.ral.gis.services.pipeline;

import java.io.File;

import ucar.ral.gis.services.ConversionRequestImpl;
import ucar.ral.gis.services.DataFileFactory;

public class SourceDatafileProcessor implements Processor {
	
	DataFileFactory dataFileFactory;
	
	public SourceDatafileProcessor(DataFileFactory dataFileFactory) {
		super();
		this.dataFileFactory = dataFileFactory;
	}

	public void process(ConversionRequestImpl conversionRequest) {
		
		File dataFile = this.dataFileFactory.findDataFile(conversionRequest.getProductRequest());
		
		conversionRequest.setDataFile(dataFile);

	}

}
