package io.tornadofaces.component.list;

import io.tornadofaces.component.util.ComponentUtils;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIColumn;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;

@FacesComponent(value = Li.COMPONENT_TYPE, createTag = true, tagName = "li", namespace = "http://tornadofaces.io/ui")
public class Li extends UIColumn {
	public static final String COMPONENT_TYPE = "io.tornadofaces.component.Li";

	public Li() {
		super();
		setRendererType(LiRenderer.RENDERER_TYPE);
	}

	public String getFamily() {
		return ComponentUtils.COMPONENT_FAMILY;
	}

	public boolean getChevron() { return (boolean) getStateHelper().eval("chevron", true); }
	public void setChevron(boolean chevron) { getStateHelper().put("chevron", chevron); }
	public String getStyleClass() { return (String) getStateHelper().eval("styleClass"); }
	public void setStyleClass(String styleClass) { getStateHelper().put("styleClass", styleClass); }
	public String getActiveClass() { return (String) getStateHelper().eval("activeClass", "is-active"); }
	public void setActiveClass(String activeClass) { getStateHelper().put("activeClass", activeClass); }

	public Boolean getActive() {
		Boolean active = (Boolean) getStateHelper().eval("active");
		if (active == null) {
			String link = getLink();
			active = link != null && FacesContext.getCurrentInstance().getViewRoot().getViewId().startsWith(link);
		}
		return active;
	}
	
	public void setActive(Boolean active) { getStateHelper().put("active", active); }
	public String getLink() { return (String) getStateHelper().eval("link"); }
	public void setLink(String link) { getStateHelper().put("link", link); }
}
