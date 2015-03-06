package io.tornadofaces.component.accordion;

import io.tornadofaces.component.CoreRenderer;
import io.tornadofaces.component.tab.Tab;
import io.tornadofaces.component.util.StyleClass;
import io.tornadofaces.json.JSONArray;
import io.tornadofaces.json.JSONObject;
import io.tornadofaces.util.WidgetBuilder;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import java.io.IOException;

import static io.tornadofaces.component.util.ComponentUtils.encodeAjaxBehaviors;

@FacesRenderer(rendererType = AccordionRenderer.RENDERER_TYPE, componentFamily = "io.tornadofaces.component")
public class AccordionRenderer extends CoreRenderer {
	public static final String RENDERER_TYPE = "io.tornadofaces.component.AccordionRenderer";

	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		Accordion accordion = (Accordion) component;
		writer.startElement("div", component);
		StyleClass.of("accordion")
			.add(accordion.getStyleClass())
			.add("item-spacing", accordion.isItemSpacing())
			.write(writer);
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

		UIComponent titleFacet = tab.getFacet("title");
		
		if (titleFacet != null)
			titleFacet.encodeAll(context);
		else
			writer.writeText(tab.getTitle(), null);
		
		writer.endElement("div");

		child.encodeAll(context);

		writer.endElement("div");
	}

	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		ResponseWriter writer = context.getResponseWriter();

		writer.endElement("div");

		Accordion accordion = (Accordion) component;
		WidgetBuilder builder = new WidgetBuilder(context, accordion)
			.init()
			.attr("dynamic", accordion.isDynamic())
			.attr("cache", accordion.isCache())
			.attr("multi", accordion.isMulti())
			.attr("collapsible", accordion.isCollapsible())
			.attr("autoOpen", accordion.isAutoOpen());

		JSONArray tabChange = encodeAjaxBehaviors(context, "tabChange", accordion);
		if (tabChange != null) {
			JSONObject behaviors = new JSONObject();
			behaviors.put("tabChange", tabChange);
			builder.nativeAttr("behaviors", behaviors.toString());
		}

		builder.callback("onTabChange", "function(tab)", accordion.getOnItemChange())
			.finish();
	}

	public void decode(FacesContext context, UIComponent component) {
		decodeBehaviors(context, component);
	}

}