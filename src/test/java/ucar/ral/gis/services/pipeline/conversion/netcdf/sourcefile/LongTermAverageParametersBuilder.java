package ucar.ral.gis.services.pipeline.conversion.netcdf.sourcefile;

import ucar.ral.gis.services.Resolution;
import ucar.ral.gis.services.TemporalResolution;
import ucar.ral.gis.services.netcdf2shapefile.rest.longterm.LongTermAverageParameters;

public class LongTermAverageParametersBuilder {

    private Resolution scale;
    private String variable;
    private String scenario;
    private TemporalResolution temporalResolution;
    private String period;

    public LongTermAverageParametersBuilder scale(Resolution scale) {
        this.scale = scale;
        return this;
    }

    public LongTermAverageParametersBuilder variable(String variable) {
        this.variable = variable;
        return this;
    }

    public LongTermAverageParametersBuilder scenario(String scenario) {
        this.scenario = scenario;
        return this;
    }

    public LongTermAverageParametersBuilder temporalResolution(TemporalResolution temporalResolution) {
        this.temporalResolution = temporalResolution;
        return this;
    }

    public LongTermAverageParametersBuilder period(String period) {
        this.period = period;
        return this;
    }

    public LongTermAverageParameters build() {
        LongTermAverageParameters parameters = new LongTermAverageParameters();

        parameters.setScale(scale);
        parameters.setVariable(variable);
        parameters.setScenario(scenario);
        parameters.setTemporalResolution(temporalResolution);
        parameters.setPeriod(period);

        return parameters;
    }

    public static LongTermAverageParameters getScenario(String scenario) {
        return new LongTermAverageParametersBuilder()
                .scenario(scenario)
                .temporalResolution(TemporalResolution.LONGTERM_AVERAGE)
                .build();
    }

    public static LongTermAverageParameters getGlobalTas(String scenario, String period) {
        return new LongTermAverageParametersBuilder()
                .scale(Resolution.GLOBAL)
                .scenario(scenario)
                .period(period)
                .variable("tas")
                .temporalResolution(TemporalResolution.LONGTERM_AVERAGE)
                .build();
    }
}
