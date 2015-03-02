package io.tornadofaces.renderkit;

import javax.faces.context.PartialResponseWriter;
import javax.faces.context.PartialViewContext;
import javax.faces.context.PartialViewContextWrapper;

public class TFPartialViewContext extends PartialViewContextWrapper {
	private PartialViewContext wrapped;
	private PartialResponseWriter writer;

	public TFPartialViewContext(PartialViewContext wrapped) {
		this.wrapped = wrapped;
		this.writer = new TFPartialResponseWriter(wrapped.getPartialResponseWriter());
	}

	public PartialViewContext getWrapped() {
		return wrapped;
	}

	public PartialResponseWriter getPartialResponseWriter() {
		return writer;
	}

	public void setPartialRequest(boolean isPartialRequest) {
		wrapped.setPartialRequest(isPartialRequest);
	}
}
