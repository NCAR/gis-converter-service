package ucar.ral.gis.services.messages;

import java.io.File;
import java.io.OutputStream;

public class ConversionOutput {

	private File workingDirectory;
	private File outputFile;
	
	private OutputStream outputStream;
	
	public ConversionOutput(OutputStream outputStream) {
		super();
		this.outputStream = outputStream;
	}
	
	public File getWorkingDirectory() {
		return workingDirectory;
	}
	public void setWorkingDirectory(File workingDirectory) {
		this.workingDirectory = workingDirectory;
	}
	public File getOutputFile() {
		return outputFile;
	}
	public void setOutputFile(File outputFile) {
		this.outputFile = outputFile;
	}
	public OutputStream getOutputStream() {
		return outputStream;
	}
	
	
}
