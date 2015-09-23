package ucar.ral.gis.services.pipeline.conversion.netcdf.sourcefile;

import ucar.ral.gis.services.netcdf2shapefile.rest.BaseParameters;

import java.util.Map;

public abstract class AbstractSourceFileHandler implements SourceFileHandler {

	private SourceFileHandler nextHandler;
	private Map<String, String> ar4ScenarioDirectoryMap;

	public AbstractSourceFileHandler(SourceFileHandler nextHandler, Map<String, String> ar4ScenarioDirectoryMap) {
		super();
		this.nextHandler = nextHandler;
		this.ar4ScenarioDirectoryMap = ar4ScenarioDirectoryMap;
	}

	public FileSpecification resolveSourceFile(BaseParameters baseParameters) {

		if (this.canHandle(baseParameters)) {
			return this.getFileSpecification(baseParameters);
		} 
		else {
			return this.nextHandler.resolveSourceFile(baseParameters);
		}
	}

	protected boolean isAR4Scenario(BaseParameters parameters) {
		return this.ar4ScenarioDirectoryMap.containsKey(parameters.getScenario().toUpperCase());
	}

	protected String getScenarioMapping(BaseParameters parameters) {
		return this.ar4ScenarioDirectoryMap.get(parameters.getScenario().toUpperCase());
	}

	protected abstract boolean canHandle(BaseParameters baseParameters);

	protected abstract FileSpecification getFileSpecification(BaseParameters baseParameters);
}
