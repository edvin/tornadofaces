package io.tornadofaces.component.tab;

import io.tornadofaces.component.util.StyleClass;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;
import java.io.IOException;

@FacesRenderer(rendererType = TabViewTabRenderer.RENDERER_TYPE, componentFamily = "io.tornadofaces.component")
public class TabViewTabRenderer extends Renderer {
	public static final String RENDERER_TYPE = "io.tornadofaces.component.TabViewTabRenderer";

	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		Tab tab = (Tab) component;

		writer.startElement("div", tab);
		writer.writeAttribute("id", tab.getClientId(context), null);
		StyleClass.of("tab-content").add("is-active", tab.isActive()).write(writer);
	}

	public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
		TabView tabView = (TabView) component.getParent();

		if(component instanceof Tab && tabView.isDynamic()) {
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
