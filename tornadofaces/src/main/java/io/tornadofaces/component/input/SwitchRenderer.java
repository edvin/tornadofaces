package io.tornadofaces.component.input;

import io.tornadofaces.component.util.ComponentUtils;
import io.tornadofaces.component.util.StyleClass;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;
import java.io.IOException;

@FacesRenderer(rendererType = SwitchRenderer.RENDERER_TYPE, componentFamily = ComponentUtils.COMPONENT_FAMILY)
public class SwitchRenderer extends Renderer {
	public static final String RENDERER_TYPE = "io.tornadofaces.component.SwitchRenderer";

	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		writer.startElement("div", component);
		writer.writeAttribute("id", component.getClientId(), null);
		Switch switchComponent = (Switch) component;
		StyleClass.of(switchComponent.getSize()).add("switch").write(writer);
	}

	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		writer.startElement("label", component);

		Switch switchComponent = (Switch) component;

		writer.writeAttribute("for", switchComponent.getCheckbox().getClientId(context), null);
		writer.endElement("label");
		writer.endElement("div");
	}
}