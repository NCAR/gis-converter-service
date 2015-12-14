package ucar.ral.gis.services.messages;

import edu.ucar.gis.ipcc.*;
import ucar.ral.gis.services.netcdf2shapefile.rest.longterm.LongTermAverageParameters;

import java.io.OutputStream;

public class LongTermAverageConversionRequestImpl extends AbstractConversionRequestImpl implements ConversionRequestMessage, ConversionRequest {

    public LongTermAverageConversionRequestImpl(LongTermAverageParameters productRequest, OutputStream outputStream) {
        super();
        this.productRequest = productRequest;
        this.conversionOutput = new ConversionOutput(outputStream);
    }

    public TimeConstraint getTimeConstraint() {

        LongTermAverageParameters parameters = (LongTermAverageParameters) this.productRequest;

        TimeConstraint result = null;

        if (parameters.getPeriod().equalsIgnoreCase("monthly")) {
            result = getTimeConstraintForMonthlyPeriod(parameters);
        } else if (parameters.getPeriod().equalsIgnoreCase("annual")) {
            result = getTimeConstraintForAnnualPeriod(parameters);
        } else if (parameters.getPeriod().equalsIgnoreCase("seasonal")) {
            result = getTimeConstraintForSeasonalPeriod(parameters);
        }

        return result;
    }

    private TimeConstraint getTimeConstraintForMonthlyPeriod(LongTermAverageParameters parameters) {
        if (yearRangeSpecified(parameters)) {
            return new YearMonthTimeConstraint(parameters.getStartYear(), parameters.getEndYear(), this.getMonthIndex(parameters.getMonth()));
        } else {
            return new MonthTimeConstraint(this.getMonthIndex(parameters.getMonth()));
        }
    }

    private TimeConstraint getTimeConstraintForAnnualPeriod(LongTermAverageParameters parameters) {
        if (yearRangeSpecified(parameters)) {
            return new YearTimeConstraint(parameters.getStartYear(), parameters.getEndYear());
        } else {
            return new AllTimesConstraint();
        }
    }

    private TimeConstraint getTimeConstraintForSeasonalPeriod(LongTermAverageParameters parameters) {
        if (yearRangeSpecified(parameters)) {
            return new YearMonthTimeConstraint(parameters.getStartYear(), parameters.getEndYear(), this.getMonthIndexForSeason(parameters.getSeason()));
        } else {
            return new MonthTimeConstraint(this.getMonthIndexForSeason(parameters.getSeason()));
        }
    }

    private boolean yearRangeSpecified(LongTermAverageParameters parameters) {
        return parameters.getStartYear() != null && parameters.getEndYear() != null;
    }
}
