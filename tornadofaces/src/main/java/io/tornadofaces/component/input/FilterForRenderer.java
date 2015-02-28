package io.tornadofaces.component.input;

import io.tornadofaces.component.CoreRenderer;
import io.tornadofaces.component.util.ComponentUtils;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import java.io.IOException;

@FacesRenderer(rendererType = FilterForRenderer.RENDERER_TYPE, componentFamily = ComponentUtils.COMPONENT_FAMILY)
public class FilterForRenderer extends CoreRenderer {
	public static final String RENDERER_TYPE = "io.tornadofaces.component.FilterForRenderer";

	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
		FilterFor ff = (FilterFor) component;

		String targetName = ff.getTarget();
		UIComponent target = ComponentUtils.findComponent(context, component, targetName);

		String widgetName = ff.getWidget();
		if (target == null)
			throw new RuntimeException(String.format("Could not find component with id %s for use with widget %s", targetName, widgetName));

		String targetId = ComponentUtils.escapeClientId(target.getClientId());

		// Add a filter function that will be called onkeyup as well as document load
		// The script will execute on load and then remove itself
		ResponseWriter writer = context.getResponseWriter();
		writer.startElement("script", ff);
		writer.writeAttribute("id", ff.getClientId(), null);

		String scriptId = ComponentUtils.escapeClientId(ff.getClientId());
		
		String filterFn = "filter_" + widgetName;
		writer.write("function " + filterFn + "() { TW('" + widgetName + "').filter($('" + targetId + "').val()); }\n");
		writer.write("$(function() { " + filterFn + "(); $('" + targetId + "').on('keyup', " + filterFn + "); $('#" + scriptId + "').remove(); });");
		writer.endElement("script");
	}
}
