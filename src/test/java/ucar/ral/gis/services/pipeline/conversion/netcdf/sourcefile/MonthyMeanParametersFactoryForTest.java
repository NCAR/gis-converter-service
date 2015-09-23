package ucar.ral.gis.services.pipeline.conversion.netcdf.sourcefile;

import ucar.ral.gis.services.Resolution;
import ucar.ral.gis.services.RunMember;
import ucar.ral.gis.services.netcdf2shapefile.rest.monthly.MonthlyMeanParameters;

public class MonthyMeanParametersFactoryForTest {

    public static MonthlyMeanParameters getGlobalMonthyMeanParameters(String scenarioName, String ensembleName) {
        MonthlyMeanParameters parameters = getMonthyMeanParameters(scenarioName, ensembleName);
        parameters.setScale(Resolution.GLOBAL);

        return parameters;
    }

    public static MonthlyMeanParameters getDownscaledMonthyMeanParameters(String scenarioName, String ensembleName) {
        MonthlyMeanParameters parameters = getMonthyMeanParameters(scenarioName, ensembleName);
        parameters.setScale(Resolution.DOWNSCALED);

        return parameters;
    }

    public static MonthlyMeanParameters getMonthyMeanParameters(String scenarioName, String ensembleName) {
        MonthlyMeanParameters parameters = new MonthlyMeanParameters();
        parameters.setScenario(scenarioName);
        parameters.setEnsemble(new RunMember(ensembleName));
        parameters.setVariable("tas");

        return parameters;
    }
}
