package io.tornadofaces.component.list;

import io.tornadofaces.component.util.ComponentUtils;
import io.tornadofaces.component.util.StyleClass;
import io.tornadofaces.util.WidgetBuilder;

import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;
import java.io.IOException;

@ResourceDependencies({
	@ResourceDependency(library = "javax.faces", name = "jsf.js"),
	@ResourceDependency(library = "tornadofaces", name = "jquery.min.js"),
	@ResourceDependency(library = "tornadofaces", name = "tornadofaces.js"),
	@ResourceDependency(library = "tornadofaces", name = "foundation.css"),
	@ResourceDependency(library = "tornadofaces", name = "tornadofaces.css"),
	@ResourceDependency(library = "tornadofaces", name = "list.js")
})
@FacesRenderer(rendererType = ListRenderer.RENDERER_TYPE, componentFamily = ComponentUtils.COMPONENT_FAMILY)
public class ListRenderer extends Renderer {
	public static final String RENDERER_TYPE = "io.tornadofaces.component.ListRenderer";

	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
		if (!component.isRendered())
			return;

		ResponseWriter writer = context.getResponseWriter();
		List list = (List) component;
		writer.startElement("section", list);
		writer.writeAttribute("id", list.getClientId(), null);
		writer.writeAttribute("widgetVar", list.getWidgetVar(), null);
		StyleClass.of("block-list").add(list.getStyleClass()).write(writer);

		String title = list.getTitle();
		if (title != null) {
			writer.startElement("header", list);
			writer.write(title);
			writer.endElement("header");
		}

		writer.startElement("ul", list);
	}

	public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
		List list = (List) component;
		int rowCount = list.getRowCount();

		for (int i = 0; i < rowCount; i++) {
			list.setRowIndex(i);
			for (UIComponent child : component.getChildren())
				child.encodeAll(context);
		}
	}

	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		writer.endElement("ul");
		writer.endElement("section");

		List list = (List) component;
		list.setRowIndex(-1); // Make sure we output the clientId of the actual list container
		new WidgetBuilder(context, list)
			.init()
			.attr("filterFn", list.getFilterFn())
			.attr("highlightFilter", list.getHighlightFilter())
			.finish();
	}

	public boolean getRendersChildren() {
		return true;
	}
}