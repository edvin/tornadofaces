package io.tornadofaces.component.util;

import io.tornadofaces.component.CoreRenderer;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import java.io.IOException;

@FacesRenderer(rendererType = FocusRenderer.RENDERER_TYPE, componentFamily = ComponentUtils.COMPONENT_FAMILY)
public class FocusRenderer extends CoreRenderer {
	public static final String RENDERER_TYPE = "io.tornadofaces.component.FocusRenderer";

	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
		if (!component.isRendered())
			return;

		Focus focus = (Focus) component;
		UIComponent target = ComponentUtils.findComponent(context, focus, focus.getTarget());

		if (target == null)
			throw new RuntimeException(String.format("Could not find component with id %s to focus", focus.getTarget()));

		String targetId = ComponentUtils.escapeClientId(target.getClientId());

		// Add a filter function that will be called onkeyup as well as document load
		// The script will execute on load and then remove itself
		ResponseWriter writer = context.getResponseWriter();
		writer.startElement("script", focus);
		writer.writeAttribute("id", focus.getClientId(), null);
		String scriptId = ComponentUtils.escapeClientId(focus.getClientId());
		writer.write("$(function() { $('" + targetId + "').focus();");
		if (focus.getRemove())
			writer.write(" $('" + scriptId + "').remove();");
		writer.write("});");
		writer.endElement("script");
	}

}
