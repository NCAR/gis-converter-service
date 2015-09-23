package ucar.ral.gis.services.pipeline.conversion.netcdf.sourcefile;

import org.junit.Before;
import org.junit.Test;
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
}
