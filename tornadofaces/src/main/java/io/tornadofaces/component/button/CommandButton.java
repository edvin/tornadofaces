package io.tornadofaces.component.button;

import io.tornadofaces.component.common.Color;
import io.tornadofaces.component.common.Size;
import io.tornadofaces.component.util.ComponentUtils;
import io.tornadofaces.component.util.StyleClass;

import javax.faces.application.ResourceDependency;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.behavior.AjaxBehavior;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.PreRenderViewEvent;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@ResourceDependency(library = "javax.faces", name = "jsf.js", target = "head")
@FacesComponent(value = "io.tornadofaces.component.CommandButton", createTag = true, tagName = "commandButton", namespace = "http://tornadofaces.io/ui")
public class CommandButton extends HtmlCommandButton implements SystemEventListener {
	private static final String BUTTON_CSS = "button";
	private static final Pattern EndsWithSemicolonPattern = Pattern.compile(".*;\\s*$");

	private enum PropertyKeys { size, expand, color, hollow, ajax, execute, render, preventDoubleSubmission }

	public CommandButton() {
		super();
		FacesContext.getCurrentInstance().getViewRoot().subscribeToViewEvent(PreRenderViewEvent.class, this);
	}

	public void encodeBegin(FacesContext context) throws IOException {
		connectAjaxBehavior();
		super.encodeBegin(context);
	}

	private void connectAjaxBehavior() throws IOException {
		if (isAjax()) {
			List<ClientBehavior> actions = getClientBehaviors().get(getDefaultEventName());
			if (actions == null) {
				addDefaultAjaxBehavior();
			} else {
				Optional<ClientBehavior> candidate = actions.stream().filter(b -> b instanceof AjaxBehavior).findFirst();
				if (!candidate.isPresent())
					addDefaultAjaxBehavior();
			}
		}
	}

	private void addDefaultAjaxBehavior() throws IOException {
		AjaxBehavior b = (AjaxBehavior) getFacesContext().getApplication().createBehavior(AjaxBehavior.BEHAVIOR_ID);
		b.setRender(Arrays.asList(getRender().split(" ")));
		b.setExecute(Arrays.asList(getExecute().split(" ")));

		String onsuccess = getOnsuccess();
		String oncomplete = getOncomplete();
		String onstart = getOnstart();

		if (isPreventDoubleSubmission()) {
			String clientId = ComponentUtils.escapeClientId(getClientId());
			onstart = addStatement(onstart, "$('" + clientId + "').attr('disabled', 'true');");
			onsuccess = addStatement(onsuccess, "$('" + clientId + "').removeAttr('disabled');");
		}

		if (onsuccess != null || oncomplete != null || onstart != null) {
			StringBuilder eventHandler = new StringBuilder();
			eventHandler.append("function(event){");
			addEventCallback("start", onstart, eventHandler);
			addEventCallback("complete", oncomplete, eventHandler);
			addEventCallback("success", onsuccess, eventHandler);
			eventHandler.append("}");
			b.setOnevent(eventHandler.toString());
		}

		addClientBehavior(getDefaultEventName(), b);
	}

	private String addStatement(String statement, String codeToAdd) {
		if (statement == null)
			return codeToAdd;
		else
			return completeStatement(statement) + " " + codeToAdd;
	}

	private void addEventCallback(String name, String callback, StringBuilder eventHandler) {
		if (callback != null)
			eventHandler.append("if(event.status=='").append(name).append("') {").append(callback).append("}");
	}

	private String completeStatement(String statement) {
		return EndsWithSemicolonPattern.matcher(statement).matches() ? statement : statement + ";";
	}

	private void updateStyleClasses() {
		setStyleClass(
			StyleClass.of(getStyleClass())
				.add(getSize())
				.add(getColor())
				.add(PropertyKeys.hollow, isHollow())
				.add(HtmlCommandButton.PropertyKeys.disabled, isDisabled())
				.add(PropertyKeys.expand, isExpand())
				.add(BUTTON_CSS)
				.toString());
	}

	public Size getSize() { return (Size) this.getStateHelper().eval(PropertyKeys.size, null); }
	public void setSize(Size size) { this.getStateHelper().put(PropertyKeys.size, size); }

	public Boolean isExpand() { return (Boolean) this.getStateHelper().eval(PropertyKeys.expand, null); }
	public void setExpand(Boolean expand) { this.getStateHelper().put(PropertyKeys.expand, expand); }

	public Boolean isPreventDoubleSubmission() { return (Boolean) this.getStateHelper().eval(PropertyKeys.preventDoubleSubmission, true); }
	public void setPreventDoubleSubmission(Boolean preventDoubleSubmission) { this.getStateHelper().put(PropertyKeys.preventDoubleSubmission, preventDoubleSubmission); }

	public void setColor(Color color) { this.getStateHelper().put(PropertyKeys.color, color); }
	public Color getColor() { return (Color) this.getStateHelper().eval(PropertyKeys.color, null); }

	public boolean isAjax() { return (Boolean) getStateHelper().eval(PropertyKeys.ajax, true); }
	public void setAjax(boolean ajax) {
		getStateHelper().put(PropertyKeys.ajax, ajax);
	}

	public boolean isHollow() { return (Boolean) getStateHelper().eval(PropertyKeys.hollow, false); }
	public void setHollow(boolean hollow) {
		getStateHelper().put(PropertyKeys.hollow, hollow);
	}

	public String getExecute() {
		return (String) getStateHelper().eval(PropertyKeys.execute, "@form");
	}
	public void setExecute(String execute) {
		getStateHelper().put(PropertyKeys.execute, execute);
	}

	public String getRender() {
		return (String) getStateHelper().eval(PropertyKeys.render, "@form");
	}
	public void setRender(String render) {
		getStateHelper().put(PropertyKeys.render, render);
	}

	public void processEvent(SystemEvent event) throws AbortProcessingException {
		updateStyleClasses();
	}

	public boolean isListenerForSource(Object source) {
		return source instanceof UIViewRoot;
	}

	public String getOnsuccess() { return (String) getStateHelper().eval("onsuccess"); }
	public void setOnsuccess(String onsuccess) { getStateHelper().put("onsuccess", onsuccess); }
	public String getOncomplete() { return (String) getStateHelper().eval("oncomplete"); }
	public void setOncomplete(String oncomplete) { getStateHelper().put("oncomplete", oncomplete); }
	public String getOnstart() { return (String) getStateHelper().eval("onstart"); }
	public void setOnstart(String onstart) { getStateHelper().put("onstart", onstart); }
}