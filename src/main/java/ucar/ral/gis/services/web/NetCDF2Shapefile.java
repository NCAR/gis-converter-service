package ucar.ral.gis.services.web;

import java.io.File;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import edu.ucar.gis.ipcc.model.netcdf2gis.CCSM2ShapeFileConverter;
import edu.ucar.gis.ipcc.model.netcdf2gis.ExtractionResults;

import ucar.ral.gis.services.ConversionRequestImpl;
import ucar.ral.gis.services.DataFileFactory;
import ucar.ral.gis.services.EnsembleMember;
import ucar.ral.gis.services.Scale;

@Controller
public class NetCDF2Shapefile {
	
	private DataFileFactory dataFileFactory;
	
	
	public NetCDF2Shapefile(DataFileFactory dataFileFactory) {
		super();
		this.dataFileFactory = dataFileFactory;
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
	public ModelAndView convert(ProductRequest productRequest) {
		
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
	public ModelAndView convertToShapefile(ProductRequest productRequest) throws InterruptedException, ExecutionException {
		
		//tas_A1.20C3M_1.CCSM.atmm.1870-01_cat_1999-12.nc
		
		File dataFile = this.dataFileFactory.findDataFile(productRequest);
		
		ConversionRequestImpl conversionRequest = new ConversionRequestImpl(productRequest, dataFile, new File("/home/wilhelmi/test/test.shp"));
		
		File outputFile = new File(conversionRequest.getOutputFileName());
    	
		CCSM2ShapeFileConverter converter = new CCSM2ShapeFileConverter(conversionRequest, outputFile);
		
		ExtractionResults results = converter.execute();
		
		//month=04&startyear=1870&endyear=1870&xmin=-180.00&xmax=180.00&ymin=-90.00&ymax=90.00
		
		//System.out.println("Requested: " + scale + " " + variable  + " " + scenario + " " + ensemble);
		
		//ProductRequest productRequest = new ProductRequest(scale, variable, scenario, ensemble);
		
		//File dataFile = this.dataFileFactory.findDataFile(productRequest);
		
		
		ModelMap model = new ModelMap();
		
		model.addAttribute("filePath", dataFile.getAbsolutePath());
		model.addAttribute("fileAvailable", dataFile.exists());
//		model.addAttribute("modelSim", modelSim);
//		model.addAttribute("ensemble", ensemble);
//		
		return new ModelAndView("request", model);
	}
	
	
}
