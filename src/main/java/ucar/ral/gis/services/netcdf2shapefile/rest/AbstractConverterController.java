package ucar.ral.gis.services.netcdf2shapefile.rest;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletResponse;

import ucar.ral.gis.services.OutputFileNameFactory;
import ucar.ral.gis.services.messages.ConversionRequestMessage;
import ucar.ral.gis.services.pipeline.Processor;

public class AbstractConverterController {

	protected Processor shapefileProcessor;
	protected OutputFileNameFactory outputFileNameFactory;

	public AbstractConverterController(Processor shapefileProcessor,
			OutputFileNameFactory outputFileNameFactory) {
		super();
		this.shapefileProcessor = shapefileProcessor;
		this.outputFileNameFactory = outputFileNameFactory;
	}

	public void convert(ConversionRequestMessage conversionRequestMessage, HttpServletResponse response)
			throws InterruptedException, ExecutionException, IOException {
					
		String outputFilename = this.outputFileNameFactory.create(conversionRequestMessage.getParameters());
		
		response.setHeader("Content-Type", "application/zip");
		response.setHeader("Content-Disposition", "attachment; filename=" + outputFilename + ".zip");
		
		this.shapefileProcessor.process(conversionRequestMessage);
		
	}

}