package ucar.ral.gis.services.messages;

import edu.ucar.gis.ipcc.*;
import org.junit.Before;
import org.junit.Test;
import ucar.ral.gis.services.netcdf2shapefile.rest.longterm.LongTermAverageParameters;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class LongTermAverageConversionRequestImplTest {

    private LongTermAverageParameters parameters;
    private LongTermAverageConversionRequestImpl request;

    @Before
    public void setup() {
        parameters = new LongTermAverageParameters();
        request = new LongTermAverageConversionRequestImpl(parameters, null);
    }

    @Test
    public void givenMonthlyPeriod_timeConstraintInstanceOfMonthTimeConstraint() {
        parameters.setPeriod("monthly");
        parameters.setMonth("january");

        TimeConstraint timeConstraint = request.getTimeConstraint();

        assertThat(timeConstraint instanceof MonthTimeConstraint, is(true));
    }

    @Test
    public void givenAnnualPeriod_timeConstraintInstanceOfAllTimesConstraint() {
        parameters.setPeriod("annual");

        TimeConstraint timeConstraint = request.getTimeConstraint();

        assertThat(timeConstraint instanceof AllTimesConstraint, is(true));
    }

    @Test
    public void givenSeasonalPeriod_timeConstraintInstanceOfMonthTimeConstraint() {
        parameters.setPeriod("seasonal");
        parameters.setSeason("winter");

        TimeConstraint timeConstraint = request.getTimeConstraint();

        assertThat(timeConstraint instanceof MonthTimeConstraint, is(true));
    }

    @Test
    public void givenMonthlyPeriodAndStartEndYear_timeConstraintInstanceOfYearMonthTimeConstraint() {
        parameters.setPeriod("monthly");
        parameters.setMonth("january");
        parameters.setStartYear(2000);
        parameters.setEndYear(2010);

        TimeConstraint timeConstraint = request.getTimeConstraint();

        assertThat(timeConstraint instanceof YearMonthTimeConstraint, is(true));
    }

    @Test
    public void givenAnnualPeriodAndStartEndYear_timeConstraintInstanceOfYearTimeConstraint() {
        parameters.setPeriod("annual");
        parameters.setStartYear(2000);
        parameters.setEndYear(2010);

        TimeConstraint timeConstraint = request.getTimeConstraint();

        assertThat(timeConstraint instanceof YearTimeConstraint, is(true));
    }

    @Test
    public void givenSeasonalPeriodAndStartEndYear_timeConstraintInstanceOfMonthTimeConstraint() {
        parameters.setPeriod("seasonal");
        parameters.setSeason("winter");
        parameters.setStartYear(2000);
        parameters.setEndYear(2010);

        TimeConstraint timeConstraint = request.getTimeConstraint();

        assertThat(timeConstraint instanceof YearMonthTimeConstraint, is(true));
    }
}
