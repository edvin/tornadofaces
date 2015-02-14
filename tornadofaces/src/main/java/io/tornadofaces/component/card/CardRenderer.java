package io.tornadofaces.component.card;

import io.tornadofaces.component.util.ComponentUtils;
import io.tornadofaces.component.util.StyleClass;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;
import java.io.IOException;

@FacesRenderer(rendererType = CardRenderer.RENDERER_TYPE, componentFamily = ComponentUtils.COMPONENT_FAMILY)
public class CardRenderer extends Renderer {
	public static final String RENDERER_TYPE = "io.tornadofaces.component.CardRenderer";

	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		Card card = (Card) component;
		writer.startElement("div", card);
		writer.writeAttribute("id", card.getClientId(), null);
		StyleClass.of("card").add(card.getStyleClass()).add(card.getColor()).write(writer);

		String style = card.getStyle();
		if (style != null)
			writer.writeAttribute("style", style, null);

		UIComponent top = card.getFacet("top");
		if (top != null)
			top.encodeAll(context);

		String title = card.getTitle();

		if (title != null) {
			writer.startElement("div", card);
			writer.writeAttribute("class", "card-divider", null);
			writer.write(title);
			writer.endElement("div");
		}

		writer.startElement("div", card);
		writer.writeAttribute("class", "card-section", null);
	}

	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		writer.endElement("div");
		writer.endElement("div");
	}
}