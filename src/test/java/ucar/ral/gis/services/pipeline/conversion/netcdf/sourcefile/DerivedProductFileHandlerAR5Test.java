package ucar.ral.gis.services.pipeline.conversion.netcdf.sourcefile;

import org.junit.Before;
import org.junit.Test;
import ucar.ral.gis.services.TemporalResolution;
import ucar.ral.gis.services.netcdf2shapefile.rest.BaseParameters;
import ucar.ral.gis.services.netcdf2shapefile.rest.annual.AnnualMeanParameters;
import ucar.ral.gis.services.netcdf2shapefile.rest.longterm.LongTermAverageParameters;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class DerivedProductFileHandlerAR5Test {

    private DerivedProductFileHandlerAR5 fileHandler;

    @Before
    public void setup() {
        fileHandler = new DerivedProductFileHandlerAR5(null, new File("/"), getAR4ScenarioMap());
    }

    private Map<String, String> getAR4ScenarioMap() {
        Map<String, String> ar4ScenarioMap =  new HashMap<String, String>();
        ar4ScenarioMap.put("SCENARIO", "Scenario");
        return ar4ScenarioMap;
    }

    @Test
    public void testCanHandleNonAR5ScenarioWithAnnualMeanParameters() {
        AnnualMeanParameters parameters = AnnualMeanParametersBuilder.getScenario("scenario");
        assertThat(fileHandler.canHandle(parameters), is(false));
    }

    @Test
    public void testCanHandleAR5ScenarioWithAnnualMeanParameters() {
        AnnualMeanParameters parameters = AnnualMeanParametersBuilder.getScenario("unmapped");
        assertThat(fileHandler.canHandle(parameters), is(true));
    }

    @Test
    public void testCanHandleNonAR5ScenarioWithLongTermAverageParameters() {
        LongTermAverageParameters parameters = LongTermAverageParametersBuilder.getScenario("scenario");
        assertThat(fileHandler.canHandle(parameters), is(false));
    }

    @Test
    public void testCanHandleAR5ScenarioWithLongTermAverageParameters() {
        LongTermAverageParameters parameters = LongTermAverageParametersBuilder.getScenario("unmapped");
        assertThat(fileHandler.canHandle(parameters), is(true));
    }

    @Test
    public void testGlobalDirectory() {
        BaseParameters parameters = BaseParametersBuilder.getGlobal();

        FileSpecification result = fileHandler.getFileSpecification(parameters);

        assertThat(result.getDirectory().getAbsolutePath(), is("/ar5/CCSM/globalproducts"));
    }

    @Test
    public void testDownscaledDirectory() {
        BaseParameters parameters = BaseParametersBuilder.getDownscaled();

        FileSpecification result = fileHandler.getFileSpecification(parameters);

        assertThat(result.getDirectory().getAbsolutePath(), is("/ar5/CCSM/downproducts"));
    }

    @Test
    public void testAnnualMeanFilenamePattern() {
        BaseParameters parameters = BaseParametersBuilder.getGlobal("rcp00", TemporalResolution.ANNUAL_MEAN);

        FileSpecification result = fileHandler.getFileSpecification(parameters);

        assertThat(result.getFilenamePattern(), is("tas_Amon_CCSM4_rcp00_annual_*.nc"));
    }

    @Test
    public void testLongTermAverageMonthlyFilenamePattern() { // LongTermAverage synonymous with 20 Year Mean
        LongTermAverageParameters parameters = LongTermAverageParametersBuilder.getGlobalTas("rcp00", "monthly");

        FileSpecification result = fileHandler.getFileSpecification(parameters);

        assertThat(result.getFilenamePattern(), is("tas240m_Amon_CCSM4_rcp00_monthly_*.nc"));
    }

    @Test
    public void testLongTermAverageAnnualFilenamePattern() { // LongTermAverage synonymous with 20 Year Mean
        LongTermAverageParameters parameters = LongTermAverageParametersBuilder.getGlobalTas("rcp00", "annual");

        FileSpecification result = fileHandler.getFileSpecification(parameters);

        assertThat(result.getFilenamePattern(), is("tas20y_Amon_CCSM4_rcp00_annual_*.nc"));
    }

    @Test
    public void testAnomalyMonthlyFilenamePattern() {
        LongTermAverageParameters parameters = LongTermAverageParametersBuilder.getGlobalTas("rcp00", "monthly");
        parameters.setTemporalResolution(TemporalResolution.CLIMATE_ANOMOLY);

        FileSpecification result = fileHandler.getFileSpecification(parameters);

        assertThat(result.getFilenamePattern(), is("atas240m_Amon_CCSM4_rcp00_monthly_*.nc"));
    }

    @Test
    public void testAnomalyAnnualFilenamePattern() {
        LongTermAverageParameters parameters = LongTermAverageParametersBuilder.getGlobalTas("rcp00", "annual");
        parameters.setTemporalResolution(TemporalResolution.CLIMATE_ANOMOLY);

        FileSpecification result = fileHandler.getFileSpecification(parameters);

        assertThat(result.getFilenamePattern(), is("atas20y_Amon_CCSM4_rcp00_annual_*.nc"));
    }
}
