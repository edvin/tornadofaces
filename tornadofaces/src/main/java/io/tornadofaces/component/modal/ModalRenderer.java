package io.tornadofaces.component.modal;

import io.tornadofaces.component.util.ComponentUtils;
import io.tornadofaces.component.util.StyleClass;
import io.tornadofaces.util.WidgetBuilder;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;
import java.io.IOException;

@FacesRenderer(rendererType = ModalRenderer.RENDERER_TYPE, componentFamily = ComponentUtils.COMPONENT_FAMILY)
public class ModalRenderer extends Renderer {
	public static final String RENDERER_TYPE = "io.tornadofaces.component.ModalRenderer";

	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		Modal modal = (Modal) component;
		writer.startElement("div", modal);
		writer.writeAttribute("id", modal.getClientId(), null);
		writer.writeAttribute("class", "modal-overlay", null);

		String style = modal.getStyle();
		if (style != null)
			writer.writeAttribute("style", style, null);

		writer.startElement("aside", modal);
		writer.writeAttribute("class", "modal", null);
		writer.startElement("div", modal);
		writer.writeAttribute("class", "grid-block vertical clearfix", null);

		UIComponent left = modal.getFacet("left");
		UIComponent right = modal.getFacet("right");
		String title = modal.getTitle();
		Boolean renderTitleBar = left != null || right != null || title != null;

		if (renderTitleBar) {
			writer.startElement("div", modal);
			StyleClass.of("title-bar").add(modal.getColor()).write(writer);
		}

		if (title != null) {
			writer.startElement("div", modal);
			writer.writeAttribute("class", "center title", null);
			writer.write(title);
			writer.endElement("div");
		}

		if (left != null) {
			writer.startElement("span", modal);
			writer.writeAttribute("class", "left", null);
			left.encodeAll(context);
			writer.endElement("span");
		}

		if (right != null) {
			writer.startElement("span", modal);
			writer.writeAttribute("class", "right", null);
			right.encodeAll(context);
			writer.endElement("span");
		}

		if (renderTitleBar)
			writer.endElement("div");

		writer.startElement("div", modal);
		StyleClass.of(modal.getStyleClass()).write(writer);
	}

	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		writer.endElement("div");
		writer.endElement("div");
		writer.endElement("aside");
		writer.endElement("div");

		Modal modal = (Modal) component;

		new WidgetBuilder(context, modal)
			.init()
			.attr("open", modal.getOpen())
			.attr("closeOnOverlayClick", modal.getCloseOnOverlayClick())
			.finish();
	}
}
