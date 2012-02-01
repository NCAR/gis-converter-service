package ucar.ral.gis.services.messages;

import java.io.OutputStream;

import ucar.ral.gis.services.netcdf2shapefile.rest.annual.AnnualMeanParameters;
import edu.ucar.gis.ipcc.ConversionRequest;
import edu.ucar.gis.ipcc.TimeConstraint;
import edu.ucar.gis.ipcc.YearTimeConstraint;

public class AnnualMeanConversionRequestImpl extends AbstractConversionRequestImpl implements ConversionRequestMessage, ConversionRequest {

	public AnnualMeanConversionRequestImpl(AnnualMeanParameters productRequest, OutputStream outputStream) {
		super();
		this.productRequest = productRequest;
		
		this.conversionOutput = new ConversionOutput(outputStream);
	}
	
	public TimeConstraint getTimeConstraint() {	
		
		AnnualMeanParameters annualMeanParameters = (AnnualMeanParameters) this.productRequest;
		
		TimeConstraint result = new YearTimeConstraint(annualMeanParameters.getStartYear(), annualMeanParameters.getEndYear());
				
		return result;
		
	}

}
