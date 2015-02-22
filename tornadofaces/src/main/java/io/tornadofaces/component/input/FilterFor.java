package io.tornadofaces.component.input;

import io.tornadofaces.component.script.Script;
import io.tornadofaces.component.util.ComponentUtils;
import io.tornadofaces.component.util.GlobalId;

import javax.faces.component.UIComponent;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.context.FacesContext;
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
		String targetName = tag.getAttributes().get("target").getValue();
		FacesContext context = faceletContext.getFacesContext();
		UIComponent target = GlobalId.resolve(context, targetName);
		if (target == null)
			target = parent.findComponent(targetName);
		
		if (target == null)
			throw new RuntimeException(String.format("Could not find component with id %s for use with widget %s", targetName, widget));
		
		String targetId = ComponentUtils.escapeClientId(target.getClientId());
		
		// Add a filter function that will be called onkeyup as well as document load
		Script script = new Script();
		StringBuilder output = new StringBuilder();
		String filterFn = "filter_" + widget;
		output.append("function ").append(filterFn).append("() { TW('").append(widget).append("').filter($('").append(targetId).append("').val()); }\n");
		output.append("$(function() { ").append(filterFn).append("(); $('").append(targetId).append("').on('keyup', ").append(filterFn).append(") });");
		script.setValue(output.toString());
		
		parent.getChildren().add(script);
	}
}
