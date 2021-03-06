package ucar.ral.gis.services.pipeline.conversion.netcdf.sourcefile;

import ucar.ral.gis.services.EnsembleAverage;
import ucar.ral.gis.services.EnsembleMember;
import ucar.ral.gis.services.Resolution;
import ucar.ral.gis.services.netcdf2shapefile.rest.BaseParameters;
import ucar.ral.gis.services.netcdf2shapefile.rest.monthly.MonthlyMeanParameters;

import java.io.File;
import java.util.Map;

public class MonthlyMeanFileHandlerAR5 extends AbstractSourceFileHandler {

    private File baseDirectory;

    protected MonthlyMeanFileHandlerAR5(SourceFileHandler nextHandler, File baseDirectory, Map<String, String> ar4ScenarioDirectoryMap) {
        super(nextHandler, ar4ScenarioDirectoryMap);
        this.baseDirectory = baseDirectory;
    }

    @Override
    protected boolean canHandle(BaseParameters baseParameters) {
        return baseParameters instanceof MonthlyMeanParameters &&
                !isAR4Scenario((MonthlyMeanParameters) baseParameters);
    }

    @Override
    protected FileSpecification getFileSpecification(BaseParameters baseParameters) {
        MonthlyMeanParameters parameters = (MonthlyMeanParameters) baseParameters;
        return new FileSpecification(getDirectoryFile(parameters), getFilenamePattern(parameters));
    }

    private File getDirectoryFile(MonthlyMeanParameters parameters) {
        if (parameters.getScale() == Resolution.DOWNSCALED) {
            return new File(this.baseDirectory, "ar5/CCSM/downmonthly");
        } else {
            return new File(this.baseDirectory, "ar5/CCSM/globalmonthly");
        }
    }

    private String getFilenamePattern(MonthlyMeanParameters parameters) {
        String pattern = String.format("%s_Amon_CCSM4_%s_%s_*.nc",
                parameters.getVariable(),
                parameters.getScenario(),
                getFilenameEnsembleName(parameters.getEnsemble()));
        return pattern;
    }

    private String getFilenameEnsembleName(EnsembleMember ensembleMember) {
        if (ensembleMember instanceof EnsembleAverage) {
            return "ensave";
        } else {
            return ensembleMember.getName().replace("run", "r") + "i1p1";
        }
    }
}
