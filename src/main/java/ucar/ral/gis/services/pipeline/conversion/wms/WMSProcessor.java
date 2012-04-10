package ucar.ral.gis.services.pipeline.conversion.wms;

import java.net.URI;

import net.lingala.zip4j.io.ZipOutputStream;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import org.springframework.http.HttpMethod;
import org.springframework.ui.ModelMap;
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

	
	private static final String IMAGE_URL_TEMPLATE = "http://tds.gisclimatechange.ucar.edu/thredds/wms/products/{filename}?" +
			"service=WMS&version=1.3.0&request=GetMap&Layers={variable}&bbox={xmin},{ymin},{xmax},{ymax}&SRS=ESPG:4326&CRS=CRS:84&COLORSCALERANGE={color-min},{color-max}&width=850&height=500&styles=BOXFILL/rainbow&format=image/png&TIME={date}";

	private static final String LEGEND_URL_TEMPLATE = "http://tds.gisclimatechange.ucar.edu/thredds/wms/products/{filename}?" +
			"service=WMS&version=1.3.0&request=GetLegendGraphic&Layer={variable}&Layers={variable}&bbox={xmin},{ymin},{xmax},{ymax}&COLORSCALERANGE={color-min},{color-max}&PALETTE=rainbow";
	
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
				parameters.setFileNameInZip("map" +  ".png");
				
				outputStream.putNextEntry(null, parameters);
				
				ModelMap model = new ModelMap();
				
				model.addAttribute("filename", conversionRequest.getDataFile().getName());
				model.addAttribute("variable", conversionRequest.getParameters().getVariable());
				
				model.addAttribute("xmin", conversionRequest.getParameters().getXmin());
				model.addAttribute("xmax", conversionRequest.getParameters().getXmax());
				model.addAttribute("ymin", conversionRequest.getParameters().getYmin());
				model.addAttribute("ymax", conversionRequest.getParameters().getYmax());
				
				model.addAttribute("color-min", String.format("%.6g", message.getRange().getMin()));
				model.addAttribute("color-max", String.format("%.6g", message.getRange().getMax()));
				
				model.addAttribute("date", date);
				
				UriComponents mapUri = UriComponentsBuilder.fromHttpUrl(IMAGE_URL_TEMPLATE).build();
				URI mapRequestUri = mapUri.expand(model).toUri();
				
				Integer bytesCopied = restTemplate.execute(mapRequestUri, HttpMethod.GET, null, new ImageExtractor(outputStream));
				
				System.out.println("Bytes copied: " + bytesCopied);
				
				outputStream.closeEntry();
				
				parameters.setFileNameInZip("legend" + ".png");
				
				outputStream.putNextEntry(null, parameters);
				
				UriComponents legendUri = UriComponentsBuilder.fromHttpUrl(LEGEND_URL_TEMPLATE).build();
				URI legendRequestUri = legendUri.expand(model).toUri();
				
				bytesCopied = restTemplate.execute(legendRequestUri, HttpMethod.GET, null, new ImageExtractor(outputStream));
				
				System.out.println("Bytes copied: " + bytesCopied);
				
				outputStream.closeEntry();
			}
			
			//ZipOutputStream now writes zip header information to the zip file
			outputStream.finish();
			outputStream.close();
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
		
	}

}
