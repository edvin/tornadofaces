package io.tornadofaces.component.tab;

import io.tornadofaces.component.CoreRenderer;
import io.tornadofaces.json.JSONArray;
import io.tornadofaces.json.JSONObject;
import io.tornadofaces.util.WidgetBuilder;

import javax.faces.component.UIComponent;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.context.FacesContext;
import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;

import static io.tornadofaces.component.util.ComponentUtils.encodeAjaxBehaviors;

public class TabParentRenderer extends CoreRenderer {

	public void decode(FacesContext context, UIComponent component) {
		Map<String, String> params = context.getExternalContext().getRequestParameterMap();
		String behaviorSource = params.get("javax.faces.source");
		String clientId = component.getClientId();

		if (behaviorSource != null && clientId.equals(behaviorSource)) {
			String behaviorEvent = params.get("javax.faces.behavior.event");

			if (behaviorEvent != null) {
				Map<String, List<ClientBehavior>> behaviors = ((ClientBehaviorHolder) component).getClientBehaviors();
				List<ClientBehavior> behaviorsForEvent = behaviors.get(behaviorEvent);

				if (behaviorsForEvent != null && !behaviorsForEvent.isEmpty()) {
					for (ClientBehavior behavior : behaviorsForEvent)
						behavior.decode(context, component);
				}
			}

			// Decode behaviors for children, so we support activate ajax events on tabs
			// without accordion having to attach a tabChange listener
			for (UIComponent child : component.getChildren()) {
				if (child instanceof Tab) {
					List<ClientBehavior> activate = ((Tab) child).getClientBehaviors().get("activate");
					if (activate != null && !activate.isEmpty()) {
						if (((Tab) child).isActive())
							activate.forEach(b -> b.decode(context, child));
					}
				}
			}
		}
	}

	public void addBehaviors(WidgetBuilder builder) throws IOException {
		FacesContext context = builder.getContext();
		TabParent tabParent = (TabParent) builder.getWidget();
		JSONObject behaviors = new JSONObject();
		
		// Add tabChange behavior
		JSONArray tabChange = encodeAjaxBehaviors(context, "tabChange", tabParent);
		if (tabChange != null)
			behaviors.put("tabChange", tabChange);

		// Add tab activate behaviors if any
		for (UIComponent child : tabParent.getChildren()) {
			if (child instanceof Tab) {
				Tab tab = (Tab) child;
				JSONArray activate = encodeAjaxBehaviors(context, "activate", tab);
				int index = tabParent.getTabIndex(tab);
				behaviors.put("activate_" + index, activate);
			}
		}

		if (behaviors.length() > 0)
			builder.nativeAttr("behaviors", behaviors.toString());
	}
}
