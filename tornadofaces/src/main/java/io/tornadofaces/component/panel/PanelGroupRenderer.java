package io.tornadofaces.component.panel;

import io.tornadofaces.component.CoreRenderer;
import io.tornadofaces.component.util.StyleClass;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import java.io.IOException;

import static io.tornadofaces.component.util.ComponentUtils.COMPONENT_FAMILY;

@FacesRenderer(rendererType = PanelGroupRenderer.RENDERER_TYPE, componentFamily = COMPONENT_FAMILY)
public class PanelGroupRenderer extends CoreRenderer {
	public static final String RENDERER_TYPE = "io.tornadofaces.component.PanelGroupRenderer";

	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		PanelGroup group = (PanelGroup) component;
		writer.startElement(group.getTag(), group);
		writer.writeAttribute("id", group.getClientId(context), null);

		if (group.getShow()) {
			StyleClass.of(group.getStyleClass()).write(writer);
			String style = group.getStyle();
			if (style != null)
				writer.writeAttribute("style", style, null);
		}
	}

	public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
		PanelGroup group = (PanelGroup) component;
		if (!group.getShow())
			return;

		for (UIComponent child : component.getChildren())
			child.encodeAll(context);
	}

	public boolean getRendersChildren() {
		return true;
	}

	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		PanelGroup group = (PanelGroup) component;
		writer.endElement(group.getTag());
	}

}
