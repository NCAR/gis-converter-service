package edu.ucar.gis.ipcc.model.netcdf2gis.transform;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

import org.apache.commons.io.IOUtils;

import ucar.ma2.Array;
import ucar.ma2.Index;
import ucar.nc2.dataset.CoordinateAxis;
import ucar.nc2.dataset.CoordinateAxis1DTime;
import ucar.nc2.dt.GridCoordSystem;
import ucar.nc2.dt.grid.GeoGrid;
import ucar.nc2.time.CalendarDate;
import ucar.nc2.time.CalendarDateFormatter;

public class CSVTransformer implements Transformer {
	
	private CalendarDateFormatter dateFormatter = new CalendarDateFormatter("yyyy/MM/dd");
	
	/* (non-Javadoc)
	 * @see edu.ucar.gis.ipcc.model.netcdf2gis.transform.Transformer#transform(ucar.nc2.dt.grid.GeoGrid, java.io.File)
	 */
	public void transform(GeoGrid dataset, File outputFile) throws IOException {
		
		FileWriter fileWriter = new FileWriter(outputFile);
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		
		this.writeHeaderRow(bufferedWriter, dataset);
		
		this.writeData(bufferedWriter, dataset);
		
		bufferedWriter.flush();
		IOUtils.closeQuietly(bufferedWriter);
		
	}
	
	protected void writeHeaderRow(Writer writer, GeoGrid dataset) throws IOException {
		
		StringBuffer headerRow = new StringBuffer("lat,lon,");
		
		CoordinateAxis1DTime subsetTimeAxis = dataset.getCoordinateSystem().getTimeAxis1D();
		
		List<CalendarDate> range = subsetTimeAxis.getCalendarDates();
		
		for (CalendarDate calendarDate : range) {
			headerRow.append(dateFormatter.toString(calendarDate) + ",");
		}
		
		writer.write(headerRow.toString());
		writer.write("\r\n");
		
	}

	protected void writeData(Writer writer, GeoGrid dataset) throws IOException {
		
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
				
				StringBuffer dataRow = new StringBuffer();
				
				dataRow.append(latValues.getFloat(latIndex) + "," + lonValues.getFloat(lonIndex) + ",");
				
				Array values = dataset.readDataSlice(-1, 10, latLoopIndex, lonLoopIndex);
				
				for (int i = 0; i < values.getSize(); i++) {
					
					dataRow.append(values.getFloat(i) + ", ");
					
				}
				
				writer.write(dataRow.toString());
				
				writer.write("\r\n");
						 
			}


		}
		
	}


}

