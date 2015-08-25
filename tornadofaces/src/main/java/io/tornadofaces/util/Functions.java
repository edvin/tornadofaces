package io.tornadofaces.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
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

	public static Long localDateToMillis(LocalDate date) {
		return localDateTimeToMillis(date.atStartOfDay());
	}

	public static Long localDateTimeToMillis(LocalDateTime date) {
		return date.toEpochSecond(ZoneOffset.UTC) * 1000;
	}

	public static Long localDateTimeToMillisAtStartOfDay(LocalDateTime date) {
		return localDateToMillis(date.toLocalDate());
	}
}
