package ucar.ral.gis.services.pipeline.conversion.wms;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseExtractor;

public class ImageExtractor implements ResponseExtractor<Integer> {
	
	private OutputStream destination;
		
	public ImageExtractor(OutputStream destination) {
		super();
		this.destination = destination;
	}


	public Integer extractData(ClientHttpResponse response) throws IOException {
		
		return IOUtils.copy(response.getBody(), this.destination);
	}

}
