package ucar.ral.gis.services.netcdf2shapefile.rest.monthly;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ucar.ral.gis.services.MonthlyMeanConversionRequestImpl;
import ucar.ral.gis.services.OutputFileNameFactory;
import ucar.ral.gis.services.OutputType;
import ucar.ral.gis.services.netcdf2shapefile.rest.AbstractConverterController;
import ucar.ral.gis.services.pipeline.Processor;

@Controller
public class MonthlyMeanController extends AbstractConverterController {
	
	private Processor debugProcessor;
	
	public MonthlyMeanController(OutputFileNameFactory outputFileNameFactory, Processor shapefileProcessor, Processor debugProcessor) {
		super(shapefileProcessor, outputFileNameFactory);
		
		this.outputFileNameFactory = outputFileNameFactory;
		this.shapefileProcessor = shapefileProcessor;
		this.debugProcessor = debugProcessor;
	}

	@RequestMapping(value="/{scale}/{variable}/{scenario}/monthly/mean/{ensemble}/{month}/{startYear}/{endYear}")
	public ModelAndView monthlyMeanToShapefileDiagnostics(MonthlyMeanParameters requestParameters, HttpServletResponse response) throws InterruptedException, ExecutionException, IOException {
		
		MonthlyMeanConversionRequestImpl conversionRequestMessage = new MonthlyMeanConversionRequestImpl(requestParameters, null);
		
		this.debugProcessor.process(conversionRequestMessage);
		
		ModelMap modelMap = new ModelMap("conversionRequest", conversionRequestMessage);
		modelMap.addAttribute("dataFileExists", conversionRequestMessage.getDataFile().exists());
		
		
		return new ModelAndView("validate-monthly-mean", modelMap); 
	}
	
	@RequestMapping(value="/{scale}/{variable}/{scenario}/monthly/mean/{ensemble}/{month}/{startYear}/{endYear}.shp")
	public ModelAndView convertMonthlyMeanToShapefile(MonthlyMeanParameters requestParameters, HttpServletResponse response) throws InterruptedException, ExecutionException, IOException {
		
		requestParameters.setOutputType(OutputType.SHAPE);
		
		this.convert(new MonthlyMeanConversionRequestImpl(requestParameters, response.getOutputStream()), response);
		
		return null; 
	}
	
	@RequestMapping(value="/{scale}/{variable}/{scenario}/monthly/mean/{ensemble}/{month}/{startYear}/{endYear}.txt")
	public ModelAndView convertMonthlyMeanToTextfile(MonthlyMeanParameters requestParameters, HttpServletResponse response) throws InterruptedException, ExecutionException, IOException {
		
		requestParameters.setOutputType(OutputType.TEXT);
		
		this.convert(new MonthlyMeanConversionRequestImpl(requestParameters, response.getOutputStream()), response);
		
		return null;
	}
	
}
