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
import ucar.ral.gis.services.messages.LongTermAverageWMSRequestImpl;
import ucar.ral.gis.services.netcdf2shapefile.rest.longterm.LongTermAverageParameters;
import ucar.ral.gis.services.pipeline.Processor;

@Controller
public class AnomalyController {
	
	private Processor shapefileProcessor;
	private Processor debugProcessor;
	private Processor wmsProcessor;
	
	
	private OutputFileNameFactory outputFileNameFactory;
	
	
	public AnomalyController(OutputFileNameFactory outputFileNameFactory, Processor shapefileProcessor, Processor debugProcessor, Processor wmsProcessor) {
		super();
		this.outputFileNameFactory = outputFileNameFactory;
		this.shapefileProcessor = shapefileProcessor;
		this.debugProcessor = debugProcessor;
		this.wmsProcessor = wmsProcessor;
	}
	
	
	
	/**
	 * 
	 * timePeriod -> monthly | annual | seasonal
	 * 
	 * monthly --> [Jan-Dec] [near, mid, end, last]
	 * annual --> [annual] [near, mid, end, last]
	 * seasonal --> [winter, spring, summmer, fall] [near, mid, end, last]
	 */
	
	@RequestMapping(value="/{scale}/{variable}/{scenario}/anomaly/monthly/{month}/{term}")
	public ModelAndView longTermAverageDiagnostics(LongTermAverageParameters requestParameters, HttpServletResponse response) throws InterruptedException, ExecutionException, IOException {
		
		requestParameters.setPeriod("monthly");
		
		LongTermAverageConversionRequestImpl conversionRequestMessage = new LongTermAverageConversionRequestImpl(requestParameters, null);
		
		// FIXME - Find a better way to deal with this.
		conversionRequestMessage.getParameters().setTemporalResolution(TemporalResolution.CLIMATE_ANOMOLY);
		
		this.debugProcessor.process(conversionRequestMessage);
		
		ModelMap modelMap = new ModelMap("conversionRequest", conversionRequestMessage);
		modelMap.addAttribute("dataFileExists", conversionRequestMessage.getDataFile().exists());
		
		
		return new ModelAndView("validate-annual-mean", modelMap); 
		
	}
	
		
	@RequestMapping(value="/{scale}/{variable}/{scenario}/anomaly/monthly/{month}/{term}.shp")
	public ModelAndView convertToShapefile(LongTermAverageParameters requestParameters, HttpServletResponse response) throws InterruptedException, ExecutionException, IOException {
		
		requestParameters.setOutputType(OutputType.SHAPE);
		requestParameters.setTemporalResolution(TemporalResolution.CLIMATE_ANOMOLY);
		requestParameters.setPeriod("monthly");
		
		
		this.convert(new LongTermAverageConversionRequestImpl(requestParameters, response.getOutputStream()), response);
		
		return null; 
	}
	
	@RequestMapping(value="/{scale}/{variable}/{scenario}/anomaly/monthly/{month}/{term}.txt")
	public ModelAndView convertToTextfile(LongTermAverageParameters requestParameters, HttpServletResponse response) throws InterruptedException, ExecutionException, IOException {
		
		requestParameters.setOutputType(OutputType.TEXT);
		requestParameters.setTemporalResolution(TemporalResolution.CLIMATE_ANOMOLY);
		requestParameters.setPeriod("monthly");
		
		this.convert(new LongTermAverageConversionRequestImpl(requestParameters, response.getOutputStream()), response);
		
		return null;
	}
	
	@RequestMapping(value="/{scale}/{variable}/{scenario}/anomaly/monthly/{month}/{term}.wms")
	public ModelAndView convertToWMS(LongTermAverageParameters requestParameters, HttpServletResponse response) throws InterruptedException, ExecutionException, IOException {
		
		requestParameters.setOutputType(OutputType.TEXT);
		requestParameters.setTemporalResolution(TemporalResolution.CLIMATE_ANOMOLY);
		requestParameters.setPeriod("monthly");
		
		this.generateWMS(new LongTermAverageWMSRequestImpl(requestParameters, response.getOutputStream()), response);
		
		return null;
	}

	@RequestMapping(value="/{scale}/{variable}/{scenario}/anomaly/annual/{term}")
	public ModelAndView longTermAverageDiagnosticsAnnual(LongTermAverageParameters requestParameters, HttpServletResponse response) throws InterruptedException, ExecutionException, IOException {
		
		requestParameters.setPeriod("annual");
		
		LongTermAverageConversionRequestImpl conversionRequestMessage = new LongTermAverageConversionRequestImpl(requestParameters, null);
		
		// FIXME - Find a better way to deal with this.
		conversionRequestMessage.getParameters().setTemporalResolution(TemporalResolution.CLIMATE_ANOMOLY);
		
		this.debugProcessor.process(conversionRequestMessage);
		
		ModelMap modelMap = new ModelMap("conversionRequest", conversionRequestMessage);
		modelMap.addAttribute("dataFileExists", conversionRequestMessage.getDataFile().exists());
		
		
		return new ModelAndView("validate-annual-mean", modelMap); 
		
	}
	
		
	@RequestMapping(value="/{scale}/{variable}/{scenario}/anomaly/annual/{term}.shp")
	public ModelAndView convertToShapefileAnnual(LongTermAverageParameters requestParameters, HttpServletResponse response) throws InterruptedException, ExecutionException, IOException {
		
		requestParameters.setOutputType(OutputType.SHAPE);
		requestParameters.setTemporalResolution(TemporalResolution.CLIMATE_ANOMOLY);
		requestParameters.setPeriod("annual");
		
		
		this.convert(new LongTermAverageConversionRequestImpl(requestParameters, response.getOutputStream()), response);
		
		return null; 
	}
	
	@RequestMapping(value="/{scale}/{variable}/{scenario}/anomaly/annual/{term}.txt")
	public ModelAndView convertToTextfileAnnual(LongTermAverageParameters requestParameters, HttpServletResponse response) throws InterruptedException, ExecutionException, IOException {
		
		requestParameters.setOutputType(OutputType.TEXT);
		requestParameters.setTemporalResolution(TemporalResolution.CLIMATE_ANOMOLY);
		requestParameters.setPeriod("annual");
		
		this.convert(new LongTermAverageConversionRequestImpl(requestParameters, response.getOutputStream()), response);
		
		return null;
	}
	
	@RequestMapping(value="/{scale}/{variable}/{scenario}/anomaly/annual/{term}.wms")
	public ModelAndView convertToWMSAnnual(LongTermAverageParameters requestParameters, HttpServletResponse response) throws InterruptedException, ExecutionException, IOException {
		
		requestParameters.setOutputType(OutputType.TEXT);
		requestParameters.setTemporalResolution(TemporalResolution.CLIMATE_ANOMOLY);
		requestParameters.setPeriod("annual");
		
		this.generateWMS(new LongTermAverageWMSRequestImpl(requestParameters, response.getOutputStream()), response);
		
		return null;
	}

	
	@RequestMapping(value="/{scale}/{variable}/{scenario}/anomaly/seasonal/{season}/{term}")
	public ModelAndView longTermAverageDiagnosticsSeasonal(LongTermAverageParameters requestParameters, HttpServletResponse response) throws InterruptedException, ExecutionException, IOException {
		
		requestParameters.setPeriod("seasonal");
		
		LongTermAverageConversionRequestImpl conversionRequestMessage = new LongTermAverageConversionRequestImpl(requestParameters, null);
		
		// FIXME - Find a better way to deal with this.
		conversionRequestMessage.getParameters().setTemporalResolution(TemporalResolution.CLIMATE_ANOMOLY);
		
		this.debugProcessor.process(conversionRequestMessage);
		
		ModelMap modelMap = new ModelMap("conversionRequest", conversionRequestMessage);
		modelMap.addAttribute("dataFileExists", conversionRequestMessage.getDataFile().exists());
		
		
		return new ModelAndView("validate-annual-mean", modelMap); 
		
	}
	
		
	@RequestMapping(value="/{scale}/{variable}/{scenario}/anomaly/seasonal/{season}/{term}.shp")
	public ModelAndView convertToShapefileSeasonal(LongTermAverageParameters requestParameters, HttpServletResponse response) throws InterruptedException, ExecutionException, IOException {
		
		requestParameters.setOutputType(OutputType.SHAPE);
		requestParameters.setTemporalResolution(TemporalResolution.CLIMATE_ANOMOLY);
		requestParameters.setPeriod("seasonal");
		
		
		this.convert(new LongTermAverageConversionRequestImpl(requestParameters, response.getOutputStream()), response);
		
		return null; 
	}
	
	@RequestMapping(value="/{scale}/{variable}/{scenario}/anomaly/seasonal/{season}/{term}.txt")
	public ModelAndView convertToTextfileSeasonal(LongTermAverageParameters requestParameters, HttpServletResponse response) throws InterruptedException, ExecutionException, IOException {
		
		requestParameters.setOutputType(OutputType.TEXT);
		requestParameters.setTemporalResolution(TemporalResolution.CLIMATE_ANOMOLY);
		requestParameters.setPeriod("seasonal");
		
		this.convert(new LongTermAverageConversionRequestImpl(requestParameters, response.getOutputStream()), response);
		
		return null;
	}
	
	@RequestMapping(value="/{scale}/{variable}/{scenario}/anomaly/seasonal/{season}/{term}.wms")
	public ModelAndView convertToWMSSeasonal(LongTermAverageParameters requestParameters, HttpServletResponse response) throws InterruptedException, ExecutionException, IOException {
		
		requestParameters.setOutputType(OutputType.TEXT);
		requestParameters.setTemporalResolution(TemporalResolution.CLIMATE_ANOMOLY);
		requestParameters.setPeriod("seasonal");
		
		this.generateWMS(new LongTermAverageWMSRequestImpl(requestParameters, response.getOutputStream()), response);
		
		return null;
	}

	@RequestMapping(value="/{scale}/{variable}/{scenario}/anomaly/monthly/{month}/{startYear}/{endYear}")
	public ModelAndView ar5ClimateAnomalyMonthlyDiagnostics(LongTermAverageParameters requestParameters, HttpServletResponse response) throws InterruptedException, ExecutionException, IOException {
		return longTermAverageDiagnostics(requestParameters, response);
	}

	@RequestMapping(value="/{scale}/{variable}/{scenario}/anomaly/monthly/{month}/{startYear}/{endYear}.shp")
	public ModelAndView ar5ClimateAnomalyMonthlyShapefile(LongTermAverageParameters requestParameters, HttpServletResponse response) throws InterruptedException, ExecutionException, IOException {
		return convertToShapefile(requestParameters, response);
	}

	@RequestMapping(value="/{scale}/{variable}/{scenario}/anomaly/monthly/{month}/{startYear}/{endYear}.txt")
	public ModelAndView ar5ClimateAnomalyMonthlyTextfile(LongTermAverageParameters requestParameters, HttpServletResponse response) throws InterruptedException, ExecutionException, IOException {
		return convertToTextfile(requestParameters, response);
	}

	@RequestMapping(value="/{scale}/{variable}/{scenario}/anomaly/annual/{startYear}/{endYear}")
	public ModelAndView ar5ClimateAnomalyAnnualDiagnostics(LongTermAverageParameters requestParameters, HttpServletResponse response) throws InterruptedException, ExecutionException, IOException {
		return longTermAverageDiagnosticsAnnual(requestParameters, response);
	}

	@RequestMapping(value="/{scale}/{variable}/{scenario}/anomaly/annual/{startYear}/{endYear}.shp")
	public ModelAndView ar5ClimateAnomalyAnnualShapefile(LongTermAverageParameters requestParameters, HttpServletResponse response) throws InterruptedException, ExecutionException, IOException {
		return convertToShapefileAnnual(requestParameters, response);
	}

	@RequestMapping(value="/{scale}/{variable}/{scenario}/anomaly/annual/{startYear}/{endYear}.txt")
	public ModelAndView ar5ClimateAnomalyAnnualTextfile(LongTermAverageParameters requestParameters, HttpServletResponse response) throws InterruptedException, ExecutionException, IOException {
		return convertToTextfileAnnual(requestParameters, response);
	}

	public void convert(ConversionRequestMessage conversionRequestMessage, HttpServletResponse response) throws InterruptedException, ExecutionException, IOException {
		
		String outputFilename = this.outputFileNameFactory.create(conversionRequestMessage.getParameters());
		
		response.setHeader("Content-Type", "application/zip");
		response.setHeader("Content-Disposition", "attachment; filename=" + outputFilename + ".zip");
		
//		/MonthlyMeanConversionRequestImpl conversionRequest = new MonthlyMeanConversionRequestImpl(requestParameters, response.getOutputStream(), outputType);
		
		this.shapefileProcessor.process(conversionRequestMessage);
		
	}
	
	public void generateWMS(LongTermAverageWMSRequestImpl wmsRequestMessage, HttpServletResponse response) throws InterruptedException, ExecutionException, IOException {
		
		String outputFilename = this.outputFileNameFactory.create(wmsRequestMessage.getParameters());
		
		response.setHeader("Content-Type", "application/zip");
		response.setHeader("Content-Disposition", "attachment; filename=" + outputFilename + ".zip");
		
//		/MonthlyMeanConversionRequestImpl conversionRequest = new MonthlyMeanConversionRequestImpl(requestParameters, response.getOutputStream(), outputType);
		
		this.wmsProcessor.process(wmsRequestMessage);
		
	}
	
}
