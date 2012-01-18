package ucar.ral.gis.services;



import ucar.ral.gis.services.web.BaseParameters;
import ucar.ral.gis.services.web.MonthlyMeanParameters;

public class OutputFileNameFactory {
	
	public String create(BaseParameters parameters) {
		
		if(parameters instanceof MonthlyMeanParameters) {
			return this.create( (MonthlyMeanParameters) parameters);
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

}
