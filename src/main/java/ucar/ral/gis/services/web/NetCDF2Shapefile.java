package ucar.ral.gis.services.web;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ucar.nc2.util.IO.HttpResult;
import ucar.ral.gis.services.ConversionRequestImpl;
import ucar.ral.gis.services.DataFileFactory;
import ucar.ral.gis.services.OutputType;
import ucar.ral.gis.services.pipeline.Processor;

@Controller
public class NetCDF2Shapefile {
	
	private Processor shapefileProcessor;
	
	private DataFileFactory dataFileFactory;
	
	
	public NetCDF2Shapefile(DataFileFactory dataFileFactory, Processor shapefileProcessor) {
		super();
		this.dataFileFactory = dataFileFactory;
		this.shapefileProcessor = shapefileProcessor;
	}

	//products/[product <global/downscaled>/<variable>/<scenario>/<run>.[shp, txt]?xmin=1800&xman...&temporal_resolution=monthlymean&month=jan&start_year=1800&end_year=1800
	
	/**
	 * 	5. Select region of interest:
		Selection:
			<XMin>							xmin=<XMin>
			<XMax>							xmax=<XMax>
			<YMin>							ymin=<YMin>
			<YMax>							Ymax=<YMax>

	6. Select Temporal Resolution: temporalres
		Selection:
			Monthly Mean						temporal_resolution=monthly
			Annual Mean						"annual_sum" (for ppt)
										"annual_avg" (for tas)
			Long Term Average					""
			Climate Anomaly						"anomaly"


	7. Select time period:
		Selection:
			<month>							month=<month>
										month=allmonths
			<Start Year>						start_year=<Start Year>
			<End Year>						end_year=<End Year>


			<month>							"monthly"
			<annual>						"annual"
			<season>						"seasonal"

			Near Term (2030)					"near"
			Mid-Century (2050)					"mid"
			End of Century (2080)					"end"
			Last Decade (2099)					"LastDecade"	 */
	
	
	
	@RequestMapping(value="/{scale}/{variable}/{scenario}/{ensemble}")
	public ModelAndView convert(RequestParameters productRequest) {
		
		//System.out.println("Requested: " + scale + " " + variable  + " " + scenario + " " + ensemble);
		
		//ProductRequest productRequest = new ProductRequest(scale, variable, scenario, ensemble);
		
		File dataFile = this.dataFileFactory.findDataFile(productRequest);
		
		
		ModelMap model = new ModelMap();
		
		model.addAttribute("filePath", dataFile.getAbsolutePath());
		model.addAttribute("fileAvailable", dataFile.exists());
//		model.addAttribute("modelSim", modelSim);
//		model.addAttribute("ensemble", ensemble);
//		
		return new ModelAndView("request", model);
	}
	
	@RequestMapping(value="/{scale}/{variable}/{scenario}/{ensemble}/{temporalres}.shp")
	public ModelAndView convertToShapefile(RequestParameters requestParameters, HttpServletResponse response) throws InterruptedException, ExecutionException, IOException {
		
		
		response.setHeader("Content-Type", "application/zip");
		
		ConversionRequestImpl conversionRequest = new ConversionRequestImpl(requestParameters, response.getOutputStream(), OutputType.SHAPE);
		
		this.shapefileProcessor.process(conversionRequest);
		
		return null; 
	}
	
	@RequestMapping(value="/{scale}/{variable}/{scenario}/{ensemble}/{temporalres}.txt")
	public ModelAndView convertToTextfile(RequestParameters requestParameters, HttpServletResponse response) throws InterruptedException, ExecutionException, IOException {
		
		
		response.setHeader("Content-Type", "application/zip");
		
		ConversionRequestImpl conversionRequest = new ConversionRequestImpl(requestParameters, response.getOutputStream(), OutputType.TEXT);
		
		this.shapefileProcessor.process(conversionRequest);
		
		return null; 
	}
	
	
}
