package ucar.ral.gis.services;

import java.io.File;
import java.io.FileFilter;
import java.util.Map;

import org.apache.commons.io.filefilter.WildcardFileFilter;

import ucar.ral.gis.services.web.ProductRequest;

public class DataFileFactory {
	
	private File baseDirectory;
	
	private Map<String, String> scenarioDirectoryMap;
	
	public DataFileFactory(File baseDirectory, Map<String, String> scenarioDirectoryMap) {
		super();
		
		this.baseDirectory = baseDirectory;
		this.scenarioDirectoryMap = scenarioDirectoryMap;
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
	
	
	public File findDataFile(ProductRequest productRequest) {
		
		if(productRequest.getTemporalres() == TemporalResolution.MONTHLY_MEAN) {
			return this.findMonthlyDataFile(productRequest);
		}
		else {
			return this.findProductDataFile(productRequest);
		}
	}
	
	
	
	
	public File findProductDataFile(ProductRequest productRequest) {
		
		
		// FIXME - Inject this!
		String productDirectory = "Products";
		
		File searchDirectory = new File(this.baseDirectory, productDirectory);
		
		/**
		 *                                      0:31 tas_SRESB1_annual_avg_downscaled.nc
-rw-r--r-- 1 wilhelmi vetsdev  13126508 Oct 20 17:25 tas_SRESB1_annual_avg.nc
-rw-r--r-- 1 wilhelmi vetsdev    149040 Jan 28  2011 tas_SRESB1_end_annual_anomaly.nc
-rw-r--r-- 1 wilhelmi vetsdev   3550844 Jan 28  2011 tas_SRESB1_end_annual_down_anomaly.nc
-rw-r--r-- 1 wilhelmi vetsdev   3550364 Sep 22 17:26 tas_SRESB1_end_annual_down.nc
-rw-r--r-- 1 wilhelmi vetsdev    148928 Sep 22 17:26 tas_SRESB1_end_annual.nc
-rw-r--r-- 1 wilhelmi vetsdev   1590976 Jan 28  2011 tas_SRESB1_end_monthly_anomaly.nc
-rw-r--r-- 1 wilhelmi vetsdev  41941200 Jan 28  2011 tas_SRESB1_end_monthly_down_anomaly.nc
-rw-r--r-- 1 wilhelmi vetsdev  41940724 Sep 22 17:26 tas_SRESB1_end_monthly_down.nc
-rw-r--r-- 1 wilhelmi vetsdev   1590872 Sep 22 17:26 tas_SRESB1_end_monthly.nc
-rw-r--r-- 1 wilhelmi vetsdev    542280 Jan 28  2011 tas_SRESB1_end_seasonal_anomaly.nc
-rw-r--r-- 1 wilhelmi vetsdev  14020916 Jan 28  2011 tas_SRESB1_end_seasonal_down_anomaly.nc
-rw-r--r-- 1 wilhelmi vetsdev  14020436 Sep 22 17:26 tas_SRESB1_end_seasonal_down.nc
-rw-r--r-- 1 wilhelmi vetsdev    542168 Sep 22 17:26 tas_SRESB1_end_seasonal.nc
-rw-r--r-- 1 wilhelmi vetsdev    149096 Jan 28  2011 tas_SRESB1_LastDecade_annual_anomaly.nc
-rw-r--r-- 1 wilhelmi vetsdev   3550900 Jan 28  2011 tas_SRESB1_LastDecade_annual_down_anomaly.nc
-rw-r--r-- 1 wilhelmi vetsdev   3550456 Sep 22 17:26 tas_SRESB1_LastDecade_annual_down.nc
-rw-r--r-- 1 wilhelmi vetsdev    149020 Sep 22 17:26 tas_SRESB1_LastDecade_annual.nc
-rw-r--r-- 1 wilhelmi vetsdev   1591032 Jan 28  2011 tas_SRESB1_LastDecade_monthly_anomaly.nc
-rw-r--r-- 1 wilhelmi vetsdev  41941256 Jan 28  2011 tas_SRESB1_LastDecade_monthly_down_anomaly.nc
-rw-r--r-- 1 wilhelmi vetsdev  41940800 Sep 22 17:27 tas_SRESB1_LastDecade_monthly_down.nc
-rw-r--r-- 1 wilhelmi vetsdev   1590948 Sep 22 17:27 tas_SRESB1_LastDecade_monthly.nc
-rw-r--r-- 1 wilhelmi vetsdev    542336 Jan 28  2011 tas_SRESB1_LastDecade_seasonal_anomaly.nc
-rw-r--r-- 1 wilhelmi vetsdev  14020972 Jan 28  2011 tas_SRESB1_LastDecade_seasonal_down_anomaly.nc
-rw-r--r-- 1 wilhelmi vetsdev  14020516 Sep 22 17:27 tas_SRESB1_LastDecade_seasonal_down.nc
-rw-r--r-- 1 wilhelmi vetsdev    542248 Sep 22 17:27 tas_SRESB1_LastDecade_seasonal.nc
-rw-r--r-- 1 wilhelmi vetsdev    149040 Jan 28  2011 tas_SRESB1_mid_annual_anomaly.nc
-rw-r--r-- 1 wilhelmi vetsdev   3550844 Jan 28  2011 tas_SRESB1_mid_annual_down_anomaly.nc
-rw-r--r-- 1 wilhelmi vetsdev   3550360 Sep 22 17:27 tas_SRESB1_mid_annual_down.nc
-rw-r--r-- 1 wilhelmi vetsdev    148928 Sep 22 17:27 tas_SRESB1_mid_annual.nc
-rw-r--r-- 1 wilhelmi vetsdev   1590976 Jan 28  2011 tas_SRESB1_mid_monthly_anomaly.nc
-rw-r--r-- 1 wilhelmi vetsdev  41941200 Jan 28  2011 tas_SRESB1_mid_monthly_down_anomaly.nc
-rw-r--r-- 1 wilhelmi vetsdev  41940720 Sep 22 17:28 tas_SRESB1_mid_monthly_down.nc
-rw-r--r-- 1 wilhelmi vetsdev   1590868 Sep 22 17:28 tas_SRESB1_mid_monthly.nc
-rw-r--r-- 1 wilhelmi vetsdev    542280 Jan 28  2011 tas_SRESB1_mid_seasonal_anomaly.nc
-rw-r--r-- 1 wilhelmi vetsdev  14020916 Jan 28  2011 tas_SRESB1_mid_seasonal_down_anomaly.nc
-rw-r--r-- 1 wilhelmi vetsdev  14020436 Sep 22 17:28 tas_SRESB1_mid_seasonal_down.nc
-rw-r--r-- 1 wilhelmi vetsdev    542168 Sep 22 17:28 tas_SRESB1_mid_seasonal.nc
-rw-r--r-- 1 wilhelmi vetsdev    149048 Jan 28  2011 tas_SRESB1_near_annual_anomaly.nc
-rw-r--r-- 1 wilhelmi vetsdev   3550852 Jan 28  2011 tas_SRESB1_near_annual_down_anomaly.nc
-rw-r--r-- 1 wilhelmi vetsdev   3550376 Sep 22 17:28 tas_SRESB1_near_annual_down.nc
-rw-r--r-- 1 wilhelmi vetsdev    148940 Sep 22 17:28 tas_SRESB1_near_annual.nc
-rw-r--r-- 1 wilhelmi vetsdev   1590984 Jan 28  2011 tas_SRESB1_near_monthly_anomaly.nc
-rw-r--r-- 1 wilhelmi vetsdev  41941208 Jan 28  2011 tas_SRESB1_near_monthly_down_anomaly.nc
-rw-r--r-- 1 wilhelmi vetsdev  41940732 Sep 22 17:28 tas_SRESB1_near_monthly_down.nc
-rw-r--r-- 1 wilhelmi vetsdev   1590880 Sep 22 17:28 tas_SRESB1_near_monthly.nc
-rw-r--r-- 1 wilhelmi vetsdev    542288 Jan 28  2011 tas_SRESB1_near_seasonal_anomaly.nc
-rw-r--r-- 1 wilhelmi vetsdev  14020924 Jan 28  2011 tas_SRESB1_near_seasonal_down_anomaly.nc
-rw-r--r-- 1 wilhelmi vetsdev  14020448 Sep 22 17:28 tas_SRESB1_near_seasonal_down.nc
-rw-r--r-- 1 wilhelmi vetsdev    542180 Sep 22 17:28 tas_SRESB1_near_seasonal.nc

		 */
		
		
		// AnnualMean			-> [variable]_[ensemble]_annual_avg[ | _downscaled].nc
		// Long Term Average 	-> [variable]_[ensemble]_[projection(4)]_[time(monthly|seasonal|annual)].nc
		// Climate Anomoly		-> [variable]_[ensemble]_[projection(4)]_[time(monthly|seasonal|annual)]_anomoly.nc
		
		//tasmin_A1.20C3M_1.CCSM.atmm.1870-01_cat_1999-12.nc
		String fileNamePattern = productRequest.getVariable() + "_";
		
		fileNamePattern += productRequest.getEnsemble().getName() + "_";
		
		if(productRequest.getTemporalres() == TemporalResolution.ANNUAL_MEAN) {
			fileNamePattern += productRequest.getEnsemble().getName() + "annual_avg_";
		} 
		else {
			fileNamePattern += productRequest.getEnsemble().getName() + "annual_avg_";
		}
		
		
		
		
		
		
		
		
		String wildCardPattern = fileNamePattern.format(fileNamePattern, productRequest.getVariable(), this.scenarioDirectoryMap.get(productRequest.getModelSim()));
		
		File files = findFile(searchDirectory, wildCardPattern);
		
		
		return files;
	}
	
	public File findMonthlyDataFile(ProductRequest productRequest) {
		
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
		
		if(productRequest.getEnsemble() instanceof RunMember) {
			productDirectory += "/A1/" + productRequest.getEnsemble().getName();
		}
		
		
		File result = new File(this.baseDirectory, productDirectory);
		
		//tasmin_A1.20C3M_1.CCSM.atmm.1870-01_cat_1999-12.nc
		String fileNamePattern = "%s_A1.%s_*.nc";
		
		String wildCardPattern = fileNamePattern.format(fileNamePattern, productRequest.getVariable(), this.scenarioDirectoryMap.get(productRequest.getModelSim()));
		
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
