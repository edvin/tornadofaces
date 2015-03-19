package io.tornadofaces.component.util;

import io.tornadofaces.component.flippanel.FlipPanel;
import io.tornadofaces.json.JSONArray;
import io.tornadofaces.json.JSONObject;

import javax.faces.application.FacesMessage;
import javax.faces.component.*;
import javax.faces.component.behavior.AjaxBehavior;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.context.FacesContext;
import java.awt.*;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class ComponentUtils {
	public static final String COMPONENT_FAMILY = "io.tornadofaces.component";
	public static final String NAMESPACE = "http://tornadofaces.no/ui";
	private static final String SPLIT_EXPR = " ";

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

	public static Boolean isEmpty(String value) {
		return value == null || "".equals(value);
	}

	public static String getRequestParam(FacesContext context, String key) {
		return context.getExternalContext().getRequestParameterMap().get(key);
	}

	public static String escapeClientId(String id) {
		return "#" + id.replace(":", "\\\\:");
	}
	public static String escapeClientIdForCss(String id) {
		return "#" + id.replace(":", "\\:");
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

	public static <T extends UIComponent & ClientBehaviorHolder> JSONArray encodeAjaxBehaviors(
		FacesContext context, String name, T component) {
		List<ClientBehavior> behaviors = component.getClientBehaviors().get(name);
		if (behaviors != null && !behaviors.isEmpty()) {
			JSONArray array = new JSONArray();
			for (ClientBehavior behavior : behaviors) {
				if (behavior instanceof AjaxBehavior) {
					AjaxBehavior ab = (AjaxBehavior) behavior;

					JSONObject o = new JSONObject();
					Collection<String> execute = ab.getExecute();
					if (execute != null)
						o.put("execute", resolveIds(context, component, execute));

					Collection<String> render = ab.getRender();
					if (render != null)
						o.put("render", resolveIds(context, component, render));

					array.put(o);
				}
			}
			return array;
		}
		return null;
	}
	
	public static String resolveIds(FacesContext context, UIComponent component, Collection<String> ids) {
		if ((null == ids) || ids.isEmpty())
			return null;

		StringBuilder builder = new StringBuilder();
		
		boolean first = true;

		for (String id : ids) {
			if (id.trim().length() == 0)
				continue;
			
			if (!first)
				builder.append(' ');
			else
				first = false;

			if (id.equals("@all") || id.equals("@none") ||
				id.equals("@form") || id.equals("@this")) {
				builder.append(id);
			} else {
				builder.append(getResolvedId(context, component, id));
			}
		}

		return builder.toString();
	}

	public static UIComponent findComponent(FacesContext context, UIComponent component, String id) {
		UIComponent resolvedComponent = GlobalId.resolve(context, id);

		if (resolvedComponent == null)
			resolvedComponent = component.findComponent(id);
		
		return resolvedComponent;
	}
	
	// Returns the resolved (client id) for a particular id.
	private static String getResolvedId(FacesContext context, UIComponent component, String id) {
		UIComponent resolvedComponent = GlobalId.resolve(context, id);
		
		if (resolvedComponent == null)
			resolvedComponent = component.findComponent(id);
		
		if (resolvedComponent == null) {
			if (id.charAt(0) == UINamingContainer.getSeparatorChar(FacesContext.getCurrentInstance()))
				return id.substring(1);
			return id;
		}

		return resolvedComponent.getClientId();
	}

}
