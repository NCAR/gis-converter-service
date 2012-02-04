package ucar.ral.gis.services.messages;

import java.io.OutputStream;

import org.springframework.util.StringUtils;

import ucar.ral.gis.services.netcdf2shapefile.rest.monthly.MonthlyMeanParameters;
import edu.ucar.gis.ipcc.ConversionRequest;
import edu.ucar.gis.ipcc.TimeConstraint;
import edu.ucar.gis.ipcc.YearMonthTimeConstraint;
import edu.ucar.gis.ipcc.YearTimeConstraint;

public class MonthlyMeanConversionRequestImpl extends AbstractConversionRequestImpl implements ConversionRequestMessage, ConversionRequest {
	
	public MonthlyMeanConversionRequestImpl(MonthlyMeanParameters productRequest, OutputStream outputStream) {
		super();
		this.productRequest = productRequest;
		
		this.conversionOutput = new ConversionOutput(outputStream);
	}
	
	public TimeConstraint getTimeConstraint() {	
		
		MonthlyMeanParameters monthlyMeanParameters = (MonthlyMeanParameters) this.productRequest;
		
		
		TimeConstraint result = null;
		
		// Figure out if we are getting all or selected months.
		
		// Do all months first
		if (!StringUtils.hasText(monthlyMeanParameters.getMonth())) {
			
			result = new YearTimeConstraint(monthlyMeanParameters.getStartYear(), monthlyMeanParameters.getEndYear());
		}
		else {
			
			int monthIndex = this.getMonthIndex(monthlyMeanParameters.getMonth());
			
			result = new YearMonthTimeConstraint(monthlyMeanParameters.getStartYear(), monthlyMeanParameters.getEndYear(), monthIndex);
		}
				
		return result;
		
	}
	

}
