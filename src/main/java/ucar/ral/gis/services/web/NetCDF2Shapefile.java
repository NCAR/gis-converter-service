package ucar.ral.gis.services.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class NetCDF2Shapefile {

	//products/[product <global/downscaled>/<variable>/<model simulation>/<ensemble member>.shp?xmin=1800&xman...&temporal_resolution=monthlymean&month=jan&start_year=1800&end_year=1800
	
	
	// "*/*"
	@RequestMapping(value="/products/{product}/{variable}/{model}/{ensemble}", consumes="*/*")
	public ModelAndView convert(@PathVariable(value="product") String product, @PathVariable(value="variable") String variable,
						@PathVariable(value="model") String modelSim, @PathVariable(value="ensemble") String ensemble) {
		
		System.out.println("Requested: " + product + " " + variable  + " " + modelSim + " " + ensemble);
		
		ModelMap model = new ModelMap();
		
		model.addAttribute("product", product);
		model.addAttribute("variable", variable);
		model.addAttribute("modelSim", modelSim);
		model.addAttribute("ensemble", ensemble);
		
		return new ModelAndView("request", model);
	}
	
	@RequestMapping(value="/products/{product}/{variable}/{model}/{ensemble}", consumes="application/atom+xml")
	public ModelAndView convertAtom(@PathVariable(value="product") String product, @PathVariable(value="variable") String variable,
						@PathVariable(value="model") String modelSim, @PathVariable(value="ensemble") String ensemble) {
		
		System.out.println("Atom Requested: " + product + " " + variable  + " " + modelSim + " " + ensemble);
		
		ModelMap model = new ModelMap();
		
		model.addAttribute("product", product);
		model.addAttribute("variable", variable);
		model.addAttribute("modelSim", modelSim);
		model.addAttribute("ensemble", ensemble);
		
		return new ModelAndView("request", model);
	}
}
