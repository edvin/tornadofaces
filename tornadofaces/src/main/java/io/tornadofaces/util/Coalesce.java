package io.tornadofaces.util;

public class Coalesce {
	public static <T> T coalesce(T... values) {
		for (T v : values)
			if (v != null)
				return v;
		return null;
	}
}
