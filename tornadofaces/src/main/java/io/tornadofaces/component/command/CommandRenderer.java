package io.tornadofaces.component.command;

import io.tornadofaces.component.CoreRenderer;
import io.tornadofaces.component.button.ButtonBase;
import io.tornadofaces.component.util.ComponentUtils;
import io.tornadofaces.util.WidgetBuilder;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.ActionEvent;
import javax.faces.render.FacesRenderer;
import java.io.IOException;
import java.util.Arrays;

@FacesRenderer(rendererType = CommandRenderer.RENDERER_TYPE, componentFamily = ComponentUtils.COMPONENT_FAMILY)
public class CommandRenderer extends CoreRenderer {
	public static final String RENDERER_TYPE = "io.tornadofaces.component.CommandRenderer";

	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		Command command = (Command) component;

		ResponseWriter writer = context.getResponseWriter();
		writer.startElement("input", command);
		writer.writeAttribute("type", "hidden", null);
		writer.writeAttribute("id", command.getClientId(context), null);
		writer.endElement("input");

		new WidgetBuilder(context, command)
			.init()
			.attr("name", command.getName())
			.attr("render", ComponentUtils.resolveIds(context, command, Arrays.asList(command.getRender().split(" "))))
			.attr("execute", ComponentUtils.resolveIds(context, command, Arrays.asList(command.getExecute().split(" "))))
			.attr("onsuccess", command.getOnsuccess())
			.attr("oncomplete", command.getOncomplete())
			.attr("onstart", command.getOnstart())
			.nativeAttr("onload", command.getOnload().toString())
			.finish();
	}

	public void decode(FacesContext context, UIComponent component) {
		String clientId = component.getClientId();
		String source = context.getExternalContext().getRequestParameterMap().get("javax.faces.source");

		if (clientId.equals(source))
			component.queueEvent(new ActionEvent(component));

		decodeBehaviors(context, component);
	}

}