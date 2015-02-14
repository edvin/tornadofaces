package io.tornadofaces.component.titlebar;

import io.tornadofaces.component.util.ComponentUtils;
import io.tornadofaces.component.util.StyleClass;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;
import java.io.IOException;

@FacesRenderer(rendererType = TitleBarRenderer.RENDERER_TYPE, componentFamily = ComponentUtils.COMPONENT_FAMILY)
public class TitleBarRenderer extends Renderer {
	public static final String RENDERER_TYPE = "io.tornadofaces.component.TitleBarRenderer";

	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
		if (!component.isRendered())
			return;

		ResponseWriter writer = context.getResponseWriter();
		TitleBar titleBar = (TitleBar) component;
		writer.startElement("div", titleBar);
		writer.writeAttribute("id", titleBar.getClientId(), null);

		StyleClass
			.of("title-bar")
			.add(titleBar.getStyleClass())
			.add(titleBar.getColor())
			.write(writer);

		String style = titleBar.getStyle();
		if (style != null)
			writer.writeAttribute("style", style, null);
	}

	public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		TitleBar titleBar = (TitleBar) component;

		UIComponent titleFacet = titleBar.getFacet("title");
		if (titleFacet != null) {
			writer.startElement("div", titleBar);
			writer.writeAttribute("class", "title " + titleBar.getTitlePosition(), null);
			titleFacet.encodeAll(context);
			writer.endElement("div");
		} else if (titleBar.getTitle() != null) {
			writer.startElement("span", titleBar);
			writer.writeAttribute("class", "title " + titleBar.getTitlePosition(), null);
			writer.write(titleBar.getTitle());
			writer.endElement("span");
		}

		UIComponent leftFacet = titleBar.getFacet("left");
		if (leftFacet != null) {
			writer.startElement("span", titleBar);
			writer.writeAttribute("class", "left", null);
			leftFacet.encodeAll(context);
			writer.endElement("span");
		}

		UIComponent rightFacet = titleBar.getFacet("right");
		if (rightFacet != null) {
			writer.startElement("span", titleBar);
			writer.writeAttribute("class", "right", null);
			rightFacet.encodeAll(context);
			writer.endElement("span");
		}

	}

	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		writer.endElement("div");
	}

	public boolean getRendersChildren() {
		return true;
	}
}