package io.tornadofaces.component.poll;

import io.tornadofaces.component.CoreRenderer;
import io.tornadofaces.component.command.Command;
import io.tornadofaces.component.util.ComponentUtils;
import io.tornadofaces.util.WidgetBuilder;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.ActionEvent;
import javax.faces.render.FacesRenderer;
import java.io.IOException;
import java.util.Arrays;

@FacesRenderer(rendererType = PollRenderer.RENDERER_TYPE, componentFamily = ComponentUtils.COMPONENT_FAMILY)
public class PollRenderer extends CoreRenderer {
	public static final String RENDERER_TYPE = "io.tornadofaces.component.PollRenderer";

	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		Poll poll = (Poll) component;

		ResponseWriter writer = context.getResponseWriter();
		writer.startElement("input", poll);
		writer.writeAttribute("type", "hidden", null);
		writer.writeAttribute("id", poll.getClientId(context), null);
		writer.endElement("input");

		new WidgetBuilder(context, poll)
			.init()
			.attr("onload", poll.getOnload())
			.attr("interval", poll.getInterval())
			.attr("delay", poll.getDelay())
			.attr("render", ComponentUtils.resolveIds(context, poll, Arrays.asList(poll.getRender().split(" "))))
			.attr("execute", ComponentUtils.resolveIds(context, poll, Arrays.asList(poll.getExecute().split(" "))))
			.attr("onsuccess", poll.getOnsuccess())
			.attr("oncomplete", poll.getOncomplete())
			.attr("onbegin", poll.getOnbegin())
			.finish();
	}

	public void decode(FacesContext context, UIComponent component) {
		String clientId = component.getClientId();
		String source = context.getExternalContext().getRequestParameterMap().get("javax.faces.source");

		if (clientId.equals(source))
			component.queueEvent(new ActionEvent(component));

		super.decode(context, component);
	}

}