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

    private MonthlyMeanFileHandler(SourceFileHandler nextHandler, File baseDirectory, Map<String, String> scenarioDirectoryMap) {
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

        String productDirectory;
        
        if (parameters.getEnsemble() instanceof RunMember) {

            if (Resolution.DOWNSCALED == parameters.getScale()) {
                productDirectory = "completeDownscaled";
            } else {

                // Pull it from the scenario map
                String scenarioDirectory = this.scenarioDirectoryMap.get(parameters.getScenario().toUpperCase());
                productDirectory = scenarioDirectory;

                productDirectory += "/A1/" + parameters.getEnsemble().getName();
            }

        } else {

            if (Resolution.DOWNSCALED == parameters.getScale()) {
                productDirectory = "completeDownscaled";
            } else {
                productDirectory = "completeEnsembleAverages";
            }

        }

        return getFileSpecification(parameters, productDirectory);
    }

    private FileSpecification getFileSpecification(MonthlyMeanParameters parameters, String productDirectory) {
        FileSpecification fileSpec;

        File result = new File(this.baseDirectory, productDirectory);

        //tasmin_A1.20C3M_1.CCSM.atmm.1870-01_cat_1999-12.nc
        String fileNamePattern = "%s_A1.%s_*.nc";

        String wildCardPattern = fileNamePattern.format(fileNamePattern, parameters.getVariable(), this.scenarioDirectoryMap.get(parameters.getScenario().toUpperCase()));

        fileSpec = new FileSpecification(result, wildCardPattern);

        return fileSpec;
    }

}
