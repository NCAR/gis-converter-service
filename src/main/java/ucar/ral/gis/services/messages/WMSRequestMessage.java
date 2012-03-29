package ucar.ral.gis.services.messages;

import java.util.List;

import ucar.ral.gis.services.pipeline.conversion.wms.Range;

public interface WMSRequestMessage {
	
	Range getRange();
	
	void setRange(Range range);
	
	List<String> getDates();
	
	void setDates(List<String> dates);
	
}
