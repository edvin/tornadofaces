package io.tornadofaces.component.util;

import sun.misc.MessageUtils;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.component.UIInput;
import javax.faces.component.UIViewRoot;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitHint;
import javax.faces.component.visit.VisitResult;
import javax.faces.context.FacesContext;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class ComponentUtils {
	public static final String COMPONENT_FAMILY = "io.tornadofaces.component";
	private static String SKIP_ITERATION_HINT = "javax.faces.visit.SKIP_ITERATION";

	/**
	 * Checks if this component's id was the "javax.faces.source" of the request
	 *
	 * @param context The current FacesContext
	 * @param component The component
	 * @return True if the "javax.faces.source" parameter for this request was for the supplied component
	 */
	public static Boolean isRequestSource(FacesContext context, UIComponent component) {
		return component.getClientId(context).equals(context.getExternalContext().getRequestParameterMap().get(Constants.RequestParams.PARTIAL_SOURCE_PARAM));
	}

	public static void addCSSToResponse() {
//		FacesContext context = FacesContext.getCurrentInstance();
//		if (!"true".equals(context.getExternalContext().getInitParameter("tornadofaces.SKIP_STYLESHEET"))) {
//			UIComponent css = new UIOutput();
//			css.getAttributes().put("library", "tornadofaces");
//			css.getAttributes().put("name", "css/app.css");
//			css.setRendererType("javax.faces.resource.Stylesheet");
//			context.getViewRoot().addComponentResource(context, css, "head");
//		}
	}


	public static Boolean isEmpty(String value) {
		return value == null || "".equals(value);
	}

	public static String getRequestParam(FacesContext context, String key) {
		return context.getExternalContext().getRequestParameterMap().get(key);
	}

	public static String escapeClientId(String id) {
		return "#" + id.replace(":", "\\\\:");
	}

	public static void reportIfMissingFormParent(UIComponent component, FacesContext context) {
		UIComponent parent = component.getParent();

		if (parent instanceof UIViewRoot || parent == null) {
			addFormOmittedMessage(context);
			return;
		}

		if (isForm(parent))
			return;

		reportIfMissingFormParent(parent, context);
	}

	private static void addFormOmittedMessage(FacesContext context) {
		boolean missingFormReported = false;

		FacesMessage message = new FacesMessage("This component needs to be wrapped in a form to function properly");
		List<FacesMessage> messages = context.getMessageList();
		for (FacesMessage item : messages) {
			if (item.getDetail().equals(message.getDetail())) {
				missingFormReported = true;
				break;
			}
		}
		if (!missingFormReported) {
			message.setSeverity(FacesMessage.SEVERITY_WARN);
			context.addMessage(null, message);
		}
	}

	private static boolean isForm(UIComponent component) {
		return (component instanceof UIForm || (component.getFamily() != null && component.getFamily().endsWith("Form")));
	}

	public static UIInput getFirstInputChild(UIComponent component) {
		Optional<UIComponent> x = component.getChildren().stream().filter(c -> c instanceof UIInput).findFirst();
		return (UIInput) x.orElse(null);
	}

}
