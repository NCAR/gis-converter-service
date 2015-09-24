package ucar.ral.gis.services.pipeline.conversion.netcdf.sourcefile;

import ucar.ral.gis.services.Resolution;
import ucar.ral.gis.services.TemporalResolution;
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
    protected FileSpecification getFileSpecification(BaseParameters parameters) {
        return new FileSpecification(getDirectoryFile(parameters), getFilenamePattern(parameters));
    }

    private String getFilenamePattern(BaseParameters parameters) {
        String pattern = String.format("%s_Amon_CCSM4_%s_%s_*.nc",
                getVariableNameVaryingByTemporalResolution(parameters),
                parameters.getScenario(),
                getEnsembleNameVaryingByTemporalResolution(parameters));
        return pattern;
    }

    private String getVariableNameVaryingByTemporalResolution(BaseParameters parameters) {
        if (parameters.getTemporalResolution() == TemporalResolution.ANNUAL_MEAN) {
            return parameters.getVariable();
        } else if (parameters.getTemporalResolution() == TemporalResolution.LONGTERM_AVERAGE) {
            return getVariableNameVaryingByLongTermAveragePeriod((LongTermAverageParameters) parameters);
        } else if (parameters.getTemporalResolution() == TemporalResolution.CLIMATE_ANOMOLY) {
            return getVariableNameVaryingByClimateAnomaly((LongTermAverageParameters) parameters);
        }
        return null;
    }

    private String getVariableNameVaryingByLongTermAveragePeriod(LongTermAverageParameters parameters) {
        if (parameters.getPeriod().equals("monthly")) {
            return parameters.getVariable() + "240m";
        } else if (parameters.getPeriod().equals("annual")) {
            return parameters.getVariable() + "20y";
        }
        return null;
    }

    private String getVariableNameVaryingByClimateAnomaly(LongTermAverageParameters parameters) {
        return "a" + getVariableNameVaryingByLongTermAveragePeriod(parameters);
    }

    private String getEnsembleNameVaryingByTemporalResolution(BaseParameters parameters) {
        if (parameters.getTemporalResolution() == TemporalResolution.ANNUAL_MEAN) {
            return "annual";
        } else if (parameters.getTemporalResolution() == TemporalResolution.LONGTERM_AVERAGE) {
            return ((LongTermAverageParameters) parameters).getPeriod();
        } else if (parameters.getTemporalResolution() == TemporalResolution.CLIMATE_ANOMOLY) {
            return ((LongTermAverageParameters) parameters).getPeriod();
        }
        return null;
    }

    private File getDirectoryFile(BaseParameters parameters) {
        if (parameters.getScale() == Resolution.DOWNSCALED) {
            return new File(this.baseDirectory, "/ar5/CCSM/downproducts");
        } else {
            return new File(this.baseDirectory, "/ar5/CCSM/globalproducts");
        }
    }
}
