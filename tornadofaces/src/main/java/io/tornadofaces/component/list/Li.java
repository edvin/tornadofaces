package io.tornadofaces.component.list;

import io.tornadofaces.component.util.ComponentUtils;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIColumn;
import javax.faces.component.ValueHolder;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.servlet.ServletContext;

@FacesComponent(value = Li.COMPONENT_TYPE, createTag = true, tagName = "li", namespace = "http://tornadofaces.io/ui")
public class Li extends UIColumn implements ValueHolder {
	public static final String COMPONENT_TYPE = "io.tornadofaces.component.Li";
	private Converter converter;

	public Li() {
		super();
		setRendererType(LiRenderer.RENDERER_TYPE);
	}

	public String getFamily() {
		return ComponentUtils.COMPONENT_FAMILY;
	}

	public boolean isChevron() { return (boolean) getStateHelper().eval("chevron", false); }
	public void setChevron(boolean chevron) { getStateHelper().put("chevron", chevron); }
	public boolean isTitle() { return (boolean) getStateHelper().eval("title", false); }
	public void setTitle(boolean title) { getStateHelper().put("title", title); }
	public String getStyleClass() { return (String) getStateHelper().eval("styleClass"); }
	public void setStyleClass(String styleClass) { getStateHelper().put("styleClass", styleClass); }
	public String getIcon() { return (String) getStateHelper().eval("icon"); }
	public void setIcon(String icon) { getStateHelper().put("icon", icon); }
	public String getActiveClass() { return (String) getStateHelper().eval("activeClass", "is-active"); }
	public void setActiveClass(String activeClass) { getStateHelper().put("activeClass", activeClass); }
	public boolean isShow() { return (boolean) getStateHelper().eval("show", true); }
	public void setShow(boolean show) { getStateHelper().put("show", show); }

	public Boolean getActive() {
		Boolean active = (Boolean) getStateHelper().eval("active");
		if (active == null) {
			String link = getLink();
			if (link != null) {
				FacesContext context = FacesContext.getCurrentInstance();
				ServletContext servletContext = (ServletContext) context.getExternalContext().getContext();
				String contextPath = servletContext.getContextPath();
				if (link.startsWith(contextPath))
					link = link.substring(contextPath.length());

				active = context.getViewRoot().getViewId().startsWith(link);
			}
		}
		return active;
	}
	
	public void setActive(Boolean active) { getStateHelper().put("active", active); }
	public String getLink() { return (String) getStateHelper().eval("link"); }
	public void setLink(String link) { getStateHelper().put("link", link); }

	public Object getLocalValue() {
		return getStateHelper().get("value");
	}

	public Object getValue() {
		return getStateHelper().eval("value");
	}

	public void setValue(Object value) {
		getStateHelper().put("value", value);
	}

	public Converter getConverter() {
		if (this.converter != null)
			return (this.converter);
		
		return (Converter) getStateHelper().eval("converter");	}

	public void setConverter(Converter converter) {
		this.converter = converter;
	}
}
