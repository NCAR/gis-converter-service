package ucar.ral.gis.services.messages;

import edu.ucar.gis.ipcc.ConversionRequest;
import edu.ucar.gis.ipcc.TimeConstraint;

public class LongTermAverageConversionRequestImpl extends AbstractConversionRequestImpl implements ConversionRequestMessage, ConversionRequest {

	public TimeConstraint getTimeConstraint() {	
		
		//TimeConstraint result = new YearTimeConstraint(this.productRequest.getStartYear(), this.productRequest.getEndYear());
				
		return null;
		
	}

}
