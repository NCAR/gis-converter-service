package ucar.ral.gis.services;

import java.io.File;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import ucar.ral.gis.services.messages.ConversionOutput;
import ucar.ral.gis.services.messages.ConversionRequestMessage;
import ucar.ral.gis.services.web.BaseParameters;
import ucar.ral.gis.services.web.MonthlyMeanParameters;
import edu.ucar.gis.ipcc.ConversionRequest;
import edu.ucar.gis.ipcc.model.netcdf2gis.AxisConstraint2;
import edu.ucar.gis.ipcc.model.netcdf2gis.Latitude;
import edu.ucar.gis.ipcc.model.netcdf2gis.Longitude;

public class MonthlyMeanConversionRequestImpl implements ConversionRequestMessage, ConversionRequest {
	
	private static final NumberFormat monthFormat = new DecimalFormat("00");
	private static final NumberFormat yearFormat = new DecimalFormat("0000");
	
	
	private MonthlyMeanParameters productRequest;
	private File dataFile;
	
	
	private ConversionOutput conversionOutput;
	
	public MonthlyMeanConversionRequestImpl(MonthlyMeanParameters productRequest, OutputStream outputStream) {
		super();
		this.productRequest = productRequest;
		
		this.conversionOutput = new ConversionOutput(outputStream);
	}
	
	public void setDataFile(File dataFile) {
		this.dataFile = dataFile;
	}
	
	public String getDataFileName() {
		
		return this.dataFile.getAbsolutePath();
	}

	public String getVariableName() {
		
		return this.productRequest.getVariable();
	}

	public AxisConstraint2<String> getTimeConstraint() {	
		
		AxisConstraint2<String> result = new AxisConstraint2<String>();
		
		String startYearStr = yearFormat.format(this.productRequest.getStartYear());
		String endYearStr = yearFormat.format(this.productRequest.getEndYear());
		
		if(0 == this.productRequest.getMonth().getId()) {
			result.setMin(startYearStr + "/01/01");
			result.setMax(endYearStr + "/12/31");
		}
		else {
			result.setMin(startYearStr + "/" +monthFormat.format(this.productRequest.getMonth().getId())+ "/01");
			result.setMax(endYearStr + "/" +monthFormat.format(this.productRequest.getMonth().getId())+ "/28");
		}
		
		result.setStep(this.productRequest.getMonth().getTimeStep());
		
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

	public String getOutputFileName() {
		
		return this.conversionOutput.getOutputFile().getAbsolutePath();
	}

	public ConversionOutput getConversionOutput() {
		
		return this.conversionOutput;
	}




	public BaseParameters getParameters() {
		return productRequest;
	}
	

}
