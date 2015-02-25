package io.tornadofaces.site.docs;

import io.tornadofaces.component.notification.NotificationMessage;

import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;

@Model
public class NotificationController {
	public void infoMessage() {
		FacesContext.getCurrentInstance().addMessage(null,
			new NotificationMessage("Information!", "An informational server generated message", "http://alyssa.no/resources/logo.png"));
	}
}
