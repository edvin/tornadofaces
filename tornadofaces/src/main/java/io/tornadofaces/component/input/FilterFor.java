package io.tornadofaces.component.input;

import javax.faces.component.UIComponent;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TagConfig;
import javax.faces.view.facelets.TagHandler;
import java.io.IOException;

public class FilterFor extends TagHandler {
	public FilterFor(TagConfig config) {
		super(config);
	}

	public void apply(FaceletContext faceletContext, UIComponent parent) throws IOException {
		String widget = tag.getAttributes().get("widget").getValue();
		parent.getAttributes().put("onkeyup", "TW('" + widget + "').filter($(this).val())");
	}
}
