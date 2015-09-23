package ucar.ral.gis.services.pipeline.conversion.netcdf.sourcefile;

import ucar.ral.gis.services.netcdf2shapefile.rest.BaseParameters;
import ucar.ral.gis.services.netcdf2shapefile.rest.annual.AnnualMeanParameters;
import ucar.ral.gis.services.netcdf2shapefile.rest.longterm.LongTermAverageParameters;

import java.io.File;
import java.util.Map;

public class DerivedProductFileHandlerAR5 extends AbstractSourceFileHandler {

    private File baseDirectory;

    public DerivedProductFileHandlerAR5(SourceFileHandler nextHandler, File baseDirectory, Map<String, String> ar4ScenarioDirectoryMap) {
        super(nextHandler, ar4ScenarioDirectoryMap);
        this.baseDirectory = baseDirectory;
    }

    @Override
    protected boolean canHandle(BaseParameters baseParameters) {

        boolean result = ((baseParameters instanceof AnnualMeanParameters ||
                baseParameters instanceof LongTermAverageParameters) &&
                !isAR4Scenario(baseParameters));

        return result;
    }

    @Override
    protected FileSpecification getFileSpecification(BaseParameters baseParameters) {
        return null;
    }
}
