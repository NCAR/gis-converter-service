package ucar.ral.gis.services.messages;

import java.io.OutputStream;

import ucar.ral.gis.services.netcdf2shapefile.rest.longterm.LongTermAverageParameters;
import ucar.ral.gis.services.pipeline.conversion.wms.Range;

public class LongTermAverageWMSRequestImpl extends AbstractConversionRequestImpl implements ConversionRequestMessage, WMSRequestMessage {

	private Range range;
	
	public LongTermAverageWMSRequestImpl(LongTermAverageParameters productRequest, OutputStream outputStream) {
		super();
		this.productRequest = productRequest;
		
		this.conversionOutput = new ConversionOutput(outputStream);
	}

	public Range getRange() {
		return range;
	}

	public void setRange(Range range) {
		this.range = range;
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
