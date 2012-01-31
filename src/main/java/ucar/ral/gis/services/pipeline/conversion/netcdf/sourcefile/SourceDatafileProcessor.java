package ucar.ral.gis.services.pipeline.conversion.netcdf.sourcefile;

import java.io.File;

import ucar.ral.gis.services.messages.ConversionRequestMessage;
import ucar.ral.gis.services.messages.MonthlyMeanConversionRequestImpl;
import ucar.ral.gis.services.netcdf2shapefile.rest.monthly.MonthlyMeanParameters;
import ucar.ral.gis.services.pipeline.Processor;

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
