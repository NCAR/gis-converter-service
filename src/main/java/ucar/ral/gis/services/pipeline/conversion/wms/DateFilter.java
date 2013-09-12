package ucar.ral.gis.services.pipeline.conversion.wms;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import net.opengis.wms.v_1_3_0.WMSCapabilities;

import org.joda.time.DateTime;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import ucar.ral.gis.services.messages.ConversionRequestMessage;
import ucar.ral.gis.services.messages.WMSRequestMessage;
import ucar.ral.gis.services.pipeline.Processor;
import edu.ucar.gis.ipcc.TimeConstraint;

public class DateFilter implements Processor {
	
	private RestTemplate restTemplate;
	
	public DateFilter(RestTemplate restTemplate) {
		super();
		this.restTemplate = restTemplate;
	}

	private static final String CAPABILITY_URL_TEMPLATE = "http://tds.gisclimatechange.ucar.edu/thredds/wms/products/files/{filename}?service=WMS&version=1.3.0&request=GetCapabilities";

	
	public void process(ConversionRequestMessage conversionRequest) {
		
		UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(CAPABILITY_URL_TEMPLATE).build();
		
		URI requestUri = uriComponents.expand(conversionRequest.getDataFile().getName()).toUri();
		
		
		WMSCapabilities wmsCapabilities = restTemplate.getForObject(requestUri, WMSCapabilities.class);
		
		String timeDimension = wmsCapabilities.getCapability().getLayer().getLayer().get(0).getLayer().get(0).getDimension().get(0).getValue();
		
		String[] dateValues = timeDimension.trim().split(",");
		
		TimeConstraint timeConstraint = conversionRequest.getTimeConstraint();
		
		List<String> dates = new ArrayList<String>();
		
		for (String dateValue : dateValues) {
			
			// Convert to DateTimeObjects -> 2039-02-10T00:00:00.000Z
			
			DateTime modelTime = DateTime.parse(dateValue);
			
			if(timeConstraint.includes(modelTime)) {
				dates.add(dateValue);
			}
			
//			String datePart = dateValue.substring(0, dateValue.indexOf("T"));
//			datePart = datePart.replace("-", "/");
//			                                                  
//			DateTime modelTime = null;
//			try {
//				ClimateModelCalendar climateModelCalendar = new ClimateModelCalendar("no_leap");
//				
//				modelTime = climateModelCalendar.getValidDateTime(datePart);
//				
//				modelTime.set(DateTime.DAY, modelTime.get(DateTime.DAY) + 1);
//				modelTime.set(DateTime.MONTH, modelTime.get(DateTime.MONTH) + 0);
//				
//				if(timeConstraint.includes(modelTime)) {
//					dates.add(dateValue);
//				}
//				
//			} catch (Exception e) {
//				throw new RuntimeException(e);
//			}
	
		}
		
		if(1 < dates.size()) {
			throw new RuntimeException("Found more than 1 date for the request!");
		}
		
		((WMSRequestMessage) conversionRequest).setDates(dates);
		
	}

}
