package io.tornadofaces.component.notification;

import io.tornadofaces.component.util.ComponentUtils;
import io.tornadofaces.util.WidgetBuilder;
import io.tornadofaces.json.JSONArray;
import io.tornadofaces.json.JSONException;
import io.tornadofaces.json.JSONObject;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;
import java.io.IOException;
import java.util.Iterator;

@FacesRenderer(rendererType = NotificationRenderer.RENDERER_TYPE, componentFamily = ComponentUtils.COMPONENT_FAMILY)
public class NotificationRenderer extends Renderer {
	public static final String RENDERER_TYPE = "io.tornadofaces.component.NotificationRenderer";

	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		writer.startElement("notifications", component);
		writer.writeAttribute("id", component.getClientId(), null);
		writer.endElement("notifications");

		Notification notification = (Notification) component;
		Iterator<FacesMessage> it = context.getMessages(notification.getFor());

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
				}
			} catch (JSONException ex) {
				throw new IOException(ex);
			}
			a.put(m);
		}

		new WidgetBuilder(context, notification)
			.init()
			.attr("timeout", notification.getTimeout())
			.attr("closable", notification.isClosable())
			.attr("position", notification.getPosition())
			.nativeAttr("messages", a.toString())
			.finish();
	}
}