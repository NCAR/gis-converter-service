package ucar.ral.gis.services.pipeline.conversion.netcdf;

import java.io.File;
import java.util.UUID;

import ucar.ral.gis.services.MonthlyMeanConversionRequestImpl;
import ucar.ral.gis.services.OutputFileNameFactory;
import ucar.ral.gis.services.messages.ConversionOutput;
import ucar.ral.gis.services.messages.ConversionRequestMessage;
import ucar.ral.gis.services.netcdf2shapefile.rest.BaseParameters;
import ucar.ral.gis.services.pipeline.Processor;

public class OutputFileProcessor implements Processor {
	
	private File scratchDirectory;
	private OutputFileNameFactory outputFileNameFactory;
	
	
	public OutputFileProcessor(File scratchDirectory, OutputFileNameFactory outputFileNameFactory) {
		super();
		this.scratchDirectory = scratchDirectory;
		this.outputFileNameFactory = outputFileNameFactory;
	}

	public void process(ConversionRequestMessage conversionRequest) {
		
		BaseParameters parameters = conversionRequest.getParameters();
		
		String outputFilename = this.outputFileNameFactory.create(parameters);
		
		
		File workDirectory = new File(this.scratchDirectory, UUID.randomUUID().toString());
		File outputDirectory = new File(workDirectory, "/output/");
		
		outputDirectory.mkdirs();
		
		File outputFile = new File(outputDirectory, outputFilename + "." + parameters.getOutputType().getFileExtension());
		
		ConversionOutput conversionOutput = conversionRequest.getConversionOutput();
		
		conversionOutput.setWorkingDirectory(workDirectory);
		conversionOutput.setOutputFile(outputFile);
		
	}

}
