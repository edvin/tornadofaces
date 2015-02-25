package io.tornadofaces.component.tab;

import io.tornadofaces.component.util.StyleClass;
import io.tornadofaces.util.WidgetBuilder;

import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;
import java.io.IOException;

@FacesRenderer(rendererType = TabViewRenderer.RENDERER_TYPE, componentFamily = "io.tornadofaces.component")
public class TabViewRenderer extends Renderer {
	public static final String RENDERER_TYPE = "io.tornadofaces.component.TabViewRenderer";

	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
		TabView tabView = (TabView) component;
		ResponseWriter writer = context.getResponseWriter();
		writer.startElement("div", tabView);
		writer.writeAttribute("id", tabView.getClientId(context), null);
		StyleClass.of("tabs").add(tabView.getOrientation()).add(tabView.getStyleClass()).write(writer);
	}

	public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
		ResponseWriter writer = context.getResponseWriter();

		for (UIComponent child : component.getChildren()) {
			if (child instanceof Tab) {
				Tab tab = (Tab) child;
				writer.startElement("div", tab);
				StyleClass.of("tab-item").add("is-active", tab.isActive()).add(tab.getStyleClass()).write(writer);

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
			}
		}
	}

	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		ResponseWriter writer = context.getResponseWriter();

		TabView tabView = (TabView) component;

		UIForm stateholder = (UIForm) component.getChildren().get(0);
		stateholder.encodeAll(context);

		writer.endElement("div");

		writer.startElement("div", component);
		StyleClass.of("tab-contents").add(tabView.getContentsStyleClass()).add(tabView.getOrientation()).write(writer);

		for (UIComponent child : component.getChildren())
			if (child instanceof Tab)
				child.encodeAll(context);

		writer.endElement("div");

		new WidgetBuilder(context, tabView)
			.init()
			.attr("dynamic", tabView.isDynamic())
			.attr("cache", tabView.isCache())
			.attr("autoOpen", tabView.isAutoOpen())
			.callback("onItemChange", "function(item)", tabView.getOnItemChange())
			.finish();

	}

	public boolean getRendersChildren() {
		return true;
	}
}