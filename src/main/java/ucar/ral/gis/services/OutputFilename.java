package ucar.ral.gis.services;

import ucar.ral.gis.services.web.RequestParameters;

public class OutputFilename {
	
	private RequestParameters requestParameters;
	
	public OutputFilename(RequestParameters requestParameters) {
		super();
		this.requestParameters = requestParameters;
	}

	public String getName() {
		
		// tas_All_Months_1870_1870_20th_Century_Experiment-180.0_180.0_-90.0_90.0
		
		return requestParameters.getVariable() + "_" + requestParameters.getMonth() + "_" + requestParameters.getStartDate().replace("/", "_") + "_" + 
			requestParameters.getEndDate().replace("/", "_") + "_" + requestParameters.getScenario() + 
			requestParameters.getXmin() + "_" + requestParameters.getXmax() + "_" +
			requestParameters.getYmin() + "_" + requestParameters.getYmax();
	}

}
