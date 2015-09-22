package ucar.ral.gis.services.pipeline.conversion.netcdf.sourcefile;

import org.junit.Before;
import org.junit.Test;
import ucar.ral.gis.services.EnsembleAverage;
import ucar.ral.gis.services.Resolution;
import ucar.ral.gis.services.RunMember;
import ucar.ral.gis.services.netcdf2shapefile.rest.BaseParameters;
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
    public void testAR4DownscaledRunMemberFileSpecification() {
        MonthlyMeanParameters parameters = getDownscaledMonthyMeanParameters("scenario", "ensemble");

        FileSpecification result = fileHandler.getFileSpecification(parameters);

        assertThat(result.getDirectory().getAbsolutePath(), is("/completeDownscaled"));
        assertAR4FilenamePattern(result);
    }

    @Test
    public void testAR4DownscaledEnsembleAverageFileSpecification() {
        MonthlyMeanParameters parameters = getDownscaledMonthyMeanParameters("scenario", "ensemble");
        parameters.setEnsemble(new EnsembleAverage("ensemble"));

        FileSpecification result = fileHandler.getFileSpecification(parameters);

        assertThat(result.getDirectory().getAbsolutePath(), is("/completeDownscaled"));
        assertAR4FilenamePattern(result);
    }

    @Test
    public void testAR4GlobalRunMemberFileSpecification() {
        MonthlyMeanParameters parameters = getGlobalMonthyMeanParameters("scenario", "ensemble");

        FileSpecification result = fileHandler.getFileSpecification(parameters);

        assertThat(result.getDirectory().getAbsolutePath(), is("/Scenario/A1/ensemble"));
        assertAR4FilenamePattern(result);
    }

    @Test
    public void testAR4GlobalEnsembleAverageFileSpecification() {
        MonthlyMeanParameters parameters = getGlobalMonthyMeanParameters("scenario", "ensemble");
        parameters.setEnsemble(new EnsembleAverage("ensemble"));

        FileSpecification result = fileHandler.getFileSpecification(parameters);

        assertThat(result.getDirectory().getAbsolutePath(), is("/completeEnsembleAverages"));
        assertAR4FilenamePattern(result);
    }

    @Test
    public void testAR5GlobalFileSpecification() {
        MonthlyMeanParameters parameters = getGlobalMonthyMeanParameters("rcp00", "r5i1p1");

        FileSpecification result = fileHandler.getFileSpecification(parameters);

        assertThat(result.getDirectory().getAbsolutePath(), is("/ar5/CCSM/globalmonthly"));
        assertThat(result.getFilenamePattern(), is("tas_Amon_CCSM4_rcp00_r5i1p1_*.nc"));
    }

    @Test
    public void testAR5DownscaledFileSpecification() {
        MonthlyMeanParameters parameters = getDownscaledMonthyMeanParameters("rcp00", "r5i1p1");

        FileSpecification result = fileHandler.getFileSpecification(parameters);

        assertThat(result.getDirectory().getAbsolutePath(), is("/ar5/CCSM/downmonthly"));
        assertThat(result.getFilenamePattern(), is("tas_Amon_CCSM4_rcp00_r5i1p1_*.nc"));
    }

    private void assertAR4FilenamePattern(FileSpecification result) {
        assertThat(result.getFilenamePattern(), is("tas_A1.Scenario_*.nc"));
    }

    private MonthlyMeanParameters getGlobalMonthyMeanParameters(String scenarioName, String ensembleName) {
        MonthlyMeanParameters parameters = getMonthyMeanParameters(scenarioName, ensembleName);
        parameters.setScale(Resolution.GLOBAL);

        return parameters;
    }

    private MonthlyMeanParameters getDownscaledMonthyMeanParameters(String scenarioName, String ensembleName) {
        MonthlyMeanParameters parameters = getMonthyMeanParameters(scenarioName, ensembleName);
        parameters.setScale(Resolution.DOWNSCALED);

        return parameters;
    }

    private MonthlyMeanParameters getMonthyMeanParameters(String scenarioName, String ensembleName) {
        MonthlyMeanParameters parameters = new MonthlyMeanParameters();
        parameters.setScenario(scenarioName);
        parameters.setEnsemble(new RunMember(ensembleName));
        parameters.setVariable("tas");

        return parameters;
    }
}