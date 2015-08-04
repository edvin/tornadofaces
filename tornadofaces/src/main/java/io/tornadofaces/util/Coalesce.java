package io.tornadofaces.util;

public class Coalesce {
	public static <T> T coalesce(T... values) {
		for (T v : values)
			if (v != null)
				return v;
		return null;
	}

	public static String coalesceString(Object... values) {
		for (Object v : values)
			if (v != null && !"".equals(v.toString()))
				return v.toString();
		return null;
	}
}
