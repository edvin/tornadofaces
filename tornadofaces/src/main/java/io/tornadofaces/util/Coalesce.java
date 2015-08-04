package io.tornadofaces.util;

import java.util.Arrays;

public class Coalesce {
	public static <T> T coalesce(T... values) {
		for (T v : values)
			if (v != null)
				return v;
		return null;
	}

	public static String coalesceString(Object v1, Object v2) {
		for (Object v : Arrays.asList(v1, v2))
			if (v != null && !"".equals(v.toString()))
				return v.toString();
		return null;
	}
}
