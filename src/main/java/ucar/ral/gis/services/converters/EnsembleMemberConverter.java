package ucar.ral.gis.services.converters;

import org.springframework.core.convert.converter.Converter;

import ucar.ral.gis.services.EnsembleAverage;
import ucar.ral.gis.services.EnsembleMember;
import ucar.ral.gis.services.RunMember;

public class EnsembleMemberConverter implements Converter<String, EnsembleMember> {
	
	private String esembleAverageName = "average";

	public EnsembleMember convert(String source) {
		
		EnsembleMember result;
		
		if ("ea".equalsIgnoreCase(source)) {
			result = new EnsembleAverage(esembleAverageName);
		}
		else {
			result = new RunMember(source);
		}
		
		return result;
	}

}
