package io.tornadofaces.component.slider;

import java.util.Objects;

public class SliderRangeValue<T extends Number> {
	private Integer pct;
	private T value;
	private T increment;

	public SliderRangeValue() {
	}

	public SliderRangeValue(Integer pct, T value) {
		this.pct = pct;
		this.value = value;
	}

	public SliderRangeValue(Integer pct, T value, T increment) {
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
		SliderRangeValue<?> that = (SliderRangeValue<?>) o;
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

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public T getIncrement() {
		return increment;
	}

	public void setIncrement(T increment) {
		this.increment = increment;
	}
}