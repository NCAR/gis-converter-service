package ucar.ral.gis.services.pipeline.conversion.netcdf.sourcefile;

import ucar.ral.gis.services.netcdf2shapefile.rest.longterm.LongTermAverageParameters;

public class LongTermAverageParametersBuilder {

    private String scenario;

    public LongTermAverageParametersBuilder scenario(String scenario) {
        this.scenario = scenario;
        return this;
    }

    public LongTermAverageParameters build() {
        LongTermAverageParameters parameters = new LongTermAverageParameters();

        parameters.setScenario(scenario);

        return parameters;
    }

    public static LongTermAverageParameters getScenario(String scenario) {
        return new LongTermAverageParametersBuilder()
                .scenario(scenario).build();
    }

}
