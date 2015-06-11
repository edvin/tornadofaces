package io.tornadofaces.component.data;

import io.tornadofaces.component.util.ComponentUtils;
import io.tornadofaces.json.JSONArray;
import io.tornadofaces.json.JSONObject;
import io.tornadofaces.util.WidgetBuilder;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;
import java.io.IOException;
import java.util.Collection;

@FacesRenderer(rendererType = DataRenderer.RENDERER_TYPE, componentFamily = "io.tornadofaces.component")
public class DataRenderer extends Renderer {
	public static final String RENDERER_TYPE = "io.tornadofaces.component.DataRenderer";

	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		Data data = (Data) component;
		ResponseWriter writer = context.getResponseWriter();

		String var = data.getVar();

		writer.startElement("script", data);
		writer.writeAttribute("id", data.getClientId(context), null);

		if (var != null && (!data.getLazy() || context.isPostback())) {
			Object value = data.getValue();
			if (value != null) {
				if (data.getJson()) {
					if (value instanceof Collection) {
						JSONArray array = new JSONArray();
						((Collection) value).stream().map(v -> new JSONObject(v, true)).forEach(array::put);
						value = array;
					} else if (value instanceof Object[]) {
						JSONArray array = new JSONArray();
						Object[] values = (Object[]) value;
						for (Object v : values)
							array.put(new JSONObject(v, true));
						value = array;
					} else {
						value = new JSONObject(value, true);
					}
				}

				String startWithVar = var.contains(".") ? "" : "var ";
				writer.write(startWithVar + var + " = " + value.toString() + ";");

				// Success callback
				if (data.getSuccess() != null)
					writer.write(" " + data.getSuccess() + "(" + var + ");");
			}
		}

		writer.endElement("script");

		if (data.getWidgetVar() != null) {
			ComponentUtils.reportIfMissingFormParent(component, context);

			new WidgetBuilder(context, data)
				.init()
				.attr("var", var)
				.attr("lazy", data.getLazy())
				.finish();
		}
	}
}