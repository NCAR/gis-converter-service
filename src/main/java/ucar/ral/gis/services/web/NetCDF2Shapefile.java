package ucar.ral.gis.services.web;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ucar.ral.gis.services.ConversionRequestImpl;
import ucar.ral.gis.services.DataFileFactory;
import ucar.ral.gis.services.OutputFilename;
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
	
	
	
	@RequestMapping(value="/{scale}/{variable}/{scenario}/{ensemble}/{temporalres}.shp")
	public ModelAndView convertToShapefile(RequestParameters requestParameters, HttpServletResponse response) throws InterruptedException, ExecutionException, IOException {
		
		this.convert(requestParameters, response, OutputType.SHAPE);
		
		return null; 
	}
	
	@RequestMapping(value="/{scale}/{variable}/{scenario}/{ensemble}/{temporalres}.txt")
	public ModelAndView convertToTextfile(RequestParameters requestParameters, HttpServletResponse response) throws InterruptedException, ExecutionException, IOException {
		
		this.convert(requestParameters, response, OutputType.TEXT);
		
		return null;
	}
	
	public void convert(RequestParameters requestParameters, HttpServletResponse response, OutputType outputType) throws InterruptedException, ExecutionException, IOException {
		
		OutputFilename outputFilename = new OutputFilename(requestParameters);
		
		response.setHeader("Content-Type", "application/zip");
		response.setHeader("Content-Disposition", "attachment; filename=" + outputFilename.getName() + ".zip");
		
		ConversionRequestImpl conversionRequest = new ConversionRequestImpl(requestParameters, response.getOutputStream(), outputType);
		
		this.shapefileProcessor.process(conversionRequest);
		
	}
	
}
