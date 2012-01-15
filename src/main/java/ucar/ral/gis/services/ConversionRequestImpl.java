package ucar.ral.gis.services;

import java.io.File;
import java.io.OutputStream;

import ucar.ral.gis.services.web.RequestParameters;
import edu.ucar.gis.ipcc.ConversionRequest;
import edu.ucar.gis.ipcc.model.netcdf2gis.AxisConstraint2;
import edu.ucar.gis.ipcc.model.netcdf2gis.Latitude;
import edu.ucar.gis.ipcc.model.netcdf2gis.Longitude;

public class ConversionRequestImpl implements ConversionRequest {
	
	private RequestParameters productRequest;
	private File dataFile;
	private File outputDirectory;
	private OutputStream outputStream;
	private OutputType outputType;
	
	public ConversionRequestImpl(RequestParameters productRequest, OutputStream outputStream, OutputType outputType) {
		super();
		this.productRequest = productRequest;
		this.outputStream = outputStream;
		this.outputType = outputType;
	}
	
	public OutputType getOutputType() {
		return outputType;
	}

	public OutputStream getOutputStream() {
		return outputStream;
	}

	public void setDataFile(File dataFile) {
		this.dataFile = dataFile;
	}

	public void setOutputDirectory(File outputDirectory) {
		this.outputDirectory = outputDirectory;
	}

	public String getOutputFileName() {
		
		return this.outputDirectory.getAbsolutePath();
	}
	
	public File getOutputFile() {
		
		return this.outputDirectory;
	}

	public String getDataFileName() {
		
		return this.dataFile.getAbsolutePath();
	}

	public String getVariableName() {
		
		return this.productRequest.getVariable();
	}

	public AxisConstraint2<String> getTimeConstraint() {	
		
		AxisConstraint2<String> result = new AxisConstraint2<String>(this.productRequest.getStartDate(), this.productRequest.getEndDate(), this.productRequest.getMonth());
		return result;
		
	}

	public AxisConstraint2<Latitude> getLatitudeConstraint() {
		
		AxisConstraint2<Latitude> result = new AxisConstraint2<Latitude>(new Latitude(this.productRequest.getXmin()), new Latitude(this.productRequest.getXmax()), 1);
		return result;
	
	}

	public AxisConstraint2<Longitude> getLongitudeConstraint() {
	
		AxisConstraint2<Longitude> result = new AxisConstraint2<Longitude>(new Longitude(this.productRequest.getYmin()), new Longitude(this.productRequest.getYmax()), 1);
		return result;
	
	}

	public RequestParameters getProductRequest() {
		return productRequest;
	}
	
	

}
