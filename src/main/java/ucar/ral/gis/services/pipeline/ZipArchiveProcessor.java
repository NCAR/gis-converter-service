package ucar.ral.gis.services.pipeline;

import java.io.FileInputStream;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import org.apache.commons.io.IOUtils;

import ucar.ral.gis.services.ConversionRequestImpl;

public class ZipArchiveProcessor implements Processor {
	
	public ZipArchiveProcessor() {
		super();
	}

	public void process(ConversionRequestImpl conversionRequest) {
		
		try {
			// Initiate ZipFile object with the path/name of the zip file.
			ZipFile zipFile = new ZipFile(conversionRequest.getWorkDirectory() + "/result.zip");
		
			// Initiate Zip Parameters which define various properties such
			// as compression method, etc.
			ZipParameters parameters = new ZipParameters();
			
			// set compression method to store compression
			parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
			
			// Set the compression level
			parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
			
			// Add folder to the zip file
			zipFile.addFolder(conversionRequest.getOutputFile().getParent(), parameters);
			
			
			IOUtils.copy(new FileInputStream(zipFile.getFile()), conversionRequest.getOutputStream());
			

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

}
