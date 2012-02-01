package ucar.ral.gis.services.messages;

import java.io.OutputStream;

import ucar.ral.gis.services.netcdf2shapefile.rest.longterm.LongTermAverageParameters;
import edu.ucar.gis.ipcc.AllTimesConstraint;
import edu.ucar.gis.ipcc.ConversionRequest;
import edu.ucar.gis.ipcc.MonthTimeConstraint;
import edu.ucar.gis.ipcc.TimeConstraint;

public class LongTermAverageConversionRequestImpl extends AbstractConversionRequestImpl implements ConversionRequestMessage, ConversionRequest {

	public LongTermAverageConversionRequestImpl(LongTermAverageParameters productRequest, OutputStream outputStream) {
		super();
		this.productRequest = productRequest;
		
		this.conversionOutput = new ConversionOutput(outputStream);
	}
	
	public TimeConstraint getTimeConstraint() {	
		
		LongTermAverageParameters parameters = (LongTermAverageParameters) this.productRequest;
		
		
		TimeConstraint result = null;
		
		if (parameters.getPeriod().equalsIgnoreCase("monthly")) {
			result = new MonthTimeConstraint(this.getMonthIndex(parameters.getTerm()));
		}
		else if (parameters.getPeriod().equalsIgnoreCase("annual")) {
			result = new AllTimesConstraint();
		}
		// seasonal dates: 2099/2/14, 2099/5/15, 2099/8/15, 2099/11/15, 
		
		return result;
		
	}

}
