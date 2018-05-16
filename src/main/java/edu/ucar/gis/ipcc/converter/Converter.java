/*
 * Class for converting NetCDF data to GIS shapefile format
 * 
 * @author Luca Cinquini, November 2004
 * Copyright 2004-5 UCAR, all rights reserved.
 */
package edu.ucar.gis.ipcc.converter;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ucar.ma2.Range;
import ucar.nc2.NetcdfFile;
import ucar.nc2.dataset.NetcdfDataset;
import ucar.nc2.dt.GridCoordSystem;
import ucar.nc2.dt.grid.GeoGrid;
import ucar.nc2.dt.grid.GridDataset;
import ucar.unidata.geoloc.LatLonPointImpl;
import ucar.unidata.geoloc.LatLonRect;
import edu.ucar.gis.ipcc.ConversionRequest;
import edu.ucar.gis.ipcc.model.netcdf2gis.transform.Transformer;

public class Converter {
	/**
	 * Logger for this class
	 */
	private static final Log LOG = LogFactory.getLog(Converter.class);

	// Need to make this configurable or move it elsewhere....
	private int zIndex = 10;
	
	private ConversionRequest conversionRequest;
	
	private GeoGrid grid = null;
	private GridCoordSystem coordSys = null;
	private File outputFile;
	
	private Transformer transformer;
	
	/**
	 * @param outputCellQueue
	 */
	public Converter(ConversionRequest conversionRequest, File outputFile, Transformer transformer) {
		
		this.conversionRequest = conversionRequest;
		this.outputFile = outputFile;
		this.transformer = transformer;
	}

	
	public void execute() throws Exception {
		
		NetcdfFile ncFile = NetcdfFile.open(conversionRequest.getDataFileName());
		NetcdfDataset dataset = null;

		try {
			dataset = new NetcdfDataset(ncFile);
			
			GridDataset gridDataset = new GridDataset(dataset);
			
			grid = gridDataset.findGridByName(conversionRequest.getVariableName());
			
			if (grid == null) {
				throw new Exception("Variable " + conversionRequest.getVariableName() + " not found in file " + conversionRequest.getDataFileName());
			}

			coordSys = grid.getCoordinateSystem();
			
			Range dateRange = conversionRequest.getTimeConstraint().getTimeRange(coordSys.getTimeAxis1D());
			
			LatLonPointImpl minRect = new LatLonPointImpl(this.conversionRequest.getLatitudeConstraint().getMin().getValue(), this.conversionRequest.getLongitudeConstraint().getMin().getValue());
			LatLonPointImpl maxRect = new LatLonPointImpl(this.conversionRequest.getLatitudeConstraint().getMax().getValue(), this.conversionRequest.getLongitudeConstraint().getMax().getValue());
			
			LatLonRect spatialBounds = new LatLonRect(minRect, maxRect);
			
			//makeSubset(Range t_range, Range z_range, LatLonRect bbox, int z_stride, int y_stride, int x_stride)
			this.grid = (GeoGrid) grid.makeSubset(dateRange, null, spatialBounds, 1, 1, 1);
			
			this.transformer.transform(this.grid, this.outputFile);
			
		} catch (Exception e) {
			LOG.error("call()", e); //$NON-NLS-1$
			throw e;
		} finally {
			if (null != dataset) {
				dataset.close();
			}
		}

	}
	    
} 