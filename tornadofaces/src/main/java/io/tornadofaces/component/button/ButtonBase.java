package io.tornadofaces.component.button;

import io.tornadofaces.component.api.Widget;
import io.tornadofaces.component.common.Color;
import io.tornadofaces.component.common.Size;
import io.tornadofaces.component.util.ComponentUtils;

import javax.faces.component.UICommand;
import javax.faces.component.behavior.ClientBehaviorHolder;

public abstract class ButtonBase extends UICommand implements Widget, ClientBehaviorHolder {
	public ButtonBase() {
		super();
		setRendererType(ButtonRenderer.RENDERER_TYPE);
	}

	public String getWidgetClass() {
		return "Button";
	}

	public abstract String getTagName();
	
	public String getFamily() {
		return ComponentUtils.COMPONENT_FAMILY;
	}

	public Size getSize() { return (Size) this.getStateHelper().eval("size", null); }
	public void setSize(Size size) { this.getStateHelper().put("size", size); }

	public Boolean isExpand() { return (Boolean) this.getStateHelper().eval("expand", null); }
	public void setExpand(Boolean expand) { this.getStateHelper().put("expand", expand); }

	public Boolean isDisabled() { return (Boolean) this.getStateHelper().eval("disabled", false); }
	public void setDisabled(Boolean disabled) { this.getStateHelper().put("disabled", disabled); }

	public void setColor(Color color) { this.getStateHelper().put("color", color); }
	public Color getColor() { return (Color) this.getStateHelper().eval("color", null); }

	public void setSpinnerColor(String spinnerColor) { this.getStateHelper().put("spinnerColor", spinnerColor); }
	public String getSpinnerColor() { return (String) this.getStateHelper().eval("spinnerColor", null); }

	public void setSpinnerSize(Integer spinnerSize) { this.getStateHelper().put("spinnerSize", spinnerSize); }
	public Integer getSpinnerSize() { return (Integer) this.getStateHelper().eval("spinnerSize", null); }

	public boolean isHollow() { return (Boolean) getStateHelper().eval("hollow", false); }
	public void setHollow(boolean hollow) {
		getStateHelper().put("hollow", hollow);
	}

	public String getExecute() { return (String) getStateHelper().eval("execute", "@form"); }
	public void setExecute(String execute) {
		getStateHelper().put("execute", execute);
	}

	public String getStyleClass() { return (String) getStateHelper().eval("styleClass"); }
	public void setStyleClass(String styleClass) { getStateHelper().put("styleClass", styleClass); }

	public String getRender() {
		return (String) getStateHelper().eval("render", "@form");
	}
	public void setRender(String render) {
		getStateHelper().put("render", render);
	}

	public String getSpinnerStyle() {
		String style = (String) getStateHelper().eval("spinnerStyle", "slide-left");
		return "none".equals("style") ? null : style;
	}
	public void setSpinnerStyle(String spinnerStyle) { getStateHelper().put("spinnerStyle", spinnerStyle); }

	public String getOnsuccess() { return (String) getStateHelper().eval("onsuccess"); }
	public void setOnsuccess(String onsuccess) { getStateHelper().put("onsuccess", onsuccess); }
	public String getOncomplete() { return (String) getStateHelper().eval("oncomplete"); }
	public void setOncomplete(String oncomplete) { getStateHelper().put("oncomplete", oncomplete); }
	public String getOnstart() { return (String) getStateHelper().eval("onstart"); }
	public void setOnstart(String onstart) { getStateHelper().put("onstart", onstart); }

	public String getWidgetVar() { return (String) getStateHelper().eval("widgetVar"); }
	public void setWidgetVar(String widgetVar) { getStateHelper().put("widgetVar", widgetVar); }
}
