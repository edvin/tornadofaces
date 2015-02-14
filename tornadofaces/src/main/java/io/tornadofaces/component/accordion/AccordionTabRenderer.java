package io.tornadofaces.component.accordion;

import io.tornadofaces.component.tab.Tab;
import io.tornadofaces.component.util.StyleClass;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;
import java.io.IOException;

@FacesRenderer(rendererType = AccordionTabRenderer.RENDERER_TYPE, componentFamily = "io.tornadofaces.component")
public class AccordionTabRenderer extends Renderer {
	public static final String RENDERER_TYPE = "io.tornadofaces.component.AccordionTabRenderer";

	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
		ResponseWriter writer = context.getResponseWriter();

		Tab tab = (Tab) component;

		writer.startElement("div", tab);
		String clientId = tab.getClientId(context);
		writer.writeAttribute("id", clientId, null);

		StyleClass.of("accordion-content").add(tab.getStyleClass()).write(writer);
	}

	public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
		Accordion accordion = (Accordion) component.getParent();

		if(accordion.isDynamic()) {
			Tab tab = (Tab) component;
			if(tab.isActive())
				super.encodeChildren(context, component);
		} else {
			super.encodeChildren(context, component);
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