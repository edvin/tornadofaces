package io.tornadofaces.component.table;

import io.tornadofaces.component.column.Column;
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
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.String.format;

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
		StyleClass
			.of(table.getStyleClass())
			.add("table-reflow", table.getReflow())
			.add("table-zebra", table.getZebra())
			.add("table-compact", table.getCompact())
			.add("table-bordered", table.getBordered())
			.add("table-reflow--block", Table.ReflowMode.block.equals(table.getReflowMode()))
			.write(writer);

		String style = table.getStyle();
		if (style != null)
			writer.writeAttribute("style", style, null);

		if (table.getReflow())
			renderReflowStylesheet(writer, table);

		renderHead(context, writer, table);
	}

	private void renderHead(FacesContext context, ResponseWriter writer, Table table) throws IOException {
		writer.startElement("thead", table);
		writer.startElement("tr", table);

		for (UIComponent child : table.getChildren()) {
			if (child instanceof Column) {
				Column column = (Column) child;
				writer.startElement("th", table);
				StyleClass.of(column.getHeaderClass()).write(writer);

				String headerText = column.getHeaderText();
				if (headerText != null) {
					writer.writeText(headerText, null);
				} else {
					UIComponent header = column.getHeader();
					if (header != null)
						header.encodeAll(context);
				}

				writer.endElement("th");
			}
		}

		writer.endElement("tr");
		writer.endElement("thead");
	}

	private void renderFooter(FacesContext context, ResponseWriter writer, Table table) throws IOException {
		long footCount = table.getChildren().stream().map(c -> (Column) c).filter(c -> c.getFootertext() != null).count();
		if (footCount == 0)
			return;

		writer.startElement("tfoot", table);
		writer.startElement("tr", table);

		for (UIComponent child : table.getChildren()) {
			if (child instanceof Column) {
				Column column = (Column) child;
				writer.startElement("td", table);
				StyleClass.of(column.getFooterClass()).write(writer);
				String footerText = column.getFootertext();
				if (footerText != null) {
					writer.writeText(footerText, "footer");
				} else {
					UIComponent footer = column.getFooter();
					if (footer != null)
						footer.encodeAll(context);
				}

				writer.endElement("td");
			}
		}

		writer.endElement("tr");
		writer.endElement("tfoot");
	}

	private void renderReflowStylesheet(ResponseWriter writer, Table table) throws IOException {
		final AtomicInteger colcount = new AtomicInteger();

		writer.startElement("style", table);

		StringBuilder styles = new StringBuilder();
		String tableId = ComponentUtils.escapeClientIdForCss(table.getClientId());

		table.getChildren()
			.stream()
			.filter(c -> c instanceof Column)
			.map(c -> (Column) c)
			.forEach(column -> {
				Integer colno = colcount.addAndGet(1);
				if (Boolean.TRUE.equals(column.getReflow()))
					styles.append(format("%s tbody tr td:nth-child(%s):before { content: '%s'; }\n",
						tableId, colno, column.getHeaderText()));
			});

		writer.write(styles.toString());
		writer.endElement("style");
	}

	public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		Table table = (Table) component;
		int rowCount = table.getRowCount();

		writer.startElement("tbody", table);

		boolean reflow = table.getReflow();

		for (int i = 0; i < rowCount; i++) {
			table.setRowIndex(i);
			writer.startElement("tr", table);
			for (UIComponent child : component.getChildren()) {
				if (child instanceof Column) {
					Column column = (Column) child;
					writer.startElement("td", column);
					StyleClass.of(column.getStyleClass()).write(writer);

					if (reflow)
						writer.startElement("span", column);

					Object text = column.getText();

					if (text != null) {
						if (column.getEscape())
							writer.writeText(text, null);
						else
							writer.write(text.toString());
					} else {
						column.encodeChildren(context);
					}

					if (reflow)
						writer.endElement("span");

					writer.endElement("td");
				}
			}
			writer.endElement("tr");
		}

		writer.endElement("tbody");
	}

	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		Table table = (Table) component;

		renderFooter(context, writer, table);

		writer.endElement("table");

		table.setRowIndex(-1);
		new WidgetBuilder(context, table)
			.init()
			.attr("filterFn", table.getFilterFn())
			.attr("highlightFilter", table.getHighlightFilter())
			.finish();
	}

	public boolean getRendersChildren() {
		return true;
	}
}