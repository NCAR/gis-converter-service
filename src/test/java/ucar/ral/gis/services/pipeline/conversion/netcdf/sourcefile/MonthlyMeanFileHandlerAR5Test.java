package ucar.ral.gis.services.pipeline.conversion.netcdf.sourcefile;

import org.junit.Before;
import org.junit.Test;
import ucar.ral.gis.services.netcdf2shapefile.rest.monthly.MonthlyMeanParameters;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MonthlyMeanFileHandlerAR5Test {

    private MonthlyMeanFileHandlerAR5 fileHandler;

    @Before
    public void setup() {
        fileHandler = new MonthlyMeanFileHandlerAR5(null, new File("/"), getAR4ScenarioMap());
    }

    private Map<String, String> getAR4ScenarioMap() {
        Map<String, String> ar4ScenarioMap =  new HashMap<String, String>();
        ar4ScenarioMap.put("SCENARIO", "Scenario");
        return ar4ScenarioMap;
    }

    @Test
    public void testCanHandleNonAR5Scenario() {
        MonthlyMeanParameters parameters = MonthlyMeanParametersBuilder.downScaleEnsembleAverage("SCENARIO", "average");
        assertThat(fileHandler.canHandle(parameters), is(false));
    }

    @Test
    public void testCanHandleAR5Scenario() {
        MonthlyMeanParameters parameters = MonthlyMeanParametersBuilder.downScaleEnsembleAverage("unmapped", "average");
        assertThat(fileHandler.canHandle(parameters), is(true));
    }

    @Test
    public void testGlobalRunMemberFileSpecification() {
        MonthlyMeanParameters parameters = MonthlyMeanParametersBuilder.globalRunEnsemble("rcp00", "5");

        FileSpecification result = fileHandler.getFileSpecification(parameters);

        assertThat(result.getDirectory().getAbsolutePath(), is("/ar5/CCSM/globalmonthly"));
        assertThat(result.getFilenamePattern(), is("tas_Amon_CCSM4_rcp00_r5i1p1_*.nc"));
    }

    @Test
    public void testGlobalEnsembleAverageFileSpecification() {
        MonthlyMeanParameters parameters = MonthlyMeanParametersBuilder.globalEnsembleAverage("rcp00", "average");

        FileSpecification result = fileHandler.getFileSpecification(parameters);

        assertThat(result.getDirectory().getAbsolutePath(), is("/ar5/CCSM/globalmonthly"));
        assertThat(result.getFilenamePattern(), is("tas_Amon_CCSM4_rcp00_ensave_*.nc"));
    }

    @Test
    public void testDownscaledRunMemberFileSpecification() {
        MonthlyMeanParameters parameters = MonthlyMeanParametersBuilder.downScaleRunEnsemble("rcp00", "5");

        FileSpecification result = fileHandler.getFileSpecification(parameters);

        assertThat(result.getDirectory().getAbsolutePath(), is("/ar5/CCSM/downmonthly"));
        assertThat(result.getFilenamePattern(), is("tas_Amon_CCSM4_rcp00_r5i1p1_*.nc"));
    }

    @Test
    public void testDownscaledEnsembleAverageFileSpecification() {
        MonthlyMeanParameters parameters = MonthlyMeanParametersBuilder.downScaleEnsembleAverage("rcp00", "average");

        FileSpecification result = fileHandler.getFileSpecification(parameters);

        assertThat(result.getDirectory().getAbsolutePath(), is("/ar5/CCSM/downmonthly"));
        assertThat(result.getFilenamePattern(), is("tas_Amon_CCSM4_rcp00_ensave_*.nc"));
    }
}
