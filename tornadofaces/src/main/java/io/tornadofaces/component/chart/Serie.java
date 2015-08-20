package io.tornadofaces.component.chart;

import io.tornadofaces.component.util.ComponentUtils;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIData;

@FacesComponent(value = "io.tornadofaces.component.Serie", createTag = true, tagName = "serie", namespace = "http://tornadofaces.io/ui")
public class Serie extends UIData {
	public String getFamily() {
		return ComponentUtils.COMPONENT_FAMILY;
	}
	
	public String getName() { return (String) getStateHelper().eval("name"); }
	public void setName(String name) { getStateHelper().put("name", name); }
	public Boolean getArea() { return (Boolean) getStateHelper().eval("area"); }
	public void setArea(Boolean area) { getStateHelper().put("area", area); }
	public String getColor() { return (String) getStateHelper().eval("color"); }
	public void setColor(String color) { getStateHelper().put("color", color); }
	public Object getX() { return (Object) getStateHelper().eval("x"); }
	public void setX(Object x) { getStateHelper().put("x", x); }
	public Object getY() { return (Object) getStateHelper().eval("y"); }
	public void setY(Object y) { getStateHelper().put("y", y); }
}
