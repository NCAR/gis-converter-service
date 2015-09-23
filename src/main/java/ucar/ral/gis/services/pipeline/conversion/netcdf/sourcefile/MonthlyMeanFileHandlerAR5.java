package ucar.ral.gis.services.pipeline.conversion.netcdf.sourcefile;

import ucar.ral.gis.services.Resolution;
import ucar.ral.gis.services.netcdf2shapefile.rest.BaseParameters;
import ucar.ral.gis.services.netcdf2shapefile.rest.monthly.MonthlyMeanParameters;

import java.io.File;
import java.util.Map;

public class MonthlyMeanFileHandlerAR5 extends AbstractSourceFileHandler {

    private File baseDirectory;
    private Map<String, String> ar4ScenarioDirectoryMap;

    protected MonthlyMeanFileHandlerAR5(SourceFileHandler nextHandler, File baseDirectory, Map<String, String> ar4ScenarioDirectoryMap) {
        super(nextHandler);
        this.baseDirectory = baseDirectory;
        this.ar4ScenarioDirectoryMap = ar4ScenarioDirectoryMap;
    }

    @Override
    protected boolean canHandle(BaseParameters baseParameters) {
        return baseParameters instanceof MonthlyMeanParameters &&
                isAR5Scenario((MonthlyMeanParameters) baseParameters);
    }

    @Override
    protected FileSpecification getFileSpecification(BaseParameters baseParameters) {
        MonthlyMeanParameters parameters = (MonthlyMeanParameters) baseParameters;
        return new FileSpecification(getDirectoryFile(parameters), getFilenamePattern(parameters));
    }

    private boolean isAR5Scenario(MonthlyMeanParameters parameters) {
        return !ar4ScenarioDirectoryMap.containsKey(parameters.getScenario().toUpperCase());
    }

    private File getDirectoryFile(MonthlyMeanParameters parameters) {
        if (Resolution.DOWNSCALED == parameters.getScale()) {
            return new File(this.baseDirectory, "ar5/CCSM/downmonthly");
        } else {
            return new File(this.baseDirectory, "ar5/CCSM/globalmonthly");
        }
    }

    private String getFilenamePattern(MonthlyMeanParameters parameters) {
        String pattern = String.format("%s_Amon_CCSM4_%s_%s_*.nc", parameters.getVariable(), parameters.getScenario(), parameters.getEnsemble().getName());
        return pattern;
    }
}
