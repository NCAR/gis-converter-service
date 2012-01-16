package ucar.ral.gis.services.pipeline;

import java.io.File;
import java.util.UUID;

import ucar.ral.gis.services.ConversionRequestImpl;
import ucar.ral.gis.services.OutputFilename;

public class OutputFileProcessor implements Processor {
	
	private File scratchDirectory;
	
	public OutputFileProcessor(File scratchDirectory) {
		super();
		this.scratchDirectory = scratchDirectory;
	}

	public void process(ConversionRequestImpl conversionRequest) {
		
		OutputFilename outputFilename = new OutputFilename(conversionRequest.getProductRequest());
		
		
		File workDirectory = new File(this.scratchDirectory, UUID.randomUUID().toString());
		File outputDirectory = new File(workDirectory, "/output/");
		
		outputDirectory.mkdirs();
		
		File outputFile = new File(outputDirectory, outputFilename.getName() + conversionRequest.getOutputType().getFileExtension());
		
		conversionRequest.setWorkDirectory(workDirectory);
		conversionRequest.setOutputDirectory(outputFile);
		

	}

}
