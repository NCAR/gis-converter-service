package ucar.ral.gis.services.pipeline.conversion.netcdf.sourcefile;

import java.io.File;

public class FileSpecification {

	private File directory;
	private String filenamePattern;
	
	public FileSpecification(File directory, String filenamePattern) {
		super();
		this.directory = directory;
		this.filenamePattern = filenamePattern;
	}

	public File getDirectory() {
		return directory;
	}

	public String getFilenamePattern() {
		return filenamePattern;
	}
	
	
	
}
