package edu.ucar.gis.ipcc.model.netcdf2gis.transform;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.geotools.data.FeatureWriter;
import org.geotools.data.Transaction;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.feature.AttributeTypeFactory;
import org.geotools.feature.FeatureTypeFactory;
import org.geotools.feature.SchemaException;

import ucar.ma2.Array;
import ucar.ma2.Index;
import ucar.nc2.dataset.CoordinateAxis;
import ucar.nc2.dataset.CoordinateAxis1DTime;
import ucar.nc2.dt.GridCoordSystem;
import ucar.nc2.dt.grid.GeoGrid;
import ucar.nc2.time.CalendarDate;
import ucar.nc2.time.CalendarDateFormatter;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;

public class ShapeFileTransformer implements Transformer {
	/**
	 * Logger for this class
	 */
	private static final Log LOG = LogFactory.getLog(ShapeFileTransformer.class);
		
	private CalendarDateFormatter dateFormatter = new CalendarDateFormatter("yyyyMM");
	
	private File outputFile;
	
	private static boolean IS_NILLABLE = true;
	private static int FIELD_WIDTH = 10;
	private static String FEATURE_TYPE_NAME = "ccsm";

	private ShapefileDataStore shapefileDataStore;
	private FeatureWriter featureWriter;
	private int featureWriterFlushSize = 1000;
	private int featureWriterSize = 0;
	
	/**
	 * @param outputFile
	 * @param queue
	 * @param complete
	 */
	public ShapeFileTransformer() {
		
	}
	
	protected void createSchema(GeoGrid dataset) throws IOException, SchemaException {
		
		FeatureTypeFactory featureTypeFactory = FeatureTypeFactory.newInstance(FEATURE_TYPE_NAME);
		featureTypeFactory.addType(AttributeTypeFactory.newAttributeType("Shape", Geometry.class));
		featureTypeFactory.addType(AttributeTypeFactory.newAttributeType("GID_LAT", Float.class, IS_NILLABLE, FIELD_WIDTH, new Float(0.0)));
		featureTypeFactory.addType(AttributeTypeFactory.newAttributeType("GID_LON", Float.class, IS_NILLABLE, FIELD_WIDTH, new Float(0.0)));
		
		CoordinateAxis1DTime subsetTimeAxis = dataset.getCoordinateSystem().getTimeAxis1D();
		
		List<CalendarDate> range = subsetTimeAxis.getCalendarDates();
		
		for (CalendarDate calendarDate : range) {
			
			featureTypeFactory.addType(AttributeTypeFactory.newAttributeType(dateFormatter.toString(calendarDate), Float.class, IS_NILLABLE, FIELD_WIDTH, new Float(0.0)));
		}
		
		shapefileDataStore.createSchema(featureTypeFactory.getFeatureType());
		featureWriter = shapefileDataStore.getFeatureWriter(FEATURE_TYPE_NAME, Transaction.AUTO_COMMIT);
		
	}
	
	public void transform(GeoGrid dataset, File outputFile) throws Exception {
		
		this.outputFile = outputFile;

		try {

			this.shapefileDataStore = new ShapefileDataStore(outputFile.toURL());
			
			this.createSchema(dataset);

			GridCoordSystem coordinates = dataset.getCoordinateSystem();
			
			CoordinateAxis yAxis = coordinates.getYHorizAxis();
			
			System.out.println("Min: " + yAxis.getMinValue() + " Max: " + yAxis.getMaxValue());
			System.out.println("Y Dimension: " + dataset.getYDimension().getLength());
			System.out.println("X Dimension: " + dataset.getXDimension().getLength());
			
			Array latValues = coordinates.getYHorizAxis().read();
			Array lonValues = coordinates.getXHorizAxis().read();
			
			Index latIndex = latValues.getIndex();
			Index lonIndex = lonValues.getIndex();

			for (int latLoopIndex = 0; latLoopIndex < dataset.getYDimension().getLength(); latLoopIndex++) {
				for (int lonLoopIndex = 0; lonLoopIndex < dataset.getXDimension().getLength(); lonLoopIndex++) {
					
					latIndex.set(latLoopIndex);
					
					lonIndex.set(lonLoopIndex);
					
					List<Object> attributeValues = new ArrayList<Object>();
					
					attributeValues.add(new GeometryFactory().createPoint(new Coordinate(lonValues.getFloat(lonIndex), latValues.getFloat(latIndex))));
					attributeValues.add(new Float(latValues.getFloat(latIndex)));
					attributeValues.add(new Float(lonValues.getFloat(lonIndex)));
					
					Array values = dataset.readDataSlice(-1, 10, latLoopIndex, lonLoopIndex);
					
					for (int i = 0; i < values.getSize(); i++) {
						
						attributeValues.add(values.getFloat(i));
						
					}
					
					featureWriter.next().setAttributes(attributeValues.toArray());
					
					if(++featureWriterSize == featureWriterFlushSize) {
						featureWriter.write();
						featureWriterSize = 0;
					}
							 
				}


			}
			
		} finally {
			if (null != featureWriter) {
				// Makie sure we flush any pending writes...
				featureWriter.write();
				featureWriter.close();
			}
		}


	}

}
