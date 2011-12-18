package ucar.ral.gis.services;

import java.io.File;

public class DataFileFactory {
	
	private File baseDirectory;
	
	
	public DataFileFactory(File baseDirectory) {
		super();
		this.baseDirectory = baseDirectory;
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
	
	public File findDataFile() {
		
		//http://commons.apache.org/io/api-release/org/apache/commons/io/filefilter/WildcardFileFilter.html
		
		return this.baseDirectory;
	}

}
