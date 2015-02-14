package io.tornadofaces.component.input;

import io.tornadofaces.component.util.ComponentUtils;
import io.tornadofaces.component.util.StyleClass;
import org.w3c.dom.html.HTMLLabelElement;

import javax.faces.component.*;
import javax.faces.component.html.HtmlOutputLabel;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.PostAddToViewEvent;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;

@FacesComponent(value = FormElement.COMPONENT_TYPE, createTag = true, tagName = "form-element", namespace = "http://tornadofaces.io/ui")
public class FormElement extends UIPanel implements SystemEventListener {
	public static final String COMPONENT_TYPE = "io.tornadofaces.component.FormElement";

	public FormElement() {
		super();
		setRendererType(FormElementRenderer.RENDERER_TYPE);
		FacesContext.getCurrentInstance().getViewRoot().subscribeToViewEvent(PostAddToViewEvent.class, this);
	}

	public void processEvent(SystemEvent event) throws AbortProcessingException {
		if (event instanceof PostAddToViewEvent) {
			for (UIComponent child : getChildren()) {
				// Add form-label class to span children
				if (child instanceof HTMLLabelElement) {
					HtmlOutputLabel label = (HtmlOutputLabel) child;
					label.setStyleClass(StyleClass.of("form-label").add(label.getStyleClass()).toString());
				}

				// Add label from form-element to inputs with no label and vise-versa
				else if (child instanceof UIInput) {
					String elemLabel = getLabel();
					String inputLabel = (String) child.getAttributes().get("label");
					if (elemLabel != null && inputLabel == null)
						child.getAttributes().put("label", elemLabel);
					else if (elemLabel == null && inputLabel != null)
						setLabel(inputLabel);
				}
			}
		}
	}

	public boolean isListenerForSource(Object source) {
		return source instanceof UIViewRoot;
	}

	public String getFamily() {
		return ComponentUtils.COMPONENT_FAMILY;
	}

	public Boolean shouldRenderInlineLabelSpan() {
		return getChildCount() > 1;
	}

	public Boolean getLabelWrapsInput() { return (Boolean) getStateHelper().eval("labelWrapsInput", true); }
	public void setLabelWrapsInput(Boolean labelWrapsInput) { getStateHelper().put("labelWrapsInput", labelWrapsInput); }
	public String getLayout() { return (String) getStateHelper().eval("layout", "grid-content"); }
	public void setLayout(String layout) { getStateHelper().put("layout", layout); }
	public String getStyleClass() { return (String) getStateHelper().eval("styleClass"); }
	public void setStyleClass(String styleClass) { getStateHelper().put("styleClass", styleClass); }
	public String getLabel() { return (String) getStateHelper().eval("label"); }
	public void setLabel(String label) { getStateHelper().put("label", label); }
	public Integer getSmall() { return (Integer) getStateHelper().eval("small", null); }
	public void setSmall(Integer small) { getStateHelper().put("small", small); }
	public Integer getMedium() { return (Integer) getStateHelper().eval("medium", null); }
	public void setMedium(Integer medium) { getStateHelper().put("medium", medium); }
	public Integer getLarge() { return (Integer) getStateHelper().eval("large", null); }
	public void setLarge(Integer large) { getStateHelper().put("large", large); }
}
