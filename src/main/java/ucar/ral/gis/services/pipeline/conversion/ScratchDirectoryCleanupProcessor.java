package ucar.ral.gis.services.pipeline.conversion;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import ucar.ral.gis.services.OutputFileNameFactory;
import ucar.ral.gis.services.messages.ConversionRequestMessage;
import ucar.ral.gis.services.pipeline.Processor;

public class ScratchDirectoryCleanupProcessor implements Processor {
	
	public ScratchDirectoryCleanupProcessor() {
		super();
	}

	public void process(ConversionRequestMessage conversionRequest) {
		
		File workingDirectory = conversionRequest.getConversionOutput().getWorkingDirectory();
		
		try {
			
			FileUtils.deleteDirectory(workingDirectory);
		} catch (IOException e) {
			
			throw new RuntimeException(e);
		}
		
	}

}
