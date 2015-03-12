package io.tornadofaces.component.command;

import io.tornadofaces.component.CoreRenderer;
import io.tornadofaces.component.util.ComponentUtils;
import io.tornadofaces.util.WidgetBuilder;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.FacesRenderer;
import java.io.IOException;
import java.util.Arrays;

@FacesRenderer(rendererType = CommandRenderer.RENDERER_TYPE, componentFamily = ComponentUtils.COMPONENT_FAMILY)
public class CommandRenderer extends CoreRenderer {
	public static final String RENDERER_TYPE = "io.tornadofaces.component.CommandRenderer";

	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		Command command = (Command) component;

		new WidgetBuilder(context, command)
			.init()
			.attr("name", command.getName())
			.attr("render", ComponentUtils.resolveIds(context, command, Arrays.asList(command.getRender().split(" "))))
			.attr("execute", ComponentUtils.resolveIds(context, command, Arrays.asList(command.getExecute().split(" "))))
			.attr("onsuccess", command.getOnsuccess())
			.attr("oncomplete", command.getOncomplete())
			.attr("onstart", command.getOnstart())
			.finish();
	}
}
