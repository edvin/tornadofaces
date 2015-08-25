package io.tornadofaces.component.input;

import io.tornadofaces.component.util.ComponentUtils;
import io.tornadofaces.component.util.StyleClass;

import javax.faces.application.ResourceDependency;
import javax.faces.component.*;
import javax.faces.component.html.HtmlOutputLabel;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.PostAddToViewEvent;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;

@ResourceDependency(library = "tornadofaces", name = "quickhelp.js")
@FacesComponent(value = FormElement.COMPONENT_TYPE, createTag = true, tagName = "form-element", namespace = "http://tornadofaces.io/ui")
public class FormElement extends UIPanel implements SystemEventListener {
	public static final String COMPONENT_TYPE = "io.tornadofaces.component.FormElement";
	public enum LabelPosition { top, left, right }
	
	public FormElement() {
		super();
		setRendererType(FormElementRenderer.RENDERER_TYPE);
		FacesContext.getCurrentInstance().getViewRoot().subscribeToViewEvent(PostAddToViewEvent.class, this);
	}

	public void processEvent(SystemEvent event) throws AbortProcessingException {
		if (event instanceof PostAddToViewEvent) {
			for (UIComponent child : getChildren()) {
				// Add form-label class to span children
				if (child instanceof HtmlOutputLabel) {
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
		return getChildCount() > 1 || getPrefix() != null || getSuffix() != null || (getChildCount() == 1 && Boolean.TRUE.equals(getChildren().get(0).getAttributes().get("renderInlineLabelWrapper")));
	}

	public String getLayout() { return (String) getStateHelper().eval("layout", null); }
	public void setLayout(String layout) { getStateHelper().put("layout", layout); }
	public String getStyleClass() { return (String) getStateHelper().eval("styleClass"); }
	public void setStyleClass(String styleClass) { getStateHelper().put("styleClass", styleClass); }
	public String getLabelClass() { return (String) getStateHelper().eval("labelClass"); }
	public void setLabelClass(String labelClass) { getStateHelper().put("labelClass", labelClass); }
	public String getStyle() { return (String) getStateHelper().eval("style"); }
	public void setStyle(String style) { getStateHelper().put("style", style); }
	public String getPrefix() { return (String) getStateHelper().eval("prefix"); }
	public void setPrefix(String prefix) { getStateHelper().put("prefix", prefix); }
	public String getSuffix() { return (String) getStateHelper().eval("suffix"); }
	public void setSuffix(String suffix) { getStateHelper().put("suffix", suffix); }
	public String getLabel() { return (String) getStateHelper().eval("label"); }
	public void setLabel(String label) { getStateHelper().put("label", label); }
	public Integer getSmall() { return (Integer) getStateHelper().eval("small", null); }
	public void setSmall(Integer small) { getStateHelper().put("small", small); }
	public Integer getMedium() { return (Integer) getStateHelper().eval("medium", null); }
	public void setMedium(Integer medium) { getStateHelper().put("medium", medium); }
	public Integer getLarge() { return (Integer) getStateHelper().eval("large", null); }
	public void setLarge(Integer large) { getStateHelper().put("large", large); }
	public LabelPosition getLabelPosition() { return (LabelPosition) getStateHelper().eval("labelPosition"); }
	public void setLabelPosition(LabelPosition labelPosition) { getStateHelper().put("labelPosition", labelPosition); }
	public Boolean isPadding() { return (Boolean) getStateHelper().eval("padding", null); }
	public void setPadding(Boolean padding) { getStateHelper().put("padding", padding); }
	public Boolean isShow() { return (Boolean) getStateHelper().eval("show", true); }
	public void setShow(Boolean show) { getStateHelper().put("show", show); }

	public void setQuickhelp(String quickhelp) { getStateHelper().put("quickhelp", quickhelp); }
	public String getQuickhelp(){ return (String) getStateHelper().eval("quickhelp", ""); }
}
