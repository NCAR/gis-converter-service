package ucar.ral.gis.services.web;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ucar.ral.gis.services.DerivedProductConversionRequestImpl;
import ucar.ral.gis.services.MonthlyMeanConversionRequestImpl;
import ucar.ral.gis.services.OutputFileNameFactory;
import ucar.ral.gis.services.OutputType;
import ucar.ral.gis.services.messages.ConversionRequestMessage;
import ucar.ral.gis.services.pipeline.Processor;

@Controller
public class NetCDF2Shapefile {
	
	private Processor shapefileProcessor;
	private Processor debugProcessor;
	
	private OutputFileNameFactory outputFileNameFactory;
	
	
	public NetCDF2Shapefile(OutputFileNameFactory outputFileNameFactory, Processor shapefileProcessor, Processor debugProcessor) {
		super();
		this.outputFileNameFactory = outputFileNameFactory;
		this.shapefileProcessor = shapefileProcessor;
		this.debugProcessor = debugProcessor;
	}

	@RequestMapping(value="/{scale}/{variable}/{scenario}/{ensemble}/{month}/{startYear}/{endYear}")
	public ModelAndView monthlyMeanToShapefileDiagnostics(MonthlyMeanParameters requestParameters, HttpServletResponse response) throws InterruptedException, ExecutionException, IOException {
		
		MonthlyMeanConversionRequestImpl conversionRequestMessage = new MonthlyMeanConversionRequestImpl(requestParameters, null);
		
		this.debugProcessor.process(conversionRequestMessage);
		
		ModelMap modelMap = new ModelMap("conversionRequest", conversionRequestMessage);
		modelMap.addAttribute("dataFileExists", conversionRequestMessage.getDataFile().exists());
		
		
		return new ModelAndView("validate-monthly-mean", modelMap); 
	}
	
	@RequestMapping(value="/{scale}/{variable}/{scenario}/{ensemble}/{month}/{startYear}/{endYear}.shp")
	public ModelAndView convertMonthlyMeanToShapefile(MonthlyMeanParameters requestParameters, HttpServletResponse response) throws InterruptedException, ExecutionException, IOException {
		
		requestParameters.setOutputType(OutputType.SHAPE);
		
		this.convert(new MonthlyMeanConversionRequestImpl(requestParameters, response.getOutputStream()), response);
		
		return null; 
	}
	
	@RequestMapping(value="/{scale}/{variable}/{scenario}/{ensemble}/{month}/{startYear}/{endYear}.txt")
	public ModelAndView convertMonthlyMeanToTextfile(MonthlyMeanParameters requestParameters, HttpServletResponse response) throws InterruptedException, ExecutionException, IOException {
		
		requestParameters.setOutputType(OutputType.TEXT);
		
		this.convert(new MonthlyMeanConversionRequestImpl(requestParameters, response.getOutputStream()), response);
		
		return null;
	}
	
	///
	
	
	@RequestMapping(value="/{scale}/{variable}/{scenario}/{ensemble}/anme.shp")
	public ModelAndView convertAnnualMeanToShapefile(DerivedProductParameters requestParameters, HttpServletResponse response) throws InterruptedException, ExecutionException, IOException {
		
		requestParameters.setOutputType(OutputType.SHAPE);
		
		this.convert(new DerivedProductConversionRequestImpl(requestParameters, response.getOutputStream()), response);
		
		return null; 
	}
	
	@RequestMapping(value="/{scale}/{variable}/{scenario}/{ensemble}/anme.txt")
	public ModelAndView convertAnnualMeanToTextfile(DerivedProductParameters requestParameters, HttpServletResponse response) throws InterruptedException, ExecutionException, IOException {
		
		requestParameters.setOutputType(OutputType.TEXT);
		
		this.convert(new DerivedProductConversionRequestImpl(requestParameters, response.getOutputStream()), response);
		
		return null;
	}
		
	@RequestMapping(value="/{scale}/{variable}/{scenario}/{ensemble}/{temporalResolution}.shp")
	public ModelAndView convertToShapefile(MonthlyMeanParameters requestParameters, HttpServletResponse response) throws InterruptedException, ExecutionException, IOException {
		
		requestParameters.setOutputType(OutputType.SHAPE);
		
		this.convert(new MonthlyMeanConversionRequestImpl(requestParameters, response.getOutputStream()), response);
		
		return null; 
	}
	
	@RequestMapping(value="/{scale}/{variable}/{scenario}/{ensemble}/{temporalResolution}.txt")
	public ModelAndView convertToTextfile(MonthlyMeanParameters requestParameters, HttpServletResponse response) throws InterruptedException, ExecutionException, IOException {
		
		requestParameters.setOutputType(OutputType.TEXT);
		
		this.convert(new MonthlyMeanConversionRequestImpl(requestParameters, response.getOutputStream()), response);
		
		return null;
	}
	
	
	
	
	public void convert(ConversionRequestMessage conversionRequestMessage, HttpServletResponse response) throws InterruptedException, ExecutionException, IOException {
		
		String outputFilename = this.outputFileNameFactory.create(conversionRequestMessage.getParameters());
		
		response.setHeader("Content-Type", "application/zip");
		response.setHeader("Content-Disposition", "attachment; filename=" + outputFilename + ".zip");
		
//		/MonthlyMeanConversionRequestImpl conversionRequest = new MonthlyMeanConversionRequestImpl(requestParameters, response.getOutputStream(), outputType);
		
		this.shapefileProcessor.process(conversionRequestMessage);
		
	}
	
}
