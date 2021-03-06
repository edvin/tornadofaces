package io.tornadofaces.component.notification;

import javax.faces.application.FacesMessage;

public class NotificationMessage extends FacesMessage {
	private String image;
	private Boolean closable;
	private Integer timeout;

	public NotificationMessage(String summary) {
		super(summary);
	}

	public NotificationMessage(FacesMessage message) {
		this.setSummary(message.getSummary());
		this.setDetail(message.getDetail());
		this.setSeverity(message.getSeverity());
	}

	public NotificationMessage(String summary, String detail) {
		super(summary, detail);
	}

	public NotificationMessage(String summary, String detail, String image) {
		super(summary, detail);
		this.image = image;
	}

	public NotificationMessage(Severity severity, String summary, String detail) {
		super(severity, summary, detail);
	}

	public NotificationMessage(Severity severity, String summary, String detail, String image) {
		super(severity, summary, detail);
		this.image = image;
	}

	public NotificationMessage(Severity severity, String summary, String detail, String image, Boolean closable, Integer timeout) {
		super(severity, summary, detail);
		this.image = image;
		this.closable = closable;
		this.timeout = timeout;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public NotificationMessage image(String image) {
		setImage(image);
		return this;
	}

	public Boolean getClosable() {
		return closable;
	}

	public void setClosable(Boolean closable) {
		this.closable = closable;
	}

	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	public NotificationMessage closable(boolean closable) {
		setClosable(closable);
		return this;
	}
}