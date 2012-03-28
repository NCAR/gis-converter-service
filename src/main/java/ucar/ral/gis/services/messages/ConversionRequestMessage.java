package ucar.ral.gis.services.messages;

import java.io.File;

import ucar.ral.gis.services.netcdf2shapefile.rest.BaseParameters;

public interface ConversionRequestMessage {
	
	ConversionOutput getConversionOutput();
	
	BaseParameters getParameters();

	void setDataFile(File dataFile);
	
	File getDataFile();


}
