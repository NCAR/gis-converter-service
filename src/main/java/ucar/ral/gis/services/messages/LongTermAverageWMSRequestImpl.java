package ucar.ral.gis.services.messages;

import java.io.OutputStream;

import ucar.ral.gis.services.netcdf2shapefile.rest.longterm.LongTermAverageParameters;

public class LongTermAverageWMSRequestImpl extends AbstractConversionRequestImpl implements ConversionRequestMessage {

	public LongTermAverageWMSRequestImpl(LongTermAverageParameters productRequest, OutputStream outputStream) {
		super();
		this.productRequest = productRequest;
		
		this.conversionOutput = new ConversionOutput(outputStream);
	}
	
//	public TimeConstraint getTimeConstraint() {	
//		
//		LongTermAverageParameters parameters = (LongTermAverageParameters) this.productRequest;
//		
//		
//		TimeConstraint result = null;
//		
//		if (parameters.getPeriod().equalsIgnoreCase("monthly")) {
//			result = new MonthTimeConstraint(this.getMonthIndex(parameters.getMonth()));
//		}
//		else if (parameters.getPeriod().equalsIgnoreCase("annual")) {
//			result = new AllTimesConstraint();
//		}
//		else if (parameters.getPeriod().equalsIgnoreCase("seasonal")) {
//			result = new MonthTimeConstraint(this.getMonthIndexForSeason(parameters.getSeason()));
//		}
//		
//		return result;
//		
//	}

}
