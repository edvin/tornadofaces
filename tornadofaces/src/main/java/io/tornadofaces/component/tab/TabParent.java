package io.tornadofaces.component.tab;

import io.tornadofaces.component.api.Widget;
import io.tornadofaces.component.util.ComponentUtils;

import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UIPanel;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import java.util.Arrays;
import java.util.Collection;

import static io.tornadofaces.component.util.ComponentUtils.getRequestParam;

public abstract class TabParent extends UIPanel implements Widget, ClientBehaviorHolder, NamingContainer {
	public static final String TAB_CHANGE_EVENT = "tabChange";

	public String getFamily() {
		return ComponentUtils.COMPONENT_FAMILY;
	}

	public abstract String getTabRendererType();

	public Integer getTabIndex(Tab tab) {
		int i = 0;
		for (UIComponent child : getChildren()) {
			if (child instanceof Tab) {
				if (child == tab)
					return i;
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

//	public void decode(FacesContext context) {
//		Map<String, String> params = context.getExternalContext().getRequestParameterMap();
//
//		String event = params.get("javax.faces.behavior.event");
//
//		if (event != null) {
//			if ("tabChange".equals(event)) {
//				String behaviorSource = params.get("javax.faces.source");
//				Tab newTab = (Tab) findComponent(behaviorSource);
//				int newIndex = getTabIndex(newTab);
//				setActiveIndex(String.valueOf(newIndex));
//			}
//
//			List<ClientBehavior> behaviors = getClientBehaviors().get(event);
//			if (behaviors != null)
//				for (ClientBehavior behavior : behaviors)
//					behavior.decode(context, this);
//		}
//	}


//	public Integer getTabContentRequestIndex(FacesContext context) {
//		String v = getRequestParam(context, getClientId(context) + "_active");
//		return v == null ? null : Integer.valueOf(v);
//	}
//
//	public Boolean isTabContentRequest(FacesContext context) {
//		return getTabContentRequestIndex(context) != null;
//	}

//	public String getContainerClientId(FacesContext context) {
//		if(this.isRepeating()) {
//			String clientId = super.getContainerClientId(context);
//
//			int index = getIndex();
//			System.out.println("Index is " + index);
//			if (index == -1)
//				return clientId;
//
//			return clientId + UINamingContainer.getSeparatorChar(context) + index;
//		}
//		else {
//			UIComponent parent = this.getParent();
//			while (parent != null) {
//				if (parent instanceof NamingContainer) {
//					return parent.getContainerClientId(context);
//				}
//				parent = parent.getParent();
//			}
//
//			return null;
//		}
//	}

}
