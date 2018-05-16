package edu.ucar.gis.ipcc;

public class Longitude extends Coordinate {

	/**
	 * @param value
	 */
	public Longitude(double value) {
		super(fixLon(value));
	}

	
	
	@Override
	public void setValue(final double value) {
		this.value = fixLon(value);
	}
	
	/** Utility method to reduce longitudes to the range -180, 180 */
	private static double fixLon(double lon) {
		if (lon > 180)
			lon = -1 * (360 - lon);
		return lon;
	} // fixLon
}
