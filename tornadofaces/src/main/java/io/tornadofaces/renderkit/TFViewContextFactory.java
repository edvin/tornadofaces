package io.tornadofaces.renderkit;

import javax.faces.context.FacesContext;
import javax.faces.context.PartialViewContext;
import javax.faces.context.PartialViewContextFactory;

public class TFViewContextFactory extends PartialViewContextFactory {
	private PartialViewContextFactory wrapped;

	public TFViewContextFactory(PartialViewContextFactory wrapped) {
		this.wrapped = wrapped;
	}

	public PartialViewContext getPartialViewContext(FacesContext context) {
		return new TFPartialViewContext(wrapped.getPartialViewContext(context));
	}
}
