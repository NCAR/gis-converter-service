package edu.ucar.gis.ipcc;

public class Coordinate implements Comparable<Coordinate> {

	protected double value;

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
	
	public Coordinate() {
		
	}
	
	/**
	 * @param value
	 */
	public Coordinate(double value) {
		this.value = value;
	}

	public int compareTo(Coordinate rhs) {
		if(this.value < rhs.value) {
			return 1;
		}
		else if(this.value >  rhs.value) {
			return -1;
		}
		return 0;
	}
	
}
