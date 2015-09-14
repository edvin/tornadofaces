package io.tornadofaces.component.slider;

import java.util.Objects;

public class SliderRangeValue {
	private Integer pct;
	private Integer value;
	private Integer increment;

	public SliderRangeValue() {
	}

	public SliderRangeValue(Integer pct, Integer value) {
		this.pct = pct;
		this.value = value;
	}

	public SliderRangeValue(Integer pct, Integer value, Integer increment) {
		this.pct = pct;
		this.value = value;
		this.increment = increment;
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("'").append(pct).append("%': [ ").append(value);
		if (increment != null)
			s.append(", ").append(increment);
		s.append(" ]");
		return s.toString();
	}

	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		SliderRangeValue that = (SliderRangeValue) o;
		return Objects.equals(pct, that.pct);
	}

	public int hashCode() {
		return Objects.hash(pct);
	}

	public Integer getPct() {
		return pct;
	}

	public void setPct(Integer pct) {
		this.pct = pct;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public Integer getIncrement() {
		return increment;
	}

	public void setIncrement(Integer increment) {
		this.increment = increment;
	}
}