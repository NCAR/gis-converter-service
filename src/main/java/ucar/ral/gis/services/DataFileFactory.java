package ucar.ral.gis.services;

import java.io.File;
import java.io.FileFilter;
import java.util.Map;

import org.apache.commons.io.filefilter.WildcardFileFilter;

import ucar.ral.gis.services.web.RequestParameters;

public class DataFileFactory {
	
	private File baseDirectory;
	
	private Map<String, String> scenarioDirectoryMap;
	
	public DataFileFactory(File baseDirectory, Map<String, String> scenarioDirectoryMap) {
		super();
		
		this.baseDirectory = baseDirectory;
		this.scenarioDirectoryMap = scenarioDirectoryMap;
	}
	
	public File findDataFile(RequestParameters productRequest) {
		
		if(productRequest.getTemporalres() == TemporalResolution.MONTHLY_MEAN) {
			return this.findMonthlyDataFile(productRequest);
		}
		else {
			return this.findProductDataFile(productRequest);
		}
	}
	
	
	public File findProductDataFile(RequestParameters productRequest) {
		
		
		// FIXME - Inject this!
		String productDirectory = "Products";
		
		File searchDirectory = new File(this.baseDirectory, productDirectory);
		
		// AnnualMean			-> [variable]_[ensemble]_annual_avg[ | _downscaled].nc
		// Long Term Average 	-> [variable]_[ensemble]_[projection(4)]_[time(monthly|seasonal|annual)].nc
		// Climate Anomoly		-> [variable]_[ensemble]_[projection(4)]_[time(monthly|seasonal|annual)]_anomoly.nc
		
		//tasmin_A1.20C3M_1.CCSM.atmm.1870-01_cat_1999-12.nc
		String fileNamePattern = productRequest.getVariable() + "_";
		
		fileNamePattern += productRequest.getEnsemble().getName() + "_";
		
		if(productRequest.getTemporalres() == TemporalResolution.ANNUAL_MEAN) {
			fileNamePattern += productRequest.getEnsemble().getName() + "annual_avg_";
		} 
		else if(productRequest.getTemporalres() == TemporalResolution.LONGTERM_AVERAGE) {
			fileNamePattern += productRequest.getEnsemble().getName() + "annual_avg_";
		}
		
		String wildCardPattern = fileNamePattern.format(fileNamePattern, productRequest.getVariable(), this.scenarioDirectoryMap.get(productRequest.getScale()));
		
		File files = findFile(searchDirectory, wildCardPattern);
		
		
		return files;
	}
	
	public File findMonthlyDataFile(RequestParameters productRequest) {
		
		//http://commons.apache.org/io/api-release/org/apache/commons/io/filefilter/WildcardFileFilter.html
		
		String productDirectory;
		
		if(Resolution.DOWNSCALED == productRequest.getScale()) {
			productDirectory = "completeDownscaled";
		} 
		else {
			// Pull it from the scenario map
			String scenarioDirectory = this.scenarioDirectoryMap.get(productRequest.getScenario());
			productDirectory = scenarioDirectory;
			
			if(productRequest.getEnsemble() instanceof RunMember) {
				productDirectory += "/A1/" + productRequest.getEnsemble().getName();
			}
		}
		
		
		
		File result = new File(this.baseDirectory, productDirectory);
		
		//tasmin_A1.20C3M_1.CCSM.atmm.1870-01_cat_1999-12.nc
		String fileNamePattern = "%s_A1.%s_*.nc";
		
		String wildCardPattern = fileNamePattern.format(fileNamePattern, productRequest.getVariable(), this.scenarioDirectoryMap.get(productRequest.getScenario()));
		
		File files = findFile(result, wildCardPattern);
		
		
		return files;
	}

	protected File findFile(File directory, String wildCardPattern) {
		
		System.out.println("Searching directory: " + directory.getAbsolutePath() + " using: " + wildCardPattern );
		
		//File dir = new File(".");
		 FileFilter fileFilter = new WildcardFileFilter(wildCardPattern);
		 File[] files = directory.listFiles(fileFilter);
		 for (int i = 0; i < files.length; i++) {
		   System.out.println(files[i]);
		 }
		 
		 
		if (0 == files.length) {
			throw new RuntimeException("Found 0 files using wildcard: " + wildCardPattern);
		}
		return files[0];
	}

}
