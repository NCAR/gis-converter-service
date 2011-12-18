package ucar.ral.gis.services.web;

import java.io.File;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ucar.ral.gis.services.DataFileFactory;

@Controller
public class NetCDF2Shapefile {
	
	private DataFileFactory dataFileFactory;
	
	
	public NetCDF2Shapefile(DataFileFactory dataFileFactory) {
		super();
		this.dataFileFactory = dataFileFactory;
	}




	//products/[product <global/downscaled>/<variable>/<model simulation>/<ensemble member>.[shp, txt]?xmin=1800&xman...&temporal_resolution=monthlymean&month=jan&start_year=1800&end_year=1800
	
	
	// "*/*"
	@RequestMapping(value="/{product}/{variable}/{model}/{ensemble}")
	public ModelAndView convert(@PathVariable(value="product") String product, @PathVariable(value="variable") String variable,
						@PathVariable(value="model") String modelSim, @PathVariable(value="ensemble") String ensemble) {
		
		System.out.println("Requested: " + product + " " + variable  + " " + modelSim + " " + ensemble);
		
		File dataFile = this.dataFileFactory.findDataFile();
		
		
		ModelMap model = new ModelMap();
		
		model.addAttribute("filePath", dataFile.getAbsolutePath());
		model.addAttribute("fileAvailable", dataFile.exists());
//		model.addAttribute("modelSim", modelSim);
//		model.addAttribute("ensemble", ensemble);
//		
		return new ModelAndView("request", model);
	}
	
	
}
