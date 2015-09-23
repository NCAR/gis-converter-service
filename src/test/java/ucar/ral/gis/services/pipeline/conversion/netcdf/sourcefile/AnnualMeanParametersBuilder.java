package ucar.ral.gis.services.pipeline.conversion.netcdf.sourcefile;

import ucar.ral.gis.services.netcdf2shapefile.rest.annual.AnnualMeanParameters;

public class AnnualMeanParametersBuilder {

    private String scenario;

    public AnnualMeanParametersBuilder scenario(String scenario) {
        this.scenario = scenario;
        return this;
    }

    public AnnualMeanParameters build() {
        AnnualMeanParameters parameters = new AnnualMeanParameters();

        parameters.setScenario(scenario);

        return parameters;
    }

    public static AnnualMeanParameters getScenario(String scenario) {
        return new AnnualMeanParametersBuilder()
                .scenario(scenario).build();
    }
}
