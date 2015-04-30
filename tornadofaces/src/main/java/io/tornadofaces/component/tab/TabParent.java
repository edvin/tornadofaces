package io.tornadofaces.component.tab;

import io.tornadofaces.component.api.Widget;
import io.tornadofaces.component.util.ComponentUtils;
import io.tornadofaces.component.util.Constants;
import io.tornadofaces.event.TabChangeEvent;

import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.component.UIPanel;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.FacesEvent;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import static io.tornadofaces.component.util.ComponentUtils.getRequestParam;
import static io.tornadofaces.component.util.ComponentUtils.isRequestSource;

public abstract class TabParent extends UIPanel implements Widget, ClientBehaviorHolder, NamingContainer {
	public static final String TAB_CHANGE_EVENT = "tabChange";

	public String getFamily() {
		return ComponentUtils.COMPONENT_FAMILY;
	}

	public TabParent() {
		UIForm stateholder = new UIForm();
		stateholder.setId("stateholder");
		getChildren().add(stateholder);
	}
	
	public abstract String getTabRendererType();

	public Tab getTabWithIndex(Integer index) {
		int i = 0;
		for (UIComponent child : getChildren()) {
			if (child instanceof Tab) {
				if (child.isRendered()) {
					if (i == index)
						return (Tab) child;
					i++;
				}
			}
		}
		return null;
	}
	
	public Integer getTabIndex(Tab tab) {
		int i = 0;
		for (UIComponent child : getChildren()) {
			if (child instanceof Tab) {
				if (child == tab)
					return i;

				if (child.isRendered())
					i++;
			}
		}
		return -1;
	}

	public void decode(FacesContext context) {
		String active = getRequestParam(context, getClientId(context) + "_active");

		if(!ComponentUtils.isEmpty(active))
			setActiveIndex(active);

		super.decode(context);
	}

	private enum Attributes { activeIndex, dynamic, cache, autoOpen, widgetVar, onItemChange, onItemShow }

	public String getDefaultEventName() {
		return TAB_CHANGE_EVENT;
	}

	public Collection<String> getEventNames() {
		return Arrays.asList(TAB_CHANGE_EVENT);
	}
	public <X> X getData() { return (X) getStateHelper().eval("data"); }
	public <X> void setData(X data) { getStateHelper().put("data", data); }
	public String getActiveIndex() { return (String) getStateHelper().eval(Attributes.activeIndex, isAutoOpen() ? "0" : "-1"); }
	public void setActiveIndex(String activeIndex) { getStateHelper().put(Attributes.activeIndex, activeIndex); }
	public String getOnItemChange() { return (String) getStateHelper().eval(Attributes.onItemChange); }
	public void setOnItemChange(String onItemChange) { getStateHelper().put(Attributes.onItemChange, onItemChange); }
	public String getWidgetVar() { return (String) getStateHelper().eval(Attributes.widgetVar); }
	public void setWidgetVar(String widgetVar) { getStateHelper().put(Attributes.widgetVar, widgetVar); }
	public Boolean isCache() { return (Boolean) getStateHelper().eval(Attributes.cache, true); }
	public void setCache(Boolean cache) { getStateHelper().put(Attributes.cache, cache); }
	public Boolean isAutoOpen() { return (Boolean) getStateHelper().eval(Attributes.autoOpen, true); }
	public void setAutoOpen(Boolean autoOpen) { getStateHelper().put(Attributes.autoOpen, autoOpen); }
	public Boolean isDynamic() { return (Boolean) getStateHelper().eval(Attributes.dynamic, false); }
	public String getStyleClass() { return (String) getStateHelper().eval("styleClass"); }
	public void setStyleClass(String styleClass) { getStateHelper().put("styleClass", styleClass); }
	public void setDynamic(Boolean dynamic) { getStateHelper().put(Attributes.dynamic, dynamic); }

	public void queueEvent(FacesEvent event) {
		FacesContext context = getFacesContext();

		if (isRequestSource(context, this) && event instanceof AjaxBehaviorEvent) {
			Map<String, String> params = context.getExternalContext().getRequestParameterMap();
			String eventName = params.get(Constants.RequestParams.PARTIAL_BEHAVIOR_EVENT_PARAM);
			String clientId = this.getClientId(context);
			AjaxBehaviorEvent behaviorEvent = (AjaxBehaviorEvent) event;

			String activeIndex = params.get(clientId + "_active");
			Tab tab = activeIndex != null ? getTabWithIndex(Integer.valueOf(activeIndex)) : null;

			if (eventName.equals("tabChange")) {
				TabChangeEvent changeEvent = new TabChangeEvent(this, behaviorEvent.getBehavior(), tab);
				changeEvent.setPhaseId(behaviorEvent.getPhaseId());
				super.queueEvent(changeEvent);
			}
		} else {
			super.queueEvent(event);
		}
	}

}