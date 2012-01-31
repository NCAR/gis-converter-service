package ucar.ral.gis.services.pipeline.conversion.netcdf.sourcefile;

import ucar.ral.gis.services.netcdf2shapefile.rest.BaseParameters;


public interface SourceFileHandler {
	
	FileSpecification resolveSourceFile(BaseParameters baseParameters);

}
