package ucar.ral.gis.services.web;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ucar.ral.gis.services.ConversionRequestImpl;
import ucar.ral.gis.services.OutputFileNameFactory;
import ucar.ral.gis.services.OutputType;
import ucar.ral.gis.services.pipeline.Processor;

@Controller
public class NetCDF2Shapefile {
	
	private Processor shapefileProcessor;
	
	private OutputFileNameFactory outputFileNameFactory;
	
	
	public NetCDF2Shapefile(OutputFileNameFactory outputFileNameFactory, Processor shapefileProcessor) {
		super();
		this.outputFileNameFactory = outputFileNameFactory;
		this.shapefileProcessor = shapefileProcessor;
	}

		
	@RequestMapping(value="/{scale}/{variable}/{scenario}/{ensemble}/{temporalResolution}.shp")
	public ModelAndView convertToShapefile(MonthlyMeanParameters requestParameters, HttpServletResponse response) throws InterruptedException, ExecutionException, IOException {
		
		this.convert(requestParameters, response, OutputType.SHAPE);
		
		return null; 
	}
	
	@RequestMapping(value="/{scale}/{variable}/{scenario}/{ensemble}/{temporalResolution}.txt")
	public ModelAndView convertToTextfile(MonthlyMeanParameters requestParameters, HttpServletResponse response) throws InterruptedException, ExecutionException, IOException {
		
		this.convert(requestParameters, response, OutputType.TEXT);
		
		return null;
	}
	
	public void convert(MonthlyMeanParameters requestParameters, HttpServletResponse response, OutputType outputType) throws InterruptedException, ExecutionException, IOException {
		
		String outputFilename = this.outputFileNameFactory.create(requestParameters);
		
		response.setHeader("Content-Type", "application/zip");
		response.setHeader("Content-Disposition", "attachment; filename=" + outputFilename + ".zip");
		
		ConversionRequestImpl conversionRequest = new ConversionRequestImpl(requestParameters, response.getOutputStream(), outputType);
		
		this.shapefileProcessor.process(conversionRequest);
		
	}
	
}
