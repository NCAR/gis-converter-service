package edu.ucar.gis.ipcc.model.netcdf2gis.transform;

import java.io.File;

import ucar.nc2.dt.grid.GeoGrid;

public interface Transformer {

	void transform(GeoGrid dataset, File outputFile) throws Exception;

}