package ucar.ral.gis.services.pipeline.conversion.wms;

import java.net.URI;

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
	private static final String MINMAX_URL_TEMPLATE = "http://tds.gisclimatechange.ucar.edu/thredds/wms/products/{filename}?" +
			"service=WMS&version=1.3.0&item=minmax&request=GetMetadata&Layers={variable}&bbox=-124,24,66,49&SRS=ESPG:4326&CRS=CRS:84&width=850&height=500&TIME=2039-02-10T00:00:00.000Z";
	
	
	public void process(ConversionRequestMessage conversionRequest) {
		
		UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(MINMAX_URL_TEMPLATE).build();
		
		URI requestUri = uriComponents.expand(conversionRequest.getDataFile().getName(), conversionRequest.getParameters().getVariable()).toUri();
	
		WMSRequestMessage wmsRequestMessage = (WMSRequestMessage) conversionRequest;
		
		Range range = restTemplate.getForObject(requestUri, Range.class);
		
		System.out.println("Range: " + range.getMin() + ", " + range.getMax());

		wmsRequestMessage.setRange(range);
	}

}
