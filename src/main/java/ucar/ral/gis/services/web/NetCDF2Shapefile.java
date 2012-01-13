package ucar.ral.gis.services.web;

import java.io.File;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
	
	@RequestMapping(value="/{scale}/{variable}/{scenario}/{ensemble}/{temporalResolution}.shp")
	public ModelAndView convertToShapefile(ProductRequest productRequest) {
		
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
	
	
}
