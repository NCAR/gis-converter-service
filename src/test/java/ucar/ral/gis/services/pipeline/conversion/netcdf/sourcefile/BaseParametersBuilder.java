package ucar.ral.gis.services.pipeline.conversion.netcdf.sourcefile;

import ucar.ral.gis.services.Resolution;
import ucar.ral.gis.services.TemporalResolution;
import ucar.ral.gis.services.netcdf2shapefile.rest.BaseParameters;

public class BaseParametersBuilder {

    private Resolution scale;
    private String variable;
    private String scenario;
    private TemporalResolution temporalResolution;
    private Float xmin = -180.0F;
    private Float xmax = 180.0F;
    private Float ymin = -90.0F;
    private Float ymax = 90.0F;

    public BaseParametersBuilder scale(Resolution scale) {
        this.scale = scale;
        return this;
    }

    public BaseParametersBuilder variable(String variable) {
        this.variable = variable;
        return this;
    }

    public BaseParametersBuilder scenario(String scenario) {
        this.scenario = scenario;
        return this;
    }

    public BaseParametersBuilder temporalResolution(TemporalResolution temporalResolution) {
        this.temporalResolution = temporalResolution;
        return this;
    }

    public BaseParameters build() {
        BaseParameters parameters = new BaseParameters();

        parameters.setVariable(variable);
        parameters.setScale(scale);
        parameters.setScenario(scenario);
        parameters.setTemporalResolution(temporalResolution);

        return parameters;
    }

    public static BaseParameters getGlobal(String scenario, TemporalResolution temporalResolution) {
        return new BaseParametersBuilder()
                .scale(Resolution.GLOBAL)
                .scenario(scenario)
                .temporalResolution(temporalResolution)
                .variable("tas")
                .build();
    }

    public static BaseParameters getGlobal() {
        return new BaseParametersBuilder()
                .scale(Resolution.GLOBAL)
                .build();
    }

    public static BaseParameters getDownscaled() {
        return new BaseParametersBuilder()
                .scale(Resolution.DOWNSCALED)
                .build();
    }
}
