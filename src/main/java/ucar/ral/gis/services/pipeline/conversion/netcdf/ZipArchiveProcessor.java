package ucar.ral.gis.services.pipeline.conversion.netcdf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
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
			
			includeMetadata(conversionRequest, zipFile, parameters);
			
			includeProjection(conversionRequest, zipFile, parameters);
			
			// Add folder to the zip file
			includeOutput(conversionOutput, zipFile, parameters);
			
			IOUtils.copy(new FileInputStream(zipFile.getFile()), conversionOutput.getOutputStream());
			

		} catch (ZipException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	private void includeOutput(ConversionOutput conversionOutput, ZipFile zipFile, ZipParameters parameters) {
		try {
			zipFile.addFolder(conversionOutput.getOutputFile().getParent(), parameters);
		} catch (Exception e) {
			throw new RuntimeException("Failed to add output directory: " + this.projectMetadata.toString(), e);
		}
	}

	private void includeProjection(ConversionRequestMessage conversionRequest, ZipFile zipFile, ZipParameters parameters) {
		
		try {
			
			String baseName = FilenameUtils.getBaseName(conversionRequest.getConversionOutput().getOutputFile().getName());
			
			String projectionName = baseName + ".prj";
		
			parameters.setFileNameInZip(projectionName);
			
			File metadataFile = new File(this.projectMetadata + "/gisportalprojection.prj");
			
			if (metadataFile.exists()) {
				zipFile.addFile(metadataFile, parameters);
			}
			
		} catch (Exception e) {
			
			throw new RuntimeException("Failed to add projection directory: " + this.projectMetadata.toString(), e);
		}
	}

	private void includeMetadata(ConversionRequestMessage conversionRequest, ZipFile zipFile, ZipParameters parameters)  {
		
		String xmlFileName = "";
		
		try {
			
			xmlFileName = conversionRequest.getDataFile().toString().replace(".nc", ".xml");
			
			parameters.setFileNameInZip(FilenameUtils.getName(xmlFileName));
			
			File metadataFile = new File(xmlFileName);
			
			if (metadataFile.exists()) {
				zipFile.addFile(new File(xmlFileName), parameters);
			}
			
		} catch (Exception e) {
			
			throw new RuntimeException("Failed to add metadata file: " + xmlFileName, e);
		}
	}

}
