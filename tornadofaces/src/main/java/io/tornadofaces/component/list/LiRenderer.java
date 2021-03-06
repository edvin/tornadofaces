package io.tornadofaces.component.list;

import io.tornadofaces.component.util.ComponentUtils;
import io.tornadofaces.component.util.StyleClass;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;
import java.io.IOException;

@FacesRenderer(rendererType = LiRenderer.RENDERER_TYPE, componentFamily = ComponentUtils.COMPONENT_FAMILY)
public class LiRenderer extends Renderer {
	public static final String RENDERER_TYPE = "io.tornadofaces.component.LiRenderer";

	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
		if (!component.isRendered())
			return;

		ResponseWriter writer = context.getResponseWriter();
		Li li = (Li) component;
		writer.startElement("li", li);
		writer.writeAttribute("id", li.getClientId(), null);
		StyleClass.of("with-chevron", li.isChevron())
			.add("title", li.isTitle())
			.add(li.getActiveClass(), li.getActive())
			.add("is-hidden", !li.isShow())
			.add(li.getStyleClass()).write(writer);

		if (!li.isShow())
			return;

		String link = li.getLink();
		if (link != null && !"".equals(link)) {
			writer.startElement("a", li);
			writer.writeAttribute("href", link, null);
		}

		String icon = li.getIcon();
		if (icon != null) {
			writer.startElement("i", li);
			writer.writeAttribute("class", icon, null);
			writer.endElement("i");
		}
	}

	public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
		Li li = (Li) component;
		if (!li.isRendered() || !li.isShow())
			return;

		Object value = li.getValue();
		if (value != null)
			context.getResponseWriter().writeText(value, component, "value");
		
		super.encodeChildren(context, component);
	}

	public boolean getRendersChildren() {
		return true;
	}

	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		Li li = (Li) component;
		if (li.getLink() != null)
			writer.endElement("a");
		writer.endElement("li");
	}

}