package ucar.ral.gis.services.pipeline.conversion.netcdf.sourcefile;

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

    private MonthlyMeanFileHandler fileHandler = new MonthlyMeanFileHandler(null, new File("/"), buildScenarioMap());

    //@Test
    public void testGetFileSpecification() throws Exception {

        BaseParameters parameters = new BaseParameters();

        FileSpecification spec = fileHandler.getFileSpecification(null);



    }

    public Map<String, String> buildScenarioMap() {
        Map<String, String> result =  new HashMap<String, String>();

        result.put("X", "X");

        return result;
    }

    @Test
    public void testGetDownscaledEnsembleSearchDirectory() {

        MonthlyMeanParameters parameters = getDownscaledMonthyMeanParameters("z", "None");

        File result = fileHandler.getSearchDirectory(parameters);

        assertThat(result.getAbsolutePath(), is("/completeDownscaled"));
    }

    @Test
    public void testGetGlobalEnsembleSearchDirectory() {

        MonthlyMeanParameters parameters = new MonthlyMeanParameters();
        parameters.setScale(Resolution.GLOBAL);
        parameters.setEnsemble(new EnsembleAverage("None"));

        File result = fileHandler.getSearchDirectory(parameters);

        assertThat(result.getAbsolutePath(), is("/completeEnsembleAverages"));
    }

    @Test
    public void testGetDownscaledRunmemberSearchDirectory() {

        MonthlyMeanParameters parameters = getDownscaledMonthyMeanParameters("z", "None");

        File result = fileHandler.getSearchDirectory(parameters);

        assertThat(result.getAbsolutePath(), is("/completeDownscaled"));
    }

    @Test
    public void testGetGlobalRunmemberSearchDirectory() {

        MonthlyMeanParameters parameters = getGlobalMonthyMeanParameters("x", "test");

        File result = fileHandler.getSearchDirectory(parameters);

        assertThat(result.getAbsolutePath(), is("/X/A1/test"));
    }

    @Test(expected = UnknownScenario.class)
    public void testUnknownScenario() {
        MonthlyMeanParameters parameters = getGlobalMonthyMeanParameters("Unmapped", "test");
        fileHandler.getSearchDirectory(parameters);
    }

    @Test
    public void testGetFilenamePattern() {
        MonthlyMeanParameters parameters = getGlobalMonthyMeanParameters("x", "test");
        parameters.setVariable("tas");

        String filenamePattern = fileHandler.getFilenamePattern(parameters);

        assertThat(filenamePattern, is("tas_A1.X_*.nc"));
    }

    private MonthlyMeanParameters getGlobalMonthyMeanParameters(String scenarioName, String ensembleName) {
        MonthlyMeanParameters parameters = new MonthlyMeanParameters();
        parameters.setScenario(scenarioName);
        parameters.setScale(Resolution.GLOBAL);
        parameters.setEnsemble(new RunMember(ensembleName));

        return parameters;
    }

    private MonthlyMeanParameters getDownscaledMonthyMeanParameters(String scenarioName, String ensembleName) {
        MonthlyMeanParameters parameters = new MonthlyMeanParameters();
        parameters.setScenario(scenarioName);
        parameters.setScale(Resolution.DOWNSCALED);
        parameters.setEnsemble(new RunMember(ensembleName));

        return parameters;
    }

}