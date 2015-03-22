package io.tornadofaces.component.table;

import io.tornadofaces.component.CoreRenderer;
import io.tornadofaces.component.util.ComponentUtils;
import io.tornadofaces.component.util.StyleClass;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import java.io.IOException;

import static io.tornadofaces.component.util.ComponentUtils.setRequestParam;
import static javax.faces.component.UINamingContainer.getSeparatorChar;

@FacesRenderer(rendererType = RowExpansionRenderer.RENDERER_TYPE, componentFamily = ComponentUtils.COMPONENT_FAMILY)
public class RowExpansionRenderer extends CoreRenderer {
	public static final String RENDERER_TYPE = "io.tornadofaces.component.RowExpansionRenderer";

	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		RowExpansion expansion = (RowExpansion) component;
		Table table = (Table) expansion.getParent();
		table.setRowIndex(-1);
		writer.startElement("tbody", expansion);
		writer.writeAttribute("id", expansion.getClientId(context), null);
		writer.writeAttribute("class", "expand", null);
		writer.startElement("tr", expansion);
		writer.writeAttribute("class", "table-expand-row", null);
		writer.startElement("td", expansion);
		writer.writeAttribute("colspan", table.getColumnCount(), null);
	}

	public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
		RowExpansion expansion = (RowExpansion) component;
		Table table = (Table) expansion.getParent();

		String rowKey = table.getRequestedExpandRowKey(context);
		if (rowKey != null) {
			Object rowData = table.getRowData(rowKey);
			String var = table.getVar();
			setRequestParam(context, var, rowData);
			for (UIComponent child : component.getChildren())
				child.encodeAll(context);
			setRequestParam(context, var, null);
		}
	}

	public boolean getRendersChildren() {
		return true;
	}

	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		writer.endElement("td");
		writer.endElement("tr");
		writer.endElement("tbody");
	}
}