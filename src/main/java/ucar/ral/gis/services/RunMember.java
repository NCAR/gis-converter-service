package ucar.ral.gis.services;

public class RunMember extends EnsembleMember {
	
	private static final String PREFIX = "run";

	public RunMember(String name) {
		super(name);
	}
	
	@Override
	public String getName() {
		
		return PREFIX + super.getName();
	}

}
