package ucar.ral.gis.services.pipeline;

import java.io.File;
import java.util.UUID;

import ucar.ral.gis.services.ConversionRequestImpl;
import ucar.ral.gis.services.OutputFileNameFactory;

public class OutputFileProcessor implements Processor {
	
	private File scratchDirectory;
	private OutputFileNameFactory outputFileNameFactory;
	
	
	public OutputFileProcessor(File scratchDirectory, OutputFileNameFactory outputFileNameFactory) {
		super();
		this.scratchDirectory = scratchDirectory;
		this.outputFileNameFactory = outputFileNameFactory;
	}

	public void process(ConversionRequestImpl conversionRequest) {
		
		String outputFilename = this.outputFileNameFactory.create(conversionRequest.getProductRequest());
		
		
		File workDirectory = new File(this.scratchDirectory, UUID.randomUUID().toString());
		File outputDirectory = new File(workDirectory, "/output/");
		
		outputDirectory.mkdirs();
		
		File outputFile = new File(outputDirectory, outputFilename + "." + conversionRequest.getOutputType().getFileExtension());
		
		conversionRequest.setWorkDirectory(workDirectory);
		conversionRequest.setOutputDirectory(outputFile);
		

	}

}
