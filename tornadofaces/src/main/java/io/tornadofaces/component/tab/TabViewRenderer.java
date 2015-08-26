package io.tornadofaces.component.tab;

import io.tornadofaces.component.util.StyleClass;
import io.tornadofaces.util.WidgetBuilder;

import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import java.io.IOException;

@FacesRenderer(rendererType = TabViewRenderer.RENDERER_TYPE, componentFamily = "io.tornadofaces.component")
public class TabViewRenderer extends TabParentRenderer {
	public static final String RENDERER_TYPE = "io.tornadofaces.component.TabViewRenderer";

	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
		TabView tabView = (TabView) component;
		ResponseWriter writer = context.getResponseWriter();
		writer.startElement("div", tabView);
		StyleClass.of("tabview").add(tabView.getStyleClass()).write(writer);
		writer.writeAttribute("id", tabView.getClientId(context), null);

		writer.startElement("div", tabView);
		StyleClass.of("tabs").add(tabView.getOrientation()).write(writer);
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

		writer.endElement("div");

		writer.startElement("div", component);
		StyleClass.of("tab-contents").add(tabView.getContentsStyleClass()).add(tabView.getOrientation()).write(writer);

		for (UIComponent child : component.getChildren())
			if (child instanceof Tab)
				child.encodeAll(context);

		writer.endElement("div"); // tabContents

		UIForm stateholder = (UIForm) component.getChildren().get(0);
		stateholder.encodeAll(context);

		writer.endElement("div"); // tabView

		WidgetBuilder builder = new WidgetBuilder(context, tabView)
			.init()
			.attr("dynamic", tabView.isDynamic())
			.attr("cache", tabView.isCache())
			.attr("autoOpen", tabView.isAutoOpen())
			.nativeAttr("onItemChange", tabView.getOnItemChange());

		addBehaviors(builder);

		builder.finish();
	}

	public boolean getRendersChildren() {
		return true;
	}
}