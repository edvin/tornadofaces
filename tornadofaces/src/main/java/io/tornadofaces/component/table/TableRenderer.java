package io.tornadofaces.component.table;

import io.tornadofaces.component.list.List;
import io.tornadofaces.component.util.ComponentUtils;
import io.tornadofaces.component.util.StyleClass;
import io.tornadofaces.util.WidgetBuilder;

import javax.faces.application.ResourceDependency;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;
import java.io.IOException;

@ResourceDependency(library = "tornadofaces", name = "table.js")
@FacesRenderer(rendererType = TableRenderer.RENDERER_TYPE, componentFamily = ComponentUtils.COMPONENT_FAMILY)
public class TableRenderer extends Renderer {
	public static final String RENDERER_TYPE = "io.tornadofaces.component.TableRenderer";

	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
		if (!component.isRendered())
			return;

		ResponseWriter writer = context.getResponseWriter();
		Table table = (Table) component;
		writer.startElement("table", table);
		writer.writeAttribute("id", table.getClientId(), null);
		writer.writeAttribute("widgetVar", table.getWidgetVar(), null);
		StyleClass.of(table.getStyleClass()).write(writer);

		String style = table.getStyle();
		if (style != null)
			writer.writeAttribute("style", style, null);

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