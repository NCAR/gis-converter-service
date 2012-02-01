package ucar.ral.gis.services.pipeline.conversion.netcdf.sourcefile;

import java.io.File;
import java.io.FileFilter;

import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.WildcardFileFilter;

import ucar.ral.gis.services.messages.ConversionRequestMessage;
import ucar.ral.gis.services.pipeline.Processor;

public class SourceDatafileProcessor implements Processor {
	
	private SourceFileHandler sourceFileHandler;
	
	private SourceDatafileProcessor(SourceFileHandler sourceFileHandler) {
		super();
		this.sourceFileHandler = sourceFileHandler;
	}



	public void process(ConversionRequestMessage conversionRequest) {
		
		FileSpecification fileSpecification = this.sourceFileHandler.resolveSourceFile(conversionRequest.getParameters());
		
		File dataFile = this.findFile(fileSpecification);
		
		conversionRequest.setDataFile(dataFile);

	}

	
	protected File findFile(FileSpecification fileSpecification) {
	
		
		 FileFilter fileFilter = new WildcardFileFilter(fileSpecification.getFilenamePattern(), IOCase.INSENSITIVE);
		 File[] files = fileSpecification.getDirectory().listFiles(fileFilter);
		 for (int i = 0; i < files.length; i++) {
		   System.out.println(files[i]);
		 }
		 
		 
		if (0 == files.length) {
			throw new RuntimeException("Found 0 files using wildcard: " + fileSpecification.getFilenamePattern());
		}
		return files[0];
	}
}
