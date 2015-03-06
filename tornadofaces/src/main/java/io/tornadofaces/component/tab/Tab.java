package io.tornadofaces.component.tab;

import io.tornadofaces.component.util.ComponentUtils;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponent;
import javax.faces.component.UIPanel;
import javax.faces.component.UIViewRoot;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.event.ListenerFor;
import javax.faces.event.PostAddToViewEvent;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@ListenerFor(systemEventClass = PostAddToViewEvent.class, sourceClass = UIViewRoot.class)
@FacesComponent(value = Tab.COMPONENT_TYPE, createTag = true, tagName = "tab", namespace = "http://tornadofaces.io/ui")
public class Tab extends UIPanel implements ClientBehaviorHolder {
	public static final String COMPONENT_TYPE = "io.tornadofaces.component.Tab";

	public enum PropertyKeys { styleClass, title, loaded }

	public String getFamily() {
		return ComponentUtils.COMPONENT_FAMILY;
	}

	public String getRendererType() {
		UIComponent parent = getParent();
		if (parent instanceof TabParent)
			return ((TabParent) parent).getTabRendererType();

		return super.getRendererType();
	}

	public Boolean isActive() {
		TabParent parent = (TabParent) getParent();

		String activeIndex = parent.getActiveIndex();
		if (activeIndex == null || "".equals(activeIndex))
			return false;

		List<String> activeIndexes = Arrays.asList(activeIndex.split(","));
		int index = parent.getTabIndex(this);
		return activeIndexes.contains(String.valueOf(index));
	}

	public String getDefaultEventName() {
		return "activate";
	}

	public Collection<String> getEventNames() {
		return Collections.singletonList(getDefaultEventName());
	}

	public String toString() {
		return getTitle();
	}

	public String getStyleClass() { return (String) getStateHelper().eval(PropertyKeys.styleClass); }
	public void setStyleClass(String styleClass) { getStateHelper().put(PropertyKeys.styleClass, styleClass); }
	public String getTitle() { return (String) getStateHelper().eval(PropertyKeys.title); }
	public void setTitle(String title) { getStateHelper().put(PropertyKeys.title, title); }
	public String getIcon() { return (String) getStateHelper().eval("icon"); }
	public void setIcon(String icon) { getStateHelper().put("icon", icon); }
}