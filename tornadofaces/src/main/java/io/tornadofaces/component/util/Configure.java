package io.tornadofaces.component.util;


import javax.el.ELContext;
import javax.el.MethodExpression;
import javax.faces.component.UIComponent;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TagConfig;
import javax.faces.view.facelets.TagHandler;
import java.io.IOException;

public class Configure extends TagHandler {
	public Configure(TagConfig config) {
		super(config);
	}

	public void apply(FaceletContext faceletContext, UIComponent component) throws IOException {
		Class parentClass = component.getClass();
		MethodExpression withExpr = tag.getAttributes().get("with").getMethodExpression(faceletContext, Void.class, new Class[]{parentClass});
		ELContext elContext = faceletContext.getFacesContext().getELContext();
		withExpr.invoke(elContext, new UIComponent[]{component});
	}
}
