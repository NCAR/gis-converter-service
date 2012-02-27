package ucar.ral.gis.services.pipeline.conversion.netcdf.sourcefile;

import java.io.File;

import ucar.ral.gis.services.Resolution;
import ucar.ral.gis.services.TemporalResolution;
import ucar.ral.gis.services.netcdf2shapefile.rest.BaseParameters;
import ucar.ral.gis.services.netcdf2shapefile.rest.annual.AnnualMeanParameters;
import ucar.ral.gis.services.netcdf2shapefile.rest.longterm.LongTermAverageParameters;
import ucar.ral.gis.services.netcdf2shapefile.rest.monthly.MonthlyMeanParameters;

public class DerivedProductFileHandler extends AbstractSourceFileHandler {
		
	private File baseDirectory;
	
	private DerivedProductFileHandler(SourceFileHandler nextHandler, File baseDirectory) {
		super(nextHandler);
		this.baseDirectory = baseDirectory;
	}

	@Override
	protected boolean canHandle(BaseParameters baseParameters) {
		
		boolean result = ((baseParameters instanceof AnnualMeanParameters) ||
						 (baseParameters instanceof LongTermAverageParameters) || 
						 (baseParameters instanceof LongTermAverageParameters));
		
		return result;
	}

	@Override
	protected FileSpecification getFileSpecification(BaseParameters baseParameters) {

		// FIXME - Inject this!
		String productDirectory = "Products";
		
		File searchDirectory = new File(this.baseDirectory, productDirectory);
		
		// AnnualMean			-> [variable]_[ensemble]_annual_avg[ | _downscaled].nc
		// Long Term Average 	-> [variable]_[ensemble]_[projection(4)]_[time(monthly|seasonal|annual)].nc
		// Climate Anomoly		-> [variable]_[ensemble]_[projection(4)]_[time(monthly|seasonal|annual)]_anomoly.nc
		
		//tasmin_A1.20C3M_1.CCSM.atmm.1870-01_cat_1999-12.nc
		String fileNamePattern = baseParameters.getVariable() + "_";
		
		//fileNamePattern += productRequest.getEnsemble().getName() + "_";
		
		if(baseParameters.getTemporalResolution() == TemporalResolution.ANNUAL_MEAN) {
			if(baseParameters.getVariable().equalsIgnoreCase("tas")) {
				fileNamePattern += baseParameters.getScenario() + "_annual_avg";
			}
			else {
				fileNamePattern += baseParameters.getScenario() + "_annual_sum";
			}
		} 
		else if ((baseParameters.getTemporalResolution() == TemporalResolution.LONGTERM_AVERAGE) || (baseParameters.getTemporalResolution() == TemporalResolution.CLIMATE_ANOMOLY))  {
			
			LongTermAverageParameters ltaParameters = (LongTermAverageParameters) baseParameters;
			
			fileNamePattern += baseParameters.getScenario() + "_" + ltaParameters.getTerm() + "_" + ltaParameters.getPeriod() ;
		}
		
		if(baseParameters.getScale() == Resolution.DOWNSCALED) {
			fileNamePattern += "_down";
		}
		
		if(baseParameters.getTemporalResolution() == TemporalResolution.CLIMATE_ANOMOLY) {
			fileNamePattern += "_anomaly";
		}
		
		fileNamePattern += ".nc";
		
		FileSpecification fileSpec = new FileSpecification(searchDirectory, fileNamePattern);
		
		return fileSpec;
	}

}
