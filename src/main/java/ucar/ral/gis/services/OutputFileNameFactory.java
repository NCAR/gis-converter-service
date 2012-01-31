package ucar.ral.gis.services;



import ucar.ral.gis.services.netcdf2shapefile.rest.BaseParameters;
import ucar.ral.gis.services.netcdf2shapefile.rest.DerivedProductParameters;
import ucar.ral.gis.services.netcdf2shapefile.rest.MonthlyMeanParameters;

public class OutputFileNameFactory {
	
	public String create(BaseParameters parameters) {
		
		if(parameters instanceof MonthlyMeanParameters) {
			return this.create( (MonthlyMeanParameters) parameters);
		}
		if(parameters instanceof DerivedProductParameters) {
			return this.create( (DerivedProductParameters) parameters);
		}
		else {
			throw new RuntimeException("Can't create filename based on base parameters");
		}
	}

	
	public String create(MonthlyMeanParameters parameters) {
		
		return parameters.getVariable() + "_" + parameters.getMonth().getDescription().replace(" ", "_") + "_" + parameters.getStartYear() + "_" + 
				parameters.getEndYear() + "_" + parameters.getScenario() + 
				parameters.getXmin() + "_" + parameters.getXmax() + "_" +
				parameters.getYmin() + "_" + parameters.getYmax();
	}
	
	public String create(DerivedProductParameters parameters) {
		
		return parameters.getVariable() + "_" + parameters.getMonth().getDescription().replace(" ", "_") + "_" + parameters.getStartYear() + "_" + 
				parameters.getEndYear() + "_" + parameters.getScenario() + 
				parameters.getXmin() + "_" + parameters.getXmax() + "_" +
				parameters.getYmin() + "_" + parameters.getYmax();
	}

}
