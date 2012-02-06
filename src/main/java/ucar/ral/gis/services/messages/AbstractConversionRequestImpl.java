package ucar.ral.gis.services.messages;

import java.io.File;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import ucar.ral.gis.services.netcdf2shapefile.rest.BaseParameters;
import edu.ucar.gis.ipcc.model.netcdf2gis.AxisConstraint2;
import edu.ucar.gis.ipcc.model.netcdf2gis.Latitude;
import edu.ucar.gis.ipcc.model.netcdf2gis.Longitude;

public class AbstractConversionRequestImpl {

	protected BaseParameters productRequest;
	private File dataFile;
	protected ConversionOutput conversionOutput;

	public AbstractConversionRequestImpl() {
		super();
	}

	public void setDataFile(File dataFile) {
		this.dataFile = dataFile;
	}

	public File getDataFile() {
		return dataFile;
	}

	public String getDataFileName() {
		
		return this.dataFile.getAbsolutePath();
	}

	public String getVariableName() {
		
		return this.productRequest.getVariable();
	}

	public AxisConstraint2<Latitude> getLatitudeConstraint() {
		
		AxisConstraint2<Latitude> result = new AxisConstraint2<Latitude>(new Latitude(this.productRequest.getYmin()), new Latitude(this.productRequest.getYmax()), 1);
		return result;
	
	}

	public AxisConstraint2<Longitude> getLongitudeConstraint() {
	
		AxisConstraint2<Longitude> result = new AxisConstraint2<Longitude>(new Longitude(this.productRequest.getXmin()), new Longitude(this.productRequest.getXmax()), 1);
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

	protected int getMonthIndex(String month) {
		
		DateTimeFormatter format = DateTimeFormat.forPattern("MMM");
	    DateTime instance = format.parseDateTime(month);  

	    int monthIndex = instance.getMonthOfYear();

	    return monthIndex;
	}
	
	// seasonal dates: 2099/2/14, 2099/5/15, 2099/8/15, 2099/11/15, 
	
	protected int getMonthIndexForSeason(String season) {
		
		int monthIndex = 0;
		
		if (season.equalsIgnoreCase("spring")) {
			monthIndex = 2;
		}
		else if (season.equalsIgnoreCase("summer")) {
			monthIndex = 5;
		}
		else if (season.equalsIgnoreCase("fall")) {
			monthIndex = 8;
		}
		else if (season.equalsIgnoreCase("winter")) {
			monthIndex = 11;
		}
		
	    return monthIndex;
	}
	
}