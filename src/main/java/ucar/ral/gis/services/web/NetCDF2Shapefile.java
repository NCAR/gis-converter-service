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
	
	
	
	
	// "*/*"
	@RequestMapping(value="/{scale}/{variable}/{scenario}/{ensemble}")
	public ModelAndView convert(@PathVariable(value="scale") Scale scale, 
								@PathVariable(value="variable") String variable,
								@PathVariable(value="scenario") String scenario, 
								@PathVariable(value="ensemble") EnsembleMember ensemble) {
		
		System.out.println("Requested: " + scale + " " + variable  + " " + scenario + " " + ensemble);
		
		ProductRequest productRequest = new ProductRequest(scale, variable, scenario, ensemble);
		
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
