package ucar.ral.gis.services.pipeline.conversion.netcdf.sourcefile;

import ucar.ral.gis.services.Resolution;
import ucar.ral.gis.services.RunMember;
import ucar.ral.gis.services.netcdf2shapefile.rest.BaseParameters;
import ucar.ral.gis.services.netcdf2shapefile.rest.monthly.MonthlyMeanParameters;

import java.io.File;
import java.util.Map;

public class MonthlyMeanFileHandler extends AbstractSourceFileHandler {

    private File baseDirectory;
    private Map<String, String> scenarioDirectoryMap;

    protected MonthlyMeanFileHandler(SourceFileHandler nextHandler, File baseDirectory, Map<String, String> scenarioDirectoryMap) {
        super(nextHandler);
        this.baseDirectory = baseDirectory;
        this.scenarioDirectoryMap = scenarioDirectoryMap;
    }

    @Override
    protected boolean canHandle(BaseParameters baseParameters) {
        return baseParameters instanceof MonthlyMeanParameters;
    }

    @Override
    protected FileSpecification getFileSpecification(BaseParameters baseParameters) {
        MonthlyMeanParameters parameters = (MonthlyMeanParameters) baseParameters;

        if (isAR4Scenario(parameters)) {
            return getAR4FileSpecification(parameters);
        } else {
            return getAR5FileSpecification(parameters);
        }
    }

    private boolean isAR4Scenario(MonthlyMeanParameters parameters) {
        return this.scenarioDirectoryMap.containsKey(parameters.getScenario().toUpperCase());
    }

    private FileSpecification getAR4FileSpecification(MonthlyMeanParameters parameters) {
        return new FileSpecification(getAR4DirectoryFile(parameters), getAR4FilenamePattern(parameters));
    }

    private File getAR4DirectoryFile(MonthlyMeanParameters parameters) {
        return new File(this.baseDirectory, getAR4Directory(parameters));
    }

    private String getAR4Directory(MonthlyMeanParameters parameters) {
        if (Resolution.DOWNSCALED == parameters.getScale()) {
            return "completeDownscaled";
        } else if (parameters.getEnsemble() instanceof RunMember) {
            return getScenarioMapping(parameters) + "/A1/" + parameters.getEnsemble().getName();
        } else {
            return "completeEnsembleAverages";
        }
    }

    private String getScenarioMapping(MonthlyMeanParameters parameters) {
        return this.scenarioDirectoryMap.get(parameters.getScenario().toUpperCase());
    }

    private String getAR4FilenamePattern(MonthlyMeanParameters parameters) {
        String pattern = String.format("%s_A1.%s_*.nc", parameters.getVariable(), getScenarioMapping(parameters));
        return pattern;
    }

    private FileSpecification getAR5FileSpecification(MonthlyMeanParameters parameters) {
        return new FileSpecification(getAR5DirectoryFile(parameters), getAR5FilenamePattern(parameters));
    }

    private File getAR5DirectoryFile(MonthlyMeanParameters parameters) {
        if (Resolution.DOWNSCALED == parameters.getScale()) {
            return new File(this.baseDirectory, "ar5/CCSM/downmonthly");
        } else {
            return new File(this.baseDirectory, "ar5/CCSM/globalmonthly");
        }
    }

    private String getAR5FilenamePattern(MonthlyMeanParameters parameters) {
        String pattern = String.format("%s_Amon_CCSM4_%s_%s_*.nc", parameters.getVariable(), parameters.getScenario(), parameters.getEnsemble().getName());
        return pattern;
    }
}
