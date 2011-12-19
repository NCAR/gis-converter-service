package ucar.ral.gis.services;

import java.io.File;
import java.io.FileFilter;
import java.util.Map;

import org.apache.commons.io.filefilter.WildcardFileFilter;

import ucar.ral.gis.services.web.ProductRequest;

public class DataFileFactory {
	
	private File baseDirectory;
	
	private Map<String, String> scenarioDirectoryMap;
	private Map<String, String> runMap;
	
	public DataFileFactory(File baseDirectory, Map<String, String> scenarioDirectoryMap, Map<String, String> runMap) {
		super();
		
		this.baseDirectory = baseDirectory;
		this.scenarioDirectoryMap = scenarioDirectoryMap;
		this.runMap = runMap;
	}




	/*
	 [wilhelmi@vetswebdev ipcc]$ ls completeEnsembleAverages/*tas*
completeEnsembleAverages/tas_A1.20C3M_EA1-9.CCSM.atmm.1870-01_cat_1999-12.nc
completeEnsembleAverages/tas_A1.Commit_EA1-5.CCSM.atmm.2000-01_cat_2099-12.nc
completeEnsembleAverages/tas_A1.SRESA1B_EA1-8.CCSM.atmm.2000-01_cat_2099-12.nc
completeEnsembleAverages/tas_A1.SRESA2_EA1-5.CCSM.atmm.2000-01_cat_2099-12.nc
completeEnsembleAverages/tas_A1.SRESB1_EA1-8.CCSM.atmm.2000-01_cat_2099-12.nc
completeEnsembleAverages/tasmax_A1.20C3M_EA1-9.CCSM.atmm.1870-01_cat_1999-12.nc
completeEnsembleAverages/tasmax_A1.Commit_EA1-5.CCSM.atmm.2000-01_cat_2099-12.nc
completeEnsembleAverages/tasmax_A1.SRESA1B_EA1-8.CCSM.atmm.2000-01_cat_2099-12.nc
completeEnsembleAverages/tasmax_A1.SRESA2_EA1-5.CCSM.atmm.2000-01_cat_2099-12.nc
completeEnsembleAverages/tasmax_A1.SRESB1_EA1-8.CCSM.atmm.2000-01_cat_2099-12.nc
completeEnsembleAverages/tasmin_A1.20C3M_EA1-9.CCSM.atmm.1870-01_cat_1999-12.nc
completeEnsembleAverages/tasmin_A1.Commit_EA1-5.CCSM.atmm.2000-01_cat_2099-12.nc
completeEnsembleAverages/tasmin_A1.SRESA1B_EA1-8.CCSM.atmm.2000-01_cat_2099-12.nc
completeEnsembleAverages/tasmin_A1.SRESA2_EA1-5.CCSM.atmm.2000-01_cat_2099-12.nc
completeEnsembleAverages/tasmin_A1.SRESB1_EA1-8.CCSM.atmm.2000-01_cat_2099-12.nc
[wilhelmi@vetswebdev ipcc]$ ls 20C3M/*tas*
ls: 20C3M/*tas*: No such file or directory
[wilhelmi@vetswebdev ipcc]$ ls 20C3M/A1/*tas*
ls: 20C3M/A1/*tas*: No such file or directory
[wilhelmi@vetswebdev ipcc]$ ls 20C3M/A1/run
run1/ run2/ run3/ run4/ run5/ run6/ run7/ run8/ run9/ 
[wilhelmi@vetswebdev ipcc]$ ls 20C3M/A1/run1/tas*
20C3M/A1/run1/tas_A1.20C3M_1.CCSM.atmm.1870-01_cat_1999-12.nc
20C3M/A1/run1/tasmax_A1.20C3M_1.CCSM.atmm.1870-01_cat_1999-12.nc
20C3M/A1/run1/tasmin_A1.20C3M_1.CCSM.atmm.1870-01_cat_1999-12.nc

	 */
	//products/[product <global/downscaled>/<variable>/<model simulation>/<ensemble member>.[shp, txt]?xmin=1800&xman...&temporal_resolution=monthlymean&month=jan&start_year=1800&end_year=1800
	
	
	
	
	
	/*
	 * If EA: path/<variable>_A1.<scenario>_EA*.nc
	 * 
	 * If Run: 
	 */
	
	public File findDataFile(ProductRequest productRequest) {
		
		//http://commons.apache.org/io/api-release/org/apache/commons/io/filefilter/WildcardFileFilter.html
		
		String productDirectory;
		
		if(Scale.DOWNSCALED == productRequest.getProduct()) {
			productDirectory = "completeDownscaled";
		} 
		else {
			// Pull it from the scenario map
			String scenarioDirectory = this.scenarioDirectoryMap.get(productRequest.getModelSim());
			productDirectory = scenarioDirectory;
		}
		
		
		String runMember = this.runMap.get(productRequest.getEnsemble());
		
		System.out.println("Ensemble: " + productRequest.getEnsemble() + " mapped to: " + runMember);
		
		if(productRequest.getEnsemble().equalsIgnoreCase("average")) {
			// Do nothing for EA
		}
		else {
			productDirectory += "/A1/" + runMember;
		}
		
		File result = new File(this.baseDirectory, productDirectory);
		
		//tasmin_A1.20C3M_1.CCSM.atmm.1870-01_cat_1999-12.nc
		String fileNamePattern = "%s_A1.%s_*.nc";
		
		String wildCardPattern = fileNamePattern.format(fileNamePattern, productRequest.getVariable(), runMember);
		
		System.out.println("Searching directory: " + result.getAbsolutePath() + " using: " + wildCardPattern );
		
		//File dir = new File(".");
		 FileFilter fileFilter = new WildcardFileFilter(wildCardPattern);
		 File[] files = result.listFiles(fileFilter);
		 for (int i = 0; i < files.length; i++) {
		   System.out.println(files[i]);
		 }
		
		
		return files[0];
	}

}
