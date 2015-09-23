package ucar.ral.gis.services.pipeline.conversion.netcdf.sourcefile;

import ucar.ral.gis.services.EnsembleAverage;
import ucar.ral.gis.services.EnsembleMember;
import ucar.ral.gis.services.Resolution;
import ucar.ral.gis.services.RunMember;
import ucar.ral.gis.services.netcdf2shapefile.rest.monthly.MonthlyMeanParameters;

public class MonthlyMeanParametersBuilder {

    private Resolution scale;
    private String variable;
    private String scenario;
    private EnsembleMember ensemble;

    public MonthlyMeanParametersBuilder scale(Resolution scale) {
        this.scale = scale;
        return this;
    }

    public MonthlyMeanParametersBuilder variable(String variable) {
        this.variable = variable;
        return this;
    }

    public MonthlyMeanParametersBuilder scenario(String scenario) {
        this.scenario = scenario;
        return this;
    }

    public MonthlyMeanParametersBuilder ensemble(EnsembleMember ensemble) {
        this.ensemble = ensemble;
        return this;
    }

    public MonthlyMeanParameters build() {
        MonthlyMeanParameters parameters = new MonthlyMeanParameters();

        parameters.setScale(scale);
        parameters.setScenario(scenario);
        parameters.setEnsemble(ensemble);
        parameters.setVariable(variable);

        return parameters;
    }

    public static MonthlyMeanParameters globalRunEnsemble(String scenario, String runEnsembleName) {
        return new MonthlyMeanParametersBuilder()
                .scale(Resolution.GLOBAL)
                .scenario(scenario)
                .ensemble(new RunMember(runEnsembleName))
                .variable("tas")
                .build();
    }

    public static MonthlyMeanParameters downScaleRunEnsemble(String scenario, String runEnsembleName) {
        return new MonthlyMeanParametersBuilder()
                .scale(Resolution.DOWNSCALED)
                .scenario(scenario)
                .ensemble(new RunMember(runEnsembleName))
                .variable("tas")
                .build();
    }

    public static MonthlyMeanParameters globalEnsembleAverage(String scenario, String ensembleAverageName) {
        return new MonthlyMeanParametersBuilder()
                .scale(Resolution.GLOBAL)
                .scenario(scenario)
                .ensemble(new EnsembleAverage(ensembleAverageName))
                .variable("tas")
                .build();
    }

    public static MonthlyMeanParameters downScaleEnsembleAverage(String scenario, String ensembleAverageName) {
        return new MonthlyMeanParametersBuilder()
                .scale(Resolution.DOWNSCALED)
                .scenario(scenario)
                .ensemble(new EnsembleAverage(ensembleAverageName))
                .variable("tas")
                .build();
    }
}
