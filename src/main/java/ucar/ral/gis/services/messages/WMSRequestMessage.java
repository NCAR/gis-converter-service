package ucar.ral.gis.services.messages;

import java.io.File;

import ucar.ral.gis.services.netcdf2shapefile.rest.BaseParameters;
import ucar.ral.gis.services.pipeline.conversion.wms.Range;

public interface WMSRequestMessage {
	
	Range getRange();
	
	void setRange(Range range);
	
}
