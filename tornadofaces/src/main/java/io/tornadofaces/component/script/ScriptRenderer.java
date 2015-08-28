package io.tornadofaces.component.script;

import io.tornadofaces.component.util.ComponentUtils;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;
import java.io.IOException;

@FacesRenderer(rendererType = ScriptRenderer.RENDERER_TYPE, componentFamily = ComponentUtils.COMPONENT_FAMILY)
public class ScriptRenderer extends Renderer {
	public static final String RENDERER_TYPE = "io.tornadofaces.component.ScriptRenderer";

	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
		Script script = (Script) component;
		ResponseWriter writer = context.getResponseWriter();

		writer.startElement("script", script);
		writer.writeAttribute("id", script.getClientId(), null);
	}

	public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
		Script script = (Script) component;
		ResponseWriter writer = context.getResponseWriter();

		String value = script.getValue();

		if (value != null && !"".equals(value)) {
			writer.write(value);
		} else {
			for (UIComponent child : component.getChildren())
				child.encodeAll(context);
		}

		writer.endElement("script");
	}

	public boolean getRendersChildren() {
		return true;
	}

	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		writer.endElement("script");
	}
}
