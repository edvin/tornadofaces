package io.tornadofaces.site.docs;

import io.tornadofaces.component.notification.NotificationMessage;

import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;

@Model
public class NotificationController {

	public void successMessage() {
		FacesContext.getCurrentInstance().addMessage(null,
			new NotificationMessage("Success!", "A successfull server generated message", "http://alyssa.no/resources/logo.png"));
	}
}
