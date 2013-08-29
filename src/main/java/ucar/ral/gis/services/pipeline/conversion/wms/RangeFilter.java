package ucar.ral.gis.services.pipeline.conversion.wms;

import java.net.URI;

import org.springframework.ui.ModelMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import ucar.ral.gis.services.messages.ConversionRequestMessage;
import ucar.ral.gis.services.messages.WMSRequestMessage;
import ucar.ral.gis.services.pipeline.Processor;

public class RangeFilter implements Processor {
	
	private RestTemplate restTemplate;
	
	public RangeFilter(RestTemplate restTemplate) {
		super();
		this.restTemplate = restTemplate;
	}


	/* Give the min / max values JSON */
	private static final String MINMAX_URL_TEMPLATE = "http://tds.gisclimatechange.ucar.edu/thredds/wms/products/files/{filename}?" +
			"service=WMS&version=1.3.0&item=minmax&request=GetMetadata&Layers={variable}&bbox={xmin},{ymin},{xmax},{ymax}&SRS=EPSG:4326&CRS=CRS:84&width=850&height=500&TIME={date}";
	
	
	public void process(ConversionRequestMessage conversionRequest) {
		
		WMSRequestMessage wmsRequestMessage = (WMSRequestMessage) conversionRequest;
		
		if(null == wmsRequestMessage.getRange()) {
		
			UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(MINMAX_URL_TEMPLATE).build();
			
			ModelMap model = new ModelMap();
			
			model.addAttribute("filename", conversionRequest.getDataFile().getName());
			model.addAttribute("variable", conversionRequest.getParameters().getVariable());
			
			model.addAttribute("xmin", conversionRequest.getParameters().getXmin());
			model.addAttribute("xmax", conversionRequest.getParameters().getXmax());
			model.addAttribute("ymin", conversionRequest.getParameters().getYmin());
			model.addAttribute("ymax", conversionRequest.getParameters().getYmax());
			
			model.addAttribute("date", wmsRequestMessage.getDates().get(0));
			
			URI requestUri = uriComponents.expand(model).toUri();
	
			Range range = restTemplate.getForObject(requestUri, Range.class);
			
			wmsRequestMessage.setRange(range);
		}
	}

}
