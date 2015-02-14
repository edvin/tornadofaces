package io.tornadofaces.component.util;

import javax.faces.context.ResponseWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StyleClass {
	public List<String> classes = new ArrayList<>();

	public StyleClass() {
	}

	public StyleClass(String... styleClasses) {
		if (styleClasses != null)
			for (String s : styleClasses)
				add(s);
	}

	public static StyleClass of(String styleClasses) {
		return new StyleClass(styleClasses);
	}

	public static StyleClass of(Object styleClasses) {
		return new StyleClass(styleClasses != null ? styleClasses.toString() : null);
	}

	public static StyleClass of(String styleClasses, Boolean condition) {
		StyleClass styleClass = new StyleClass();
		styleClass.add(styleClasses, condition);
		return styleClass;
	}

	public StyleClass add(String styleClass, Boolean condition) {
		if (condition)
			add(styleClass);

		return this;
	}

	public StyleClass add(Object styleClass, Boolean condition) {
		if (styleClass != null && condition != null && condition)
			add(styleClass);

		return this;
	}

	public StyleClass remove(String styleClass, Boolean condition) {
		if (condition)
			remove(styleClass);

		return this;
	}

	public StyleClass add(String styleClass) {
		if (styleClass != null)
			for (String s : styleClass.split("\\s+"))
				if (!classes.contains(s))
					classes.add(s);

		return this;
	}

	public StyleClass add(Object styleClass) {
		if (styleClass != null)
			add(styleClass.toString());

		return this;
	}

	public StyleClass remove(String styleClass) {
		for (String s : styleClass.split("\\s+"))
			classes.remove(s);

		return this;
	}

	public void write(ResponseWriter writer) throws IOException {
		if (!classes.isEmpty())
			writer.writeAttribute("class", toString(), null);
	}

	public void write(ResponseWriter writer, String property) throws IOException {
		if (!classes.isEmpty())
			writer.writeAttribute("class", toString(), property);
	}

	public String toString() {
		return String.join(" ", classes);
	}
}
