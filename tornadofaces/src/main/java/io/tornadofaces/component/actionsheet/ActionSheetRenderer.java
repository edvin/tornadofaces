package io.tornadofaces.component.actionsheet;

import io.tornadofaces.component.util.ComponentUtils;
import io.tornadofaces.component.util.StyleClass;
import io.tornadofaces.util.WidgetBuilder;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;
import java.io.IOException;

@FacesRenderer(rendererType = ActionSheetRenderer.RENDERER_TYPE, componentFamily = ComponentUtils.COMPONENT_FAMILY)
public class ActionSheetRenderer extends Renderer {
	public static final String RENDERER_TYPE = "io.tornadofaces.component.ActionSheetRenderer";

	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
		ActionSheet sheet = (ActionSheet) component;
		ResponseWriter writer = context.getResponseWriter();

		// ActionSheet Container
		writer.startElement("div", component);
		writer.writeAttribute("id", component.getClientId(), null);
		StyleClass.of("action-sheet-container").add(sheet.getStyleClass()).write(writer);

		// Trigger Container
		writer.startElement("div", component);
		writer.startElement("a", component);
		writer.writeAttribute("href", "#", null);
		writer.writeAttribute("class", "button", null);
		writer.writeText(sheet.getTitle(), null);
		writer.endElement("a");
		writer.endElement("div");

		// ActionSheet with description (top of the Sheet)
		writer.startElement("div", component);
		StyleClass.of("action-sheet").add(sheet.getPosition()).write(writer);
		writer.startElement("p", component);
		writer.writeText(sheet.getDescription(), null);
		writer.endElement("p");

		// Child Container
		writer.startElement("ul", component);
	}

	public boolean getRendersChildren() {
		return true;
	}

	public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
		ResponseWriter writer = context.getResponseWriter();

		for (UIComponent child : component.getChildren()) {
			writer.startElement("li", component);
			child.encodeAll(context);
			writer.endElement("li");
		}
	}

	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		ResponseWriter writer = context.getResponseWriter();

		writer.endElement("ul"); // End Child Container
		writer.endElement("div"); // End ActionSheet
		writer.endElement("div"); // End ActionSheetContainer

		ActionSheet sheet = (ActionSheet) component;

		new WidgetBuilder(context, sheet)
			.init()
			.finish();
	}
}