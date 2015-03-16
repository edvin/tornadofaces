package io.tornadofaces.component.sidemenu;

import io.tornadofaces.component.CoreRenderer;
import io.tornadofaces.component.util.ComponentUtils;
import io.tornadofaces.component.util.StyleClass;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import java.io.IOException;

@FacesRenderer(rendererType = SidemenuRenderer.RENDERER_TYPE, componentFamily = ComponentUtils.COMPONENT_FAMILY)
public class SidemenuRenderer extends CoreRenderer {
	public static final String RENDERER_TYPE = "io.tornadofaces.component.SidebarRenderer";

	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
		if (!component.isRendered())
			return;

		ResponseWriter writer = context.getResponseWriter();
		Sidemenu sidebar = (Sidemenu) component;
		writer.startElement("ul", sidebar);
		writer.writeAttribute("id", sidebar.getClientId(), null);

		String style = sidebar.getStyle();
		if (style != null)
			writer.writeAttribute("style", style, null);

		StyleClass
			.of("sidebar")
			.add(sidebar.getStyleClass())
			.add(sidebar.getColor())
			.add(sidebar.getPosition())
			.write(writer);
	}

	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		writer.endElement("ul");
	}

}