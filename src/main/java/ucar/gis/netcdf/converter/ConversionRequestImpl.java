package ucar.gis.netcdf.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;

import edu.ucar.gis.ipcc.AxisConstraint2;
import edu.ucar.gis.ipcc.ConversionRequest;
import edu.ucar.gis.ipcc.Latitude;
import edu.ucar.gis.ipcc.Longitude;
import edu.ucar.gis.ipcc.TimeConstraint;
import edu.ucar.gis.ipcc.YearTimeConstraint;

public class ConversionRequestImpl implements ConversionRequest {
	
	@Argument(required = true, index = 0, metaVar = "FILE", usage = "NetCDF file to convert")
	private String dataFileName;
	
	@Argument(required = true, index = 1, metaVar = "OUTPUT FILE", usage = "Converted filename")
	private String outputFileName;
	
	@Argument(required = true, index = 2, metaVar = "VARIABLE", usage = "Variable name to extract")
	private String variableName;
	
	@Option(name = "--start-date", usage = "Start date, format: YYYY/MM/DD default: 1870/01/01")
	private String startDate = "2000/01/01";
	
	@Option(name = "--end-date", usage = "End date, format: YYYY/MM/DD default: 1870/12/31")
	private String endDate = "2010/01/01";
	
	@Option(name = "--min-lat", usage = "Min Latitude range, default: -90.0")
	private Double minLat = -90.0;
	@Option(name = "--max-lat", usage = "Max Latitude range, default: 90.0")
	private Double maxLat = 90.0;
	
	@Option(name = "--min-lon", usage = "Min Longitude range, default: -180.0")
	private Double minLon = -180.0;
	@Option(name = "--max-lon", usage = "Max Longitude range, default: 180.0")
	private Double maxLon = 180.0;
	
	public String getOutputFileName() {
		return outputFileName;
	}

	public String getDataFileName() {
		return this.dataFileName;
	}

	public String getVariableName() {
		return this.variableName;
	}
	
	protected int getYearFromDate(String date) {
		
		String pattern = "YYYY/MM/DD";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        
        Date parsedDate = null;
        try {
			parsedDate = sdf.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return parsedDate.getYear() + 1900;

	}

	public TimeConstraint getTimeConstraint() {
		
		YearTimeConstraint yearTimeConstraint = new YearTimeConstraint(this.getYearFromDate(startDate), this.getYearFromDate(endDate));
		
		return yearTimeConstraint;
	}

	public AxisConstraint2<Latitude> getLatitudeConstraint() {
		AxisConstraint2<Latitude> result = new AxisConstraint2<Latitude>(new Latitude(this.minLat), new Latitude(this.maxLat), 1);
		return result;
	}

	public AxisConstraint2<Longitude> getLongitudeConstraint() {
		AxisConstraint2<Longitude> result = new AxisConstraint2<Longitude>(new Longitude(this.minLon), new Longitude(this.maxLon), 1);
		return result;
	}

	public void setDataFileName(String dataFileName) {
		this.dataFileName = dataFileName;
	}

	public void setOutputFileName(String outputFileName) {
		this.outputFileName = outputFileName;
	}

	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}

	
	
}
