package io.tornadofaces.component.poll;

import io.tornadofaces.component.command.Command;
import io.tornadofaces.component.command.CommandRenderer;

import javax.faces.application.ResourceDependency;
import javax.faces.component.FacesComponent;

@ResourceDependency(library = "tornadofaces", name = "poll.js")
@FacesComponent(value = "io.tornadofaces.component.Poll", createTag = true, tagName = "poll", namespace = "http://tornadofaces.io/ui")
public class Poll extends Command {

	public Poll() {
		super();
		setRendererType(CommandRenderer.RENDERER_TYPE);
	}

	public void setInterval(Integer interval) { getStateHelper().put("interval", interval); }
	public Integer getInterval() { return (Integer) getStateHelper().eval("interval"); }
	public Boolean getOnload() { return (Boolean) getStateHelper().eval("onload", true); }
	public void setOnload(Boolean onload) { getStateHelper().put("onload", onload); }

	@Override
	public String getRender() {
		return (String) getStateHelper().eval("render");
	}

}
