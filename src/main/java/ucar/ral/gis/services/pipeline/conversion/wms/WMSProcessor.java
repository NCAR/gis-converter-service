package ucar.ral.gis.services.pipeline.conversion.wms;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.opengis.wms.v_1_3_0.WMSCapabilities;

import org.springframework.web.client.RestTemplate;

import ucar.ral.gis.services.messages.ConversionRequestMessage;
import ucar.ral.gis.services.pipeline.Processor;

public class WMSProcessor implements Processor {
	
	private RestTemplate restTemplate;

	public WMSProcessor(RestTemplate restTemplate) {
		super();
		this.restTemplate = restTemplate;
	}

	private static final String CAPABILITY_URL_TEMPLATE = "http://tds.gisclimatechange.ucar.edu/thredds/wms/products/ppt_SRESB1_near_seasonal_down_anomaly.nc?" +
			"service=WMS&version=1.3.0&request=GetCapabilities&Layers=ppt&bbox=-124,24,-66,49&SRS=ESPG:4326&CRS=CRS:84&COLORSCALERANGE=-100,55&width=850&height=500&styles=BOXFILL/rainbow&format=image/png&TIME=2039-02-10T00:00:00.000Z";

	
	private static final String IMAGE_URL_TEMPLATE = "http://tds.gisclimatechange.ucar.edu/thredds/wms/products/ppt_SRESB1_near_seasonal_down_anomaly.nc?" +
			"service=WMS&version=1.3.0&request=GetMap&Layers=ppt&bbox=-124,24,-66,49&SRS=ESPG:4326&CRS=CRS:84&COLORSCALERANGE=-100,55&width=850&height=500&styles=BOXFILL/rainbow&format=image/png&TIME=2039-02-10T00:00:00.000Z";

	private static final String LEGEND_URL_TEMPLATE = "http://tds.gisclimatechange.ucar.edu/thredds/wms/products/ppt_SRESB1_near_seasonal_down_anomaly.nc?" +
			"service=WMS&version=1.3.0&request=GetLegendGraphic&LAYER=ppt&LAYERS=ppt&COLORSCALERANGE=-100,55&PALETTE=rainbow";
	
	public void process(ConversionRequestMessage conversionRequest) {
	
		
		WMSCapabilities wmsCapabilities = restTemplate.getForObject(CAPABILITY_URL_TEMPLATE, WMSCapabilities.class);
		
		String timeDimension = wmsCapabilities.getCapability().getLayer().getLayer().get(0).getLayer().get(0).getDimension().get(0).getValue();
		
		String[] dateValues = timeDimension.trim().split(",");
		
		for (String dateValue : dateValues) {
			
			// Convert to DateTimeObjects -> 2039-02-10T00:00:00.000Z
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
			                                                  
	        Date date = null;
			try {
				date = formatter.parse(dateValue);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.println("Date: " + date.getMonth() + " " + date.getYear());
			
		}
		
		
		System.out.println("Capabilities, times: " + timeDimension);
	}

}
