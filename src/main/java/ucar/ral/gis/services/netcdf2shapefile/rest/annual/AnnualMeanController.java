package ucar.ral.gis.services.netcdf2shapefile.rest.annual;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ucar.ral.gis.services.DerivedProductConversionRequestImpl;
import ucar.ral.gis.services.OutputFileNameFactory;
import ucar.ral.gis.services.OutputType;
import ucar.ral.gis.services.TemporalResolution;
import ucar.ral.gis.services.messages.ConversionRequestMessage;
import ucar.ral.gis.services.netcdf2shapefile.rest.DerivedProductParameters;
import ucar.ral.gis.services.pipeline.Processor;

@Controller
public class AnnualMeanController {
	
	private Processor shapefileProcessor;
	private Processor debugProcessor;
	
	private OutputFileNameFactory outputFileNameFactory;
	
	
	public AnnualMeanController(OutputFileNameFactory outputFileNameFactory, Processor shapefileProcessor, Processor debugProcessor) {
		super();
		this.outputFileNameFactory = outputFileNameFactory;
		this.shapefileProcessor = shapefileProcessor;
		this.debugProcessor = debugProcessor;
	}

	
	
	// Annual mean methods
	@RequestMapping(value="/{scale}/{variable}/{scenario}/annual/mean/{startYear}/{endYear}")
	public ModelAndView annualMeanDiagnostics(DerivedProductParameters requestParameters, HttpServletResponse response) throws InterruptedException, ExecutionException, IOException {
		
		DerivedProductConversionRequestImpl conversionRequestMessage = new DerivedProductConversionRequestImpl(requestParameters, null);
		
		// FIXME - Find a better way to deal with this.
		conversionRequestMessage.getParameters().setTemporalResolution(TemporalResolution.ANNUAL_MEAN);
		
		this.debugProcessor.process(conversionRequestMessage);
		
		ModelMap modelMap = new ModelMap("conversionRequest", conversionRequestMessage);
		modelMap.addAttribute("dataFileExists", conversionRequestMessage.getDataFile().exists());
		
		
		return new ModelAndView("validate-annual-mean", modelMap); 
	}
	
	
	@RequestMapping(value="/{scale}/{variable}/{scenario}/annual/mean/{startYear}/{endYear}.shp")
	public ModelAndView convertAnnualMeanToShapefile(DerivedProductParameters requestParameters, HttpServletResponse response) throws InterruptedException, ExecutionException, IOException {
		
		requestParameters.setTemporalResolution(TemporalResolution.ANNUAL_MEAN);
		requestParameters.setOutputType(OutputType.SHAPE);
		
		this.convert(new DerivedProductConversionRequestImpl(requestParameters, response.getOutputStream()), response);
		
		return null; 
	}
	
	@RequestMapping(value="/{scale}/{variable}/{scenario}/annual/mean/{startYear}/{endYear}.txt")
	public ModelAndView convertAnnualMeanToTextfile(DerivedProductParameters requestParameters, HttpServletResponse response) throws InterruptedException, ExecutionException, IOException {
		
		requestParameters.setTemporalResolution(TemporalResolution.ANNUAL_MEAN);
		requestParameters.setOutputType(OutputType.TEXT);
		
		this.convert(new DerivedProductConversionRequestImpl(requestParameters, response.getOutputStream()), response);
		
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
