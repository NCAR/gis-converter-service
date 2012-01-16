package ucar.ral.gis.services;

public enum OutputType {
	
	SHAPE("shp"), TEXT("txt");
	
	private String fileExtension;

	private OutputType(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	public String getFileExtension() {
		return fileExtension;
	}
	
}
