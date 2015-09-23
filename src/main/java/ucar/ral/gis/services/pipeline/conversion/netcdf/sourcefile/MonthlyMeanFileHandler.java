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
        return baseParameters instanceof MonthlyMeanParameters &&
                isAR4Scenario((MonthlyMeanParameters) baseParameters);
    }

    @Override
    protected FileSpecification getFileSpecification(BaseParameters baseParameters) {
        MonthlyMeanParameters parameters = (MonthlyMeanParameters) baseParameters;
        return new FileSpecification(getDirectoryFile(parameters), getFilenamePattern(parameters));
    }

    private boolean isAR4Scenario(MonthlyMeanParameters parameters) {
        return this.scenarioDirectoryMap.containsKey(parameters.getScenario().toUpperCase());
    }

    private File getDirectoryFile(MonthlyMeanParameters parameters) {
        return new File(this.baseDirectory, getDirectory(parameters));
    }

    private String getDirectory(MonthlyMeanParameters parameters) {
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

    private String getFilenamePattern(MonthlyMeanParameters parameters) {
        String pattern = String.format("%s_A1.%s_*.nc", parameters.getVariable(), getScenarioMapping(parameters));
        return pattern;
    }
}
