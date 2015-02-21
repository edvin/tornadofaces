package io.tornadofaces.component;

import javax.faces.component.UIComponent;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;
import java.util.List;
import java.util.Map;

public abstract class CoreRenderer extends Renderer {

	protected void decodeBehaviors(FacesContext context, UIComponent component) {
		if (!(component instanceof ClientBehaviorHolder))
			return;

		Map<String, List<ClientBehavior>> behaviors = ((ClientBehaviorHolder) component).getClientBehaviors();
		if (behaviors.isEmpty())
			return;

		Map<String, String> params = context.getExternalContext().getRequestParameterMap();
		String behaviorEvent = params.get("javax.faces.behavior.event");

		if (null != behaviorEvent) {
			List<ClientBehavior> behaviorsForEvent = behaviors.get(behaviorEvent);

			if (behaviorsForEvent != null && !behaviorsForEvent.isEmpty()) {
				String behaviorSource = params.get("javax.faces.source");
				String clientId = component.getClientId();

				if (behaviorSource != null && clientId.equals(behaviorSource)) {
					for (ClientBehavior behavior : behaviorsForEvent)
						behavior.decode(context, component);
				}
			}
		}
	}
}
