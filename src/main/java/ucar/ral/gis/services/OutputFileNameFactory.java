package ucar.ral.gis.services;



import ucar.ral.gis.services.netcdf2shapefile.rest.BaseParameters;
import ucar.ral.gis.services.netcdf2shapefile.rest.monthly.MonthlyMeanParameters;

public class OutputFileNameFactory {
	
	public String create(BaseParameters parameters) {
		
		if(parameters instanceof MonthlyMeanParameters) {
			return this._create( (MonthlyMeanParameters) parameters);
		}
		else {
			return this._create( parameters);
		}
//		else {
//			throw new RuntimeException("Can't create filename based on base parameters");
//		}
	}

	
	public String _create(MonthlyMeanParameters parameters) {
		
		return parameters.getVariable() + "_" + parameters.getMonth().getDescription().replace(" ", "_") + "_" + parameters.getStartYear() + "_" + 
				parameters.getEndYear() + "_" + parameters.getScenario() + 
				parameters.getXmin() + "_" + parameters.getXmax() + "_" +
				parameters.getYmin() + "_" + parameters.getYmax();
	}
	
	public String _create(BaseParameters parameters) {
		
		return parameters.getVariable() + "_" + parameters.getScenario() + 
				parameters.getXmin() + "_" + parameters.getXmax() + "_" +
				parameters.getYmin() + "_" + parameters.getYmax();
	}

}
