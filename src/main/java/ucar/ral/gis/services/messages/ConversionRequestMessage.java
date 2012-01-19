package ucar.ral.gis.services.messages;

import java.io.File;

import ucar.ral.gis.services.web.BaseParameters;

public interface ConversionRequestMessage {
	
	ConversionOutput getConversionOutput();
	
	BaseParameters getParameters();

	void setDataFile(File dataFile);


}
