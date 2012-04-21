package ucar.ral.gis.services.messages;

import java.io.OutputStream;
import java.util.List;

import ucar.ral.gis.services.netcdf2shapefile.rest.longterm.LongTermAverageParameters;
import ucar.ral.gis.services.pipeline.conversion.wms.Range;
import edu.ucar.gis.ipcc.AllTimesConstraint;
import edu.ucar.gis.ipcc.MonthTimeConstraint;
import edu.ucar.gis.ipcc.TimeConstraint;

public class LongTermAverageWMSRequestImpl extends AbstractConversionRequestImpl implements ConversionRequestMessage, WMSRequestMessage {

	private Range range;
	private List<String> dates;
	
	public LongTermAverageWMSRequestImpl(LongTermAverageParameters productRequest, OutputStream outputStream) {
		super();
		this.productRequest = productRequest;
		
		this.conversionOutput = new ConversionOutput(outputStream);
		
		if((null != productRequest.getColorMin()) && (null != productRequest.getColorMax())) {
			
			this.range = new Range();
			this.range.setMin(productRequest.getColorMin());
			this.range.setMax(productRequest.getColorMax());
			
		}
	}

	public Range getRange() {
		return range;
	}

	public void setRange(Range range) {
		this.range = range;
	}

	public List<String> getDates() {
		return dates;
	}

	public void setDates(List<String> dates) {
		this.dates = dates;
	}

	public TimeConstraint getTimeConstraint() {	
		
		LongTermAverageParameters parameters = (LongTermAverageParameters) this.productRequest;
		
		
		TimeConstraint result = null;
		
		if (parameters.getPeriod().equalsIgnoreCase("monthly")) {
			result = new MonthTimeConstraint(this.getMonthIndex(parameters.getMonth()));
		}
		else if (parameters.getPeriod().equalsIgnoreCase("annual")) {
			result = new AllTimesConstraint();
		}
		else if (parameters.getPeriod().equalsIgnoreCase("seasonal")) {
			result = new MonthTimeConstraint(this.getMonthIndexForSeason(parameters.getSeason()));
		}
		
		return result;
		
	}

}
