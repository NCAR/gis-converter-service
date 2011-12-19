package ucar.ral.gis.services.web;

import ucar.ral.gis.services.EnsembleMember;
import ucar.ral.gis.services.Scale;


public class ProductRequest {
	
	private Scale product;
	private String variable;
	private String modelSim;
	private EnsembleMember ensemble;
	
	public ProductRequest(Scale product, String variable, String modelSim,
			EnsembleMember ensemble) {
		super();
		this.product = product;
		this.variable = variable;
		this.modelSim = modelSim;
		this.ensemble = ensemble;
	}

	public Scale getProduct() {
		return product;
	}

	public String getVariable() {
		return variable;
	}

	public String getModelSim() {
		return modelSim;
	}

	public EnsembleMember getEnsemble() {
		return ensemble;
	}
	
}
