package io.tornadofaces.component.chart;

import io.tornadofaces.component.CoreRenderer;
import io.tornadofaces.component.util.ComponentUtils;
import io.tornadofaces.component.util.StyleClass;
import io.tornadofaces.json.JSONArray;
import io.tornadofaces.json.JSONObject;
import io.tornadofaces.util.WidgetBuilder;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import java.io.IOException;

@FacesRenderer(rendererType = LineChartRenderer.RENDERER_TYPE, componentFamily = ComponentUtils.COMPONENT_FAMILY)
public class LineChartRenderer extends CoreRenderer {
	public static final String RENDERER_TYPE = "io.tornadofaces.component.LineChartRenderer";

	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		LineChart chart = (LineChart) component;

		writer.startElement("div", chart);
		writer.writeAttribute("id", component.getClientId(context), null);

		StyleClass.of(chart.getStyleClass()).write(writer);

		writer.startElement("svg", chart);
		String style = chart.getStyle();
		if (style != null)
			writer.writeAttribute("style", style, null);

		writer.endElement("svg");

		writer.endElement("div");
	}

	public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
		LineChart chart = (LineChart) component;

		WidgetBuilder builder = new WidgetBuilder(context, chart).init();
		builder.attr("xLabel", chart.getxLabel());
		builder.nativeAttr("xTickFormat", chart.getxTickFormat());
		builder.attr("yLabel", chart.getyLabel());
		builder.nativeAttr("yTickFormat", chart.getyTickFormat());

		JSONArray series = new JSONArray();

		for (UIComponent child : component.getChildren()) {
			if (child instanceof Serie) {
				Serie serie = (Serie) child;
				if (!serie.isRendered())
					continue;

				JSONObject jserie = new JSONObject();
				jserie.put("key", serie.getName());

				String color = serie.getColor();
				if (color != null)
					jserie.put("color", color);

				Boolean area = serie.getArea();
				if (area != null)
					jserie.put("area", area);

				JSONArray values = new JSONArray();

				int rowCount = serie.getRowCount();
				for (int i = 0; i < rowCount; i++) {
					serie.setRowIndex(i);

					JSONObject e = new JSONObject();
					e.put("x", serie.getX());
					e.put("y", serie.getY());
					values.put(e);
				}

				jserie.put("values", values);
				series.put(jserie);
			}
		}

		builder.nativeAttr("datum", series.toString());
		builder.nativeAttr("beforeConfig", chart.getBeforeConfig());

		builder.finish();
	}

	public boolean getRendersChildren() {
		return true;
	}

	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
	}

}