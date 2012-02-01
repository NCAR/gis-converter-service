package ucar.ral.gis.services.pipeline.conversion.netcdf.sourcefile;

import ucar.ral.gis.services.netcdf2shapefile.rest.BaseParameters;

public abstract class AbstractSourceFileHandler implements SourceFileHandler {

	private SourceFileHandler nextHandler;
	
	public AbstractSourceFileHandler(SourceFileHandler nextHandler) {
		super();
		this.nextHandler = nextHandler;
	}

	public FileSpecification resolveSourceFile(BaseParameters baseParameters) {

		if (this.canHandle(baseParameters)) {
			return this.getFileSpecification(baseParameters);
		} 
		else {
			return this.nextHandler.resolveSourceFile(baseParameters);
		}
	}
	
	protected abstract boolean canHandle(BaseParameters baseParameters);

	protected abstract FileSpecification getFileSpecification(BaseParameters baseParameters);
}
