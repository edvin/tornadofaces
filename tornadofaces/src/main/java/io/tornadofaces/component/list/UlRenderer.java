package io.tornadofaces.component.list;

import io.tornadofaces.component.util.ComponentUtils;
import io.tornadofaces.component.util.StyleClass;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;
import java.io.IOException;

@FacesRenderer(rendererType = UlRenderer.RENDERER_TYPE, componentFamily = ComponentUtils.COMPONENT_FAMILY)
public class UlRenderer extends Renderer {
	public static final String RENDERER_TYPE = "io.tornadofaces.component.UlRenderer";

	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
		if (!component.isRendered())
			return;

		ResponseWriter writer = context.getResponseWriter();
		Ul ul = (Ul) component;
		writer.startElement("ul", ul);
		writer.writeAttribute("id", ul.getClientId(), null);

		String style = ul.getStyle();
		if (style != null)
			writer.writeAttribute("style", style, null);

		StyleClass
			.of("menu-bar", ul.isMenuBar())
			.add("breadcrumb", ul.isBreadcrumb())
			.add(ul.getStyleClass())
			.add(ul.getColor())
			.add(ul.getOrientation())
			.add(ul.getIconPosition())
			.add("is-hidden", !ul.isShow())
			.write(writer);
	}

	public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
		Ul ul = (Ul) component;
		if (ul.isRendered() && ul.isShow())
			super.encodeChildren(context, component);
	}

	public boolean getRendersChildren() {
		return true;
	}

	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		writer.endElement("ul");
	}

}