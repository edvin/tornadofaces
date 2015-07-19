package io.tornadofaces.component.table;

import io.tornadofaces.component.CoreRenderer;
import io.tornadofaces.component.column.Column;
import io.tornadofaces.component.util.ComponentUtils;
import io.tornadofaces.component.util.StyleClass;
import io.tornadofaces.json.JSONArray;
import io.tornadofaces.json.JSONObject;
import io.tornadofaces.util.WidgetBuilder;

import javax.faces.application.ResourceDependency;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static io.tornadofaces.component.util.ComponentUtils.encodeAjaxBehaviors;
import static io.tornadofaces.component.util.ComponentUtils.getRequestParam;
import static java.lang.String.format;
import static java.util.Arrays.asList;

@ResourceDependency(library = "tornadofaces", name = "table.js")
@FacesRenderer(rendererType = TableRenderer.RENDERER_TYPE, componentFamily = ComponentUtils.COMPONENT_FAMILY)
public class TableRenderer extends CoreRenderer {
	public static final String RENDERER_TYPE = "io.tornadofaces.component.TableRenderer";

	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
		if (!component.isRendered())
			return;

		ResponseWriter writer = context.getResponseWriter();
		Table table = (Table) component;

		writer.startElement("table", table);
		writer.writeAttribute("id", table.getClientId(), null);
		Table.ReflowAtSize reflow = table.getReflow();
		StyleClass tableClass = StyleClass.of(table.getStyleClass())
			.add(reflow + "-table-reflow", !Table.ReflowAtSize.none.equals(reflow))
			.add("table-zebra", table.getZebra())
			.add("table-compact", table.getCompact())
			.add("table-bordered", table.getBordered())
			.add("table-reflow--block", Table.ReflowMode.block.equals(table.getReflowMode()));

		Table.RowSelectionMode selectionMode = table.getSelectionMode();
		if (selectionMode != null)
			tableClass.add("table-rowselect-" + selectionMode);

		tableClass.write(writer);

		String style = table.getStyle();
		if (style != null)
			writer.writeAttribute("style", style, null);

		if (!Table.ReflowAtSize.none.equals(table.getReflow()))
			renderReflowStylesheet(writer, table);

		renderHead(context, writer, table);
	}

	private void renderHead(FacesContext context, ResponseWriter writer, Table table) throws IOException {
		if (table.isEmpty())
			return;

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
		if (table.isEmpty())
			return;

		long footCount = table.getChildren().stream().filter(c -> c instanceof Column).map(c -> (Column) c).filter(c -> c.getFootertext() != null).count();
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
		Table table = (Table) component;
		ResponseWriter writer = context.getResponseWriter();

		if (table.isEmpty()) {
			writer.startElement("caption", table);
			writer.write(table.getEmptyText());
			writer.endElement("caption");
			return;
		}

		int rowCount = table.getRowCount();

		writer.startElement("tbody", table);

		table.updateSelectedRowKeys();

		Table.ReflowAtSize reflow = table.getReflow();

		String rowClassString = table.getRowClasses();
		List<String> rowClasses = rowClassString == null ? null : asList(rowClassString.split(","));
		int rowClassCount = rowClasses == null ? 0 : rowClasses.size();

		for (int i = 0; i < rowCount; i++) {
			table.setRowIndex(i);
			writer.startElement("tr", table);

			StyleClass rowStyleClasses = StyleClass.of("selected", table.isRowSelected());

			if (rowClasses != null)
				rowStyleClasses.add(rowClasses.get(i % rowClassCount));

			rowStyleClasses.write(writer);

			Object rowKey = table.getRowKey();
			if (rowKey != null)
				writer.writeAttribute("data-rk", rowKey, null);

			for (UIComponent child : component.getChildren()) {
				if (child instanceof Column) {
					Column column = (Column) child;
					writer.startElement("td", column);

					if (!ComponentUtils.isEmpty(column.getStyle()))
						writer.writeAttribute("style", column.getStyle(), null);

					StyleClass.of(column.getStyleClass())
						.add("cell-reflow--block", Table.ReflowMode.block.equals(column.getReflowMode()))
						.add("row-toggle-container", column.containsRowToggler())
						.write(writer);

					String link = column.getLink();
					if (link != null) {
						writer.startElement("a", column);
						writer.writeAttribute("href", link, null);
					}

					if (!Table.ReflowAtSize.none.equals(reflow))
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

					if (!Table.ReflowAtSize.none.equals(reflow))
						writer.endElement("span");

					if (link != null)
						writer.endElement("a");

					writer.endElement("td");
				}
			}
			writer.endElement("tr");
		}

		writer.endElement("tbody");

		renderRowExpansion(context, table);
	}

	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		Table table = (Table) component;

		renderFooter(context, writer, table);

		writer.endElement("table");

		Table.RowSelectionMode selectionMode = table.getSelectionMode();

		table.setRowIndex(-1);
		WidgetBuilder builder = new WidgetBuilder(context, table)
			.init()
			.attr("filterFn", table.getFilterFn())
			.attr("highlightFilter", table.getHighlightFilter())
			.attr("selectionMode", selectionMode != null ? selectionMode.toString() : null);

		JSONArray rowSelectBehaviors = encodeAjaxBehaviors(context, "rowSelect", table);

		if (rowSelectBehaviors != null) {
			JSONObject behaviors = new JSONObject();
			behaviors.put("rowSelect", rowSelectBehaviors);
			builder.nativeAttr("behaviors", behaviors.toString());
		}

		builder.finish();
	}

	private void renderRowExpansion(FacesContext context, Table table) throws IOException {
		Optional<RowExpansion> expansion = table.getChildren().stream().filter(c -> c instanceof RowExpansion).map(c -> ((RowExpansion) c)).findFirst();
		if (expansion.isPresent())
			expansion.get().encodeAll(context);
	}

	public boolean getRendersChildren() {
		return true;
	}

	public void decode(FacesContext context, UIComponent component) {
		Table table = (Table) component;

		String clientId = table.getClientId(context);

		// Update selection based on request parameter
		if (table.isSelectionEnabled()) {
			String selection = getRequestParam(context, clientId + "_selection");
			if (selection != null)
				table.updateSelection(selection);
		}

		super.decode(context, component);
	}
}