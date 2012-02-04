package ucar.ral.gis.services.netcdf2shapefile.rest.anomaly;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ucar.ral.gis.services.OutputFileNameFactory;
import ucar.ral.gis.services.OutputType;
import ucar.ral.gis.services.TemporalResolution;
import ucar.ral.gis.services.messages.ConversionRequestMessage;
import ucar.ral.gis.services.messages.LongTermAverageConversionRequestImpl;
import ucar.ral.gis.services.netcdf2shapefile.rest.longterm.LongTermAverageParameters;
import ucar.ral.gis.services.pipeline.Processor;

@Controller
public class AnomalyController {
	
	private Processor shapefileProcessor;
	private Processor debugProcessor;
	
	private OutputFileNameFactory outputFileNameFactory;
	
	
	public AnomalyController(OutputFileNameFactory outputFileNameFactory, Processor shapefileProcessor, Processor debugProcessor) {
		super();
		this.outputFileNameFactory = outputFileNameFactory;
		this.shapefileProcessor = shapefileProcessor;
		this.debugProcessor = debugProcessor;
	}
	
	/**
	 * 
	 * timePeriod -> monthly | annual | seasonal
	 * 
	 * monthly --> [Jan-Dec] [near, mid, end, last]
	 * annual --> [annual] [near, mid, end, last]
	 * seasonal --> [winter, spring, summmer, fall] [near, mid, end, last]
	 */
	
	@RequestMapping(value="/{scale}/{variable}/{scenario}/anomaly/monthly/{term}/{season}")
	public ModelAndView longTermAverageDiagnostics(LongTermAverageParameters requestParameters, HttpServletResponse response) throws InterruptedException, ExecutionException, IOException {
		
		requestParameters.setPeriod("monthly");
		
		LongTermAverageConversionRequestImpl conversionRequestMessage = new LongTermAverageConversionRequestImpl(requestParameters, null);
		
		// FIXME - Find a better way to deal with this.
		conversionRequestMessage.getParameters().setTemporalResolution(TemporalResolution.LONGTERM_AVERAGE);
		
		this.debugProcessor.process(conversionRequestMessage);
		
		ModelMap modelMap = new ModelMap("conversionRequest", conversionRequestMessage);
		modelMap.addAttribute("dataFileExists", conversionRequestMessage.getDataFile().exists());
		
		
		return new ModelAndView("validate-annual-mean", modelMap); 
		
	}
	
		
	@RequestMapping(value="/{scale}/{variable}/{scenario}/anomaly/monthly/{term}/{season}.shp")
	public ModelAndView convertToShapefile(LongTermAverageParameters requestParameters, HttpServletResponse response) throws InterruptedException, ExecutionException, IOException {
		
		requestParameters.setOutputType(OutputType.SHAPE);
		requestParameters.setTemporalResolution(TemporalResolution.LONGTERM_AVERAGE);
		requestParameters.setPeriod("monthly");
		
		
		this.convert(new LongTermAverageConversionRequestImpl(requestParameters, response.getOutputStream()), response);
		
		return null; 
	}
	
	@RequestMapping(value="/{scale}/{variable}/{scenario}/anomaly/monthly/{term}/{season}.txt")
	public ModelAndView convertToTextfile(LongTermAverageParameters requestParameters, HttpServletResponse response) throws InterruptedException, ExecutionException, IOException {
		
		requestParameters.setOutputType(OutputType.TEXT);
		requestParameters.setTemporalResolution(TemporalResolution.LONGTERM_AVERAGE);
		requestParameters.setPeriod("monthly");
		
		this.convert(new LongTermAverageConversionRequestImpl(requestParameters, response.getOutputStream()), response);
		
		return null;
	}

	@RequestMapping(value="/{scale}/{variable}/{scenario}/anomaly/annual/{season}")
	public ModelAndView longTermAverageDiagnosticsAnnual(LongTermAverageParameters requestParameters, HttpServletResponse response) throws InterruptedException, ExecutionException, IOException {
		
		requestParameters.setPeriod("annual");
		
		LongTermAverageConversionRequestImpl conversionRequestMessage = new LongTermAverageConversionRequestImpl(requestParameters, null);
		
		// FIXME - Find a better way to deal with this.
		conversionRequestMessage.getParameters().setTemporalResolution(TemporalResolution.LONGTERM_AVERAGE);
		
		this.debugProcessor.process(conversionRequestMessage);
		
		ModelMap modelMap = new ModelMap("conversionRequest", conversionRequestMessage);
		modelMap.addAttribute("dataFileExists", conversionRequestMessage.getDataFile().exists());
		
		
		return new ModelAndView("validate-annual-mean", modelMap); 
		
	}
	
		
	@RequestMapping(value="/{scale}/{variable}/{scenario}/anomaly/annual/{season}.shp")
	public ModelAndView convertToShapefileAnnual(LongTermAverageParameters requestParameters, HttpServletResponse response) throws InterruptedException, ExecutionException, IOException {
		
		requestParameters.setOutputType(OutputType.SHAPE);
		requestParameters.setTemporalResolution(TemporalResolution.LONGTERM_AVERAGE);
		requestParameters.setPeriod("annual");
		
		
		this.convert(new LongTermAverageConversionRequestImpl(requestParameters, response.getOutputStream()), response);
		
		return null; 
	}
	
	@RequestMapping(value="/{scale}/{variable}/{scenario}/anomaly/annual/{season}.txt")
	public ModelAndView convertToTextfileAnnual(LongTermAverageParameters requestParameters, HttpServletResponse response) throws InterruptedException, ExecutionException, IOException {
		
		requestParameters.setOutputType(OutputType.TEXT);
		requestParameters.setTemporalResolution(TemporalResolution.LONGTERM_AVERAGE);
		requestParameters.setPeriod("annual");
		
		this.convert(new LongTermAverageConversionRequestImpl(requestParameters, response.getOutputStream()), response);
		
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
