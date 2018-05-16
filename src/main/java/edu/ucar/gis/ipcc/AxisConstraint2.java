package edu.ucar.gis.ipcc;

public class AxisConstraint2<T extends Comparable> {

  private T min;
  private T max;
  private Integer step = 1;
  
	/**
	 * @param checkLonValues
	 */
	public AxisConstraint2() {
	}

	/**
	 * @param min
	 * @param max
	 * @param step
	 */
	public AxisConstraint2(T min, T max, Integer step) {
		this.min = min;
		this.max = max;
		this.step = step;
	}

	/**
	 * @return Returns the max.
	 */
	public T getMax() {
		return max;
	}
	
	/**
	 * @param max The max to set.
	 */
	public void setMax(T max) {
		this.max = max;
	}
	
	/**
	 * @return Returns the min.
	 */
	public T getMin() {
		return min;
	}
	
	/**
	 * @param min The min to set.
	 */
	public void setMin(T min) {
		this.min = min;
	}
	
	/**
	 * @return Returns the step.
	 */
	public Integer getStep() {
		return step;
	}
	
	/**
	 * @param step The step to set.
	 */
	public void setStep(Integer step) {
		this.step = step;
	}
	
	


} // Constraint
