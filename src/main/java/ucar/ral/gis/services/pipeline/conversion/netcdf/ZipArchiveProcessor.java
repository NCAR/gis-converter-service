package ucar.ral.gis.services.pipeline.conversion.netcdf;

import java.io.File;
import java.io.FileInputStream;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import ucar.ral.gis.services.messages.ConversionOutput;
import ucar.ral.gis.services.messages.ConversionRequestMessage;
import ucar.ral.gis.services.pipeline.Processor;

public class ZipArchiveProcessor implements Processor {
	
	private File projectMetadata;
	
	public ZipArchiveProcessor(File projectMetadata) {
		super();
		this.projectMetadata = projectMetadata;
	}
	
	public void process(ConversionRequestMessage conversionRequest) {
		
		try {
			
			ConversionOutput conversionOutput = conversionRequest.getConversionOutput();
			
			// Initiate ZipFile object with the path/name of the zip file.
			ZipFile zipFile = new ZipFile(conversionOutput.getWorkingDirectory() + "/result.zip");
		
			// Initiate Zip Parameters which define various properties such
			// as compression method, etc.
			ZipParameters parameters = new ZipParameters();
			
			// set compression method to store compression
			parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
			
			// Set the compression level
			parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
			
			String xmlFileName = conversionRequest.getDataFile().toString().replace(".nc", ".xml");
			
			parameters.setFileNameInZip(FilenameUtils.getName(xmlFileName));
			
			zipFile.addFile(new File(xmlFileName), parameters);
			
			parameters.setFileNameInZip("");
			
			parameters.setIncludeRootFolder(false);
			
			zipFile.addFolder(this.projectMetadata, parameters);
			
			// Add folder to the zip file
			zipFile.addFolder(conversionOutput.getOutputFile().getParent(), parameters);
			
			IOUtils.copy(new FileInputStream(zipFile.getFile()), conversionOutput.getOutputStream());
			

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

}
