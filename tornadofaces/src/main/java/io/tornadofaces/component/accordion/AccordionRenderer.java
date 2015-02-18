package io.tornadofaces.component.accordion;

import io.tornadofaces.component.tab.Tab;
import io.tornadofaces.component.util.StyleClass;
import io.tornadofaces.util.WidgetBuilder;

import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;
import java.io.IOException;

@FacesRenderer(rendererType = AccordionRenderer.RENDERER_TYPE, componentFamily = "io.tornadofaces.component")
public class AccordionRenderer extends Renderer {
	public static final String RENDERER_TYPE = "io.tornadofaces.component.AccordionRenderer";

	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		Accordion accordion = (Accordion) component;
		writer.startElement("div", component);
		StyleClass.of("accordion").add(accordion.getStyleClass()).write(writer);
		writer.writeAttribute("id", component.getClientId(context), null);
	}

	public boolean getRendersChildren() {
		return true;
	}

	public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
		for (UIComponent child : component.getChildren()) {
			if (child instanceof Tab) {
				if (child.isRendered())
					encodeTab(context, child);
			}
		}
	}

	private void encodeTab(FacesContext context, UIComponent child) throws IOException {
		Tab tab = (Tab) child;

		ResponseWriter writer = context.getResponseWriter();

		writer.startElement("div", child);
		StyleClass.of("accordion-item").add("is-active", tab.isActive()).write(writer);

		writer.startElement("div", child);
		writer.writeAttribute("class", "accordion-title", null);

		String icon = tab.getIcon();
		if (icon != null) {
			writer.startElement("i", tab);
			writer.writeAttribute("class", icon, null);
			writer.endElement("i");
		}

		writer.write(tab.getTitle());
		writer.endElement("div");

		child.encodeAll(context);

		writer.endElement("div");
	}

	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		ResponseWriter writer = context.getResponseWriter();

		UIForm stateholder = (UIForm) component.getChildren().get(0);
		stateholder.encodeAll(context);

		writer.endElement("div");

		Accordion accordion = (Accordion) component;
		new WidgetBuilder(context, accordion)
			.init()
			.attr("dynamic", accordion.isDynamic())
			.attr("cache", accordion.isCache())
			.attr("multi", accordion.isMulti())
			.attr("collapsible", accordion.isCollapsible())
			.attr("autoOpen", accordion.isAutoOpen())
			.callback("onItemChange", "function(item)", accordion.getOnItemChange())
			.finish();
	}

}