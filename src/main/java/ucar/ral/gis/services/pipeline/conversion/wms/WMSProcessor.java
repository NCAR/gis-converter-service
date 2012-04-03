package ucar.ral.gis.services.pipeline.conversion.wms;

import java.net.URI;

import net.lingala.zip4j.io.ZipOutputStream;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import ucar.ral.gis.services.messages.ConversionOutput;
import ucar.ral.gis.services.messages.ConversionRequestMessage;
import ucar.ral.gis.services.messages.WMSRequestMessage;
import ucar.ral.gis.services.pipeline.Processor;

public class WMSProcessor implements Processor {
	
	private RestTemplate restTemplate;

	public WMSProcessor(RestTemplate restTemplate) {
		super();
		this.restTemplate = restTemplate;
	}

	
	private static final String IMAGE_URL_TEMPLATE = "http://tds.gisclimatechange.ucar.edu/thredds/wms/products/ppt_SRESB1_near_seasonal_down_anomaly.nc?" +
			"service=WMS&version=1.3.0&request=GetMap&Layers=ppt&bbox=-124,24,-66,49&SRS=ESPG:4326&CRS=CRS:84&COLORSCALERANGE=-100,55&width=850&height=500&styles=BOXFILL/rainbow&format=image/png&TIME=2039-02-10T00:00:00.000Z";

	private static final String LEGEND_URL_TEMPLATE = "http://tds.gisclimatechange.ucar.edu/thredds/wms/products/ppt_SRESB1_near_seasonal_down_anomaly.nc?" +
			"service=WMS&version=1.3.0&request=GetLegendGraphic&LAYER=ppt&LAYERS=ppt&COLORSCALERANGE=-100,55&PALETTE=rainbow";
	
	public void process(ConversionRequestMessage conversionRequest) {
	
		//http://localhost:8080/converter-service/global/ppt/SRESB1/longterm/average/monthly/jan/near.wms
		
		WMSRequestMessage message = (WMSRequestMessage) conversionRequest;
				
		try {
			ConversionOutput conversionOutput = conversionRequest.getConversionOutput();
			
			ZipOutputStream outputStream = new ZipOutputStream(conversionOutput.getOutputStream());

			for (String date : message.getDates()) {

				ZipParameters parameters = new ZipParameters();
				
				parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
				parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
				parameters.setSourceExternalStream(true);
				parameters.setFileNameInZip("map-" + date + ".png");
				
				outputStream.putNextEntry(null, parameters);
				
				UriComponents mapUri = UriComponentsBuilder.fromHttpUrl(IMAGE_URL_TEMPLATE).build();
				URI mapRequestUri = mapUri.expand(conversionRequest.getDataFile().getName(), conversionRequest.getParameters().getVariable(), message.getDates().get(0) ).toUri();
				
				Integer bytesCopied = restTemplate.execute(mapRequestUri, HttpMethod.GET, null, new ImageExtractor(outputStream));
				
				System.out.println("Bytes copied: " + bytesCopied);
				
				outputStream.closeEntry();
				
				parameters.setFileNameInZip("legend-" + date + ".png");
				
				outputStream.putNextEntry(null, parameters);
				
				bytesCopied = restTemplate.execute(LEGEND_URL_TEMPLATE, HttpMethod.GET, null, new ImageExtractor(outputStream));
				
				System.out.println("Bytes copied: " + bytesCopied);
				
				outputStream.closeEntry();
			}
			
			//ZipOutputStream now writes zip header information to the zip file
			outputStream.finish();
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
		
	}

}
