package io.tornadofaces.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Functions {

	public static <T> List<T> iteratorToList(Iterator<T> iterator) {
		ArrayList<T> list = new ArrayList<>();
		while (iterator.hasNext())
			list.add(iterator.next());
		return list;
	}
}
