package io.tornadofaces.component.menu;

import io.tornadofaces.component.util.ComponentUtils;
import io.tornadofaces.util.WidgetBuilder;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;
import java.io.IOException;

@FacesRenderer(rendererType = ContextMenuRenderer.RENDERER_TYPE, componentFamily = ComponentUtils.COMPONENT_FAMILY)
public class ContextMenuRenderer extends Renderer {
	public static final String RENDERER_TYPE = "io.tornadofaces.component.ContextMenuRenderer";

	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		ContextMenu menu = (ContextMenu) component;

		new WidgetBuilder(context, menu)
			.init()
			.finish();
	}
}
