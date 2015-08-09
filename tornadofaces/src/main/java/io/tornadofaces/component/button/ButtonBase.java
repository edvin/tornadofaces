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

	public String getStyle() { return (String) getStateHelper().eval("style"); }
	public void setStyle(String style) { getStateHelper().put("style", style); }

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
	public String getOnerror() { return (String) getStateHelper().eval("onerror"); }
	public void setOnerror(String onerror) { getStateHelper().put("onerror", onerror); }
	public String getIcon() { return (String) getStateHelper().eval("icon"); }
	public void setIcon(String icon) { getStateHelper().put("icon", icon); }
	public String getOncomplete() { return (String) getStateHelper().eval("oncomplete"); }
	public void setOncomplete(String oncomplete) { getStateHelper().put("oncomplete", oncomplete); }
	public String getOnbegin() { return (String) getStateHelper().eval("onbegin"); }
	public void setOnbegin(String onbegin) { getStateHelper().put("onbegin", onbegin); }
	public String getBeforebegin() { return (String) getStateHelper().eval("beforebegin"); }
	public void setBeforebegin(String beforebegin) { getStateHelper().put("beforebegin", beforebegin); }

	public String getWidgetVar() { return (String) getStateHelper().eval("widgetVar"); }
	public void setWidgetVar(String widgetVar) { getStateHelper().put("widgetVar", widgetVar); }

	public String getTitle() { return (String) getStateHelper().eval("title"); }
	public void setTitle(String title) { getStateHelper().put("title", title); }

	public Boolean getShow() { return (Boolean) getStateHelper().eval("show", true); }
	public void setShow(Boolean show) { getStateHelper().put("show", show); }

	public Boolean getTreatAsButton() {
		return !(this instanceof CommandLink) || ((CommandLink) this).isButton();
	}
}
