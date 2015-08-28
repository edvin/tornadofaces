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

@FacesRenderer(rendererType = RadialProgressRenderer.RENDERER_TYPE, componentFamily = ComponentUtils.COMPONENT_FAMILY)
public class RadialProgressRenderer extends CoreRenderer {
	public static final String RENDERER_TYPE = "io.tornadofaces.component.RadialProgressRenderer";

	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		RadialProgress progress = (RadialProgress) component;

		writer.startElement("div", progress);
		writer.writeAttribute("id", component.getClientId(context), null);
		writer.endElement("div");
	}

	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		RadialProgress progress = (RadialProgress) component;

		new WidgetBuilder(context, progress).init()
			.nativeAttr("onClick", progress.getOnClick())
			.nativeAttr("diameter", progress.getDiameter().toString())
			.nativeAttr("min", progress.getMin().toString())
			.nativeAttr("max", progress.getMax().toString())
			.nativeAttr("value", progress.getValue().toString())
			.attr("label", progress.getLabel())
			.finish();
	}

}