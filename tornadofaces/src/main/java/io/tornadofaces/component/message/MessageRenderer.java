package io.tornadofaces.component.message;

import io.tornadofaces.component.notification.NotificationMessage;
import io.tornadofaces.component.util.ComponentUtils;
import io.tornadofaces.component.util.StyleClass;
import io.tornadofaces.json.JSONArray;
import io.tornadofaces.json.JSONException;
import io.tornadofaces.json.JSONObject;
import io.tornadofaces.util.WidgetBuilder;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;
import java.io.IOException;
import java.util.Iterator;

@FacesRenderer(rendererType = MessageRenderer.RENDERER_TYPE, componentFamily = ComponentUtils.COMPONENT_FAMILY)
public class MessageRenderer extends Renderer {
	public static final String RENDERER_TYPE = "io.tornadofaces.component.MessageRenderer";

	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		writer.startElement("div", component);
		io.tornadofaces.component.message.Message messageComponent = (io.tornadofaces.component.message.Message) component;
		StyleClass.of("messages vertical grid-block").add(messageComponent.getStyleClass()).write(writer);
		writer.writeAttribute("id", component.getClientId(), null);

		String style = messageComponent.getStyle();

		if (style != null)
			writer.writeAttribute("style", style, null);

		writer.endElement("div");

		io.tornadofaces.component.message.Message messages = (io.tornadofaces.component.message.Message) component;
		Iterator<FacesMessage> it = context.getMessages(messages.getFor());

		JSONArray a = new JSONArray();

		while (it.hasNext()) {
			FacesMessage message = it.next();
			JSONObject m = new JSONObject();
			try {
				if (message.getSummary() != null)
					m.put("summary", message.getSummary());

				if (message.getDetail() != null)
					m.put("detail", message.getDetail());

				m.put("severity", message.getSeverity().toString().replaceAll("\\s+\\d+", ""));

				if (message instanceof NotificationMessage) {
					NotificationMessage nmsg = (NotificationMessage) message;
					if (nmsg.getImage() != null)
						m.put("image", nmsg.getImage());

					if (nmsg.getClosable() != null)
						m.put("closable", nmsg.getClosable());

					if (nmsg.getTimeout() != null)
						m.put("timeout", nmsg.getTimeout());

				}
			} catch (JSONException ex) {
				throw new IOException(ex);
			}
			a.put(m);
		}

		new WidgetBuilder(context, messages)
			.init()
			.attr("timeout", messages.getTimeout())
			.attr("closable", messages.isClosable())
			.nativeAttr("messages", a.toString())
			.finish();
	}
}
