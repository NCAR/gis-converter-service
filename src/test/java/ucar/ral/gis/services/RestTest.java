package ucar.ral.gis.services;

import java.util.ArrayList;
import java.util.List;

import net.opengis.wms.v_1_3_0.WMSCapabilities;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.CommonsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.client.RestTemplate;

@Ignore
public class RestTest {
	
	/* Give the min / max values JSON */
	private static final String MINMAX_URL_TEMPLATE = "http://tds.gisclimatechange.ucar.edu/thredds/wms/products/ppt_SRESB1_near_seasonal_down_anomaly.nc?" +
			"service=WMS&version=1.3.0&item=minmax&request=GetMetadata&Layers=ppt&bbox=-124,24,66,49&SRS=ESPG:4326&CRS=CRS:84&width=850&height=500&TIME=2039-02-10T00:00:00.000Z";
	
	private static final String CAPABILITY_URL_TEMPLATE = "http://tds.gisclimatechange.ucar.edu/thredds/wms/products/ppt_SRESB1_near_seasonal_down_anomaly.nc?" +
			"service=WMS&version=1.3.0&request=GetCapabilities&Layers=ppt&bbox=-124,24,-66,49&SRS=ESPG:4326&CRS=CRS:84&COLORSCALERANGE=-100,55&width=850&height=500&styles=BOXFILL/rainbow&format=image/png&TIME=2039-02-10T00:00:00.000Z";

	
	private static final String IMAGE_URL_TEMPLATE = "http://tds.gisclimatechange.ucar.edu/thredds/wms/products/ppt_SRESB1_near_seasonal_down_anomaly.nc?" +
			"service=WMS&version=1.3.0&request=GetMap&Layers=ppt&bbox=-124,24,-66,49&SRS=ESPG:4326&CRS=CRS:84&COLORSCALERANGE=-100,55&width=850&height=500&styles=BOXFILL/rainbow&format=image/png&TIME=2039-02-10T00:00:00.000Z";

	private static final String LEGEND_URL_TEMPLATE = "http://tds.gisclimatechange.ucar.edu/thredds/wms/products/ppt_SRESB1_near_seasonal_down_anomaly.nc?" +
			"service=WMS&version=1.3.0&request=GetLegendGraphic&LAYER=ppt&LAYERS=ppt&COLORSCALERANGE=-100,55&PALETTE=rainbow";


	@Test
	public void test() {
			
		Logger log = LoggerFactory.getLogger("log4j.logger.httpclient.wire");
		
		log.debug("testing");
		
		// TODO Auto-generated method stub
		
		RestTemplate restTemplate = new RestTemplate();
		
		Jaxb2Marshaller unmarshaller = new Jaxb2Marshaller();
		
		unmarshaller.setClassesToBeBound(WMSCapabilities.class);
		
		MarshallingHttpMessageConverter jaxbConverter = new MarshallingHttpMessageConverter();
		jaxbConverter.setUnmarshaller(unmarshaller);
    	
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
//			messageConverters.add(new FormHttpMessageConverter());
		messageConverters.add(jaxbConverter);
		messageConverters.add(new MappingJacksonHttpMessageConverter());
		restTemplate.setMessageConverters(messageConverters);
				
		restTemplate.setRequestFactory(new CommonsClientHttpRequestFactory());
		
//			Range range = restTemplate.getForObject(MINMAX_URL_TEMPLATE, Range.class);
//
//			System.out.println("Got the request for a WMS conversion, range: " + range.getMin() + " " + range.getMax());
		
		WMSCapabilities wmsRequest = restTemplate.getForObject(CAPABILITY_URL_TEMPLATE, WMSCapabilities.class);
		
		
		// Wow, just wow.
		System.out.println("Capabilities, times: " + wmsRequest.getCapability().getLayer().getLayer().get(0).getLayer().get(0).getDimension().get(0).getValue());
	
	}

}
