package ucar.ral.gis.services.pipeline.conversion.netcdf;

import java.io.File;

import ucar.ral.gis.services.MonthlyMeanConversionRequestImpl;
import ucar.ral.gis.services.DataFileFactory;
import ucar.ral.gis.services.messages.ConversionRequestMessage;
import ucar.ral.gis.services.pipeline.Processor;
import ucar.ral.gis.services.web.MonthlyMeanParameters;

public class SourceDatafileProcessor implements Processor {
	
	DataFileFactory dataFileFactory;
	
	public SourceDatafileProcessor(DataFileFactory dataFileFactory) {
		super();
		this.dataFileFactory = dataFileFactory;
	}

	public void process(ConversionRequestMessage conversionRequest) {
		
		File dataFile = this.dataFileFactory.findDataFile(conversionRequest.getParameters());
		
		conversionRequest.setDataFile(dataFile);

	}

}
