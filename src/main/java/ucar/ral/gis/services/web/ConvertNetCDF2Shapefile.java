package ucar.ral.gis.services.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ConvertNetCDF2Shapefile {

	//products/[product <global/downscaled>/<variable>/<model simulation>/<ensemble member>.shp?xmin=1800&xman...&temporal_resolution=monthlymean&month=jan&start_year=1800&end_year=1800
	
	@RequestMapping(value="/products")
	public ModelAndView convert() {
		
		System.out.println("Called the controller! :)");
		
		return null;
	}
}
