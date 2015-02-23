package io.tornadofaces.component.accordion;

import io.tornadofaces.component.tab.Tab;
import io.tornadofaces.component.tab.TabParent;
import io.tornadofaces.component.util.ComponentUtils;
import io.tornadofaces.component.util.Constants;
import io.tornadofaces.event.TabChangeEvent;

import javax.faces.application.ResourceDependency;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.FacesEvent;
import java.util.Map;

import static io.tornadofaces.component.util.ComponentUtils.isRequestSource;

@ResourceDependency(library = "tornadofaces", name = "accordion.js")
@FacesComponent(value = Accordion.COMPONENT_TYPE, createTag = true, tagName = "accordion", namespace = "http://tornadofaces.io/ui")
public class Accordion extends TabParent {
	public static final String COMPONENT_TYPE = "io.tornadofaces.component.Accordion";

	public enum PropertyKeys {multi, collapsible}

	public Accordion() {
		super();
		UIForm form = new UIForm();
		form.setId("stateholder");
		getChildren().add(form);
		ComponentUtils.addCSSToResponse();
		setRendererType(AccordionRenderer.RENDERER_TYPE);
	}

	public String getTabRendererType() {
		return AccordionTabRenderer.RENDERER_TYPE;
	}

	public void queueEvent(FacesEvent event) {
		FacesContext context = getFacesContext();

		if (isRequestSource(context, this) && event instanceof AjaxBehaviorEvent) {
			Map<String, String> params = context.getExternalContext().getRequestParameterMap();
			String eventName = params.get(Constants.RequestParams.PARTIAL_BEHAVIOR_EVENT_PARAM);
			String clientId = this.getClientId(context);
			AjaxBehaviorEvent behaviorEvent = (AjaxBehaviorEvent) event;

			if (eventName.equals("tabChange")) {
				String tabClientId = params.get(clientId + "_newTab");
				TabChangeEvent changeEvent = new TabChangeEvent(this, behaviorEvent.getBehavior(), findTab(tabClientId));
				changeEvent.setPhaseId(behaviorEvent.getPhaseId());
				super.queueEvent(changeEvent);
			}
		} else {
			super.queueEvent(event);
		}
	}

	public Tab findTab(String tabClientId) {
		for (UIComponent component : getChildren()) {
			if (component.getClientId().equals(tabClientId))
				return (Tab) component;
		}

		return null;
	}

	public Boolean isMulti() {
		return (Boolean) getStateHelper().eval(PropertyKeys.multi, false);
	}

	public void setMulti(Boolean multi) {
		getStateHelper().put(PropertyKeys.multi, multi);
	}

	public Boolean isCollapsible() {
		return (Boolean) getStateHelper().eval(PropertyKeys.collapsible, isMulti());
	}

	public void setCollapsible(Boolean collapsible) {
		getStateHelper().put(PropertyKeys.collapsible, collapsible);
	}
}
