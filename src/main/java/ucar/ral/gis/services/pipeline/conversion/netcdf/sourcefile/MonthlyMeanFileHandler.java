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

        boolean result = baseParameters instanceof MonthlyMeanParameters;

        return result;
    }

    @Override
    protected FileSpecification getFileSpecification(BaseParameters baseParameters) {

        MonthlyMeanParameters parameters = (MonthlyMeanParameters) baseParameters;

        FileSpecification fileSpec = new FileSpecification(getSearchDirectory(parameters), getFilenamePattern(parameters));

        return fileSpec;
    }

    protected File getSearchDirectory(MonthlyMeanParameters parameters) {

        String productDirectory;

        if (Resolution.DOWNSCALED == parameters.getScale()) {
            productDirectory = "completeDownscaled";
        } else if (parameters.getEnsemble() instanceof RunMember){
            productDirectory = getScenarioMapping(parameters) + "/A1/" + parameters.getEnsemble().getName();
        } else {
            productDirectory = "completeEnsembleAverages";
        }

        return new File(this.baseDirectory, productDirectory);
    }

    private String getScenarioMapping(MonthlyMeanParameters parameters) {
        if (!this.scenarioDirectoryMap.containsKey(parameters.getScenario().toUpperCase())) {
            throw new UnknownScenario();
        }
        return this.scenarioDirectoryMap.get(parameters.getScenario().toUpperCase());
    }

    public String getFilenamePattern(MonthlyMeanParameters parameters) {
        String pattern = String.format("%s_A1.%s_*.nc", parameters.getVariable(), getScenarioMapping(parameters));
        return pattern;
    }
}
