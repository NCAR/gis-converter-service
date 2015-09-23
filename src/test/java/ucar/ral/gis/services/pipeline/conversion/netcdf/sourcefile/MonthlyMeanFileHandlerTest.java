package ucar.ral.gis.services.pipeline.conversion.netcdf.sourcefile;

import org.junit.Before;
import org.junit.Test;
import ucar.ral.gis.services.EnsembleAverage;
import ucar.ral.gis.services.netcdf2shapefile.rest.monthly.MonthlyMeanParameters;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class MonthlyMeanFileHandlerTest {

    private MonthlyMeanFileHandler fileHandler;

    @Before
    public void setup() {
        fileHandler = new MonthlyMeanFileHandler(null, new File("/"), getAR4ScenarioMap());
    }

    private Map<String, String> getAR4ScenarioMap() {
        Map<String, String> ar4ScenarioMap =  new HashMap<String, String>();
        ar4ScenarioMap.put("SCENARIO", "Scenario");
        return ar4ScenarioMap;
    }

    @Test
    public void testCanHandleNonAR4Scenario() {
        MonthlyMeanParameters parameters = MonthyMeanParametersFactoryForTest.getDownscaledMonthyMeanParameters("unmapped", "ensemble");
        assertThat(fileHandler.canHandle(parameters), is(false));
    }

    @Test
    public void testCanHandleAR4Scenario() {
        MonthlyMeanParameters parameters = MonthyMeanParametersFactoryForTest.getDownscaledMonthyMeanParameters("SCENARIO", "ensemble");
        assertThat(fileHandler.canHandle(parameters), is(true));
    }

    @Test
    public void testDownscaledRunMemberFileSpecification() {
        MonthlyMeanParameters parameters = MonthyMeanParametersFactoryForTest.getDownscaledMonthyMeanParameters("scenario", "ensemble");

        FileSpecification result = fileHandler.getFileSpecification(parameters);

        assertThat(result.getDirectory().getAbsolutePath(), is("/completeDownscaled"));
        assertAR4FilenamePattern(result);
    }

    @Test
    public void testDownscaledEnsembleAverageFileSpecification() {
        MonthlyMeanParameters parameters = MonthyMeanParametersFactoryForTest.getDownscaledMonthyMeanParameters("scenario", "ensemble");
        parameters.setEnsemble(new EnsembleAverage("ensemble"));

        FileSpecification result = fileHandler.getFileSpecification(parameters);

        assertThat(result.getDirectory().getAbsolutePath(), is("/completeDownscaled"));
        assertAR4FilenamePattern(result);
    }

    @Test
    public void testGlobalRunMemberFileSpecification() {
        MonthlyMeanParameters parameters = MonthyMeanParametersFactoryForTest.getGlobalMonthyMeanParameters("scenario", "ensemble");

        FileSpecification result = fileHandler.getFileSpecification(parameters);

        assertThat(result.getDirectory().getAbsolutePath(), is("/Scenario/A1/ensemble"));
        assertAR4FilenamePattern(result);
    }

    @Test
    public void testGlobalEnsembleAverageFileSpecification() {
        MonthlyMeanParameters parameters = MonthyMeanParametersFactoryForTest.getGlobalMonthyMeanParameters("scenario", "ensemble");
        parameters.setEnsemble(new EnsembleAverage("ensemble"));

        FileSpecification result = fileHandler.getFileSpecification(parameters);

        assertThat(result.getDirectory().getAbsolutePath(), is("/completeEnsembleAverages"));
        assertAR4FilenamePattern(result);
    }

    private void assertAR4FilenamePattern(FileSpecification result) {
        assertThat(result.getFilenamePattern(), is("tas_A1.Scenario_*.nc"));
    }
}