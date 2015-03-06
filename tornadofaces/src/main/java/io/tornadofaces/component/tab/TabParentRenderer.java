package io.tornadofaces.component.tab;

import io.tornadofaces.component.CoreRenderer;
import io.tornadofaces.json.JSONArray;
import io.tornadofaces.json.JSONObject;
import io.tornadofaces.util.WidgetBuilder;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import java.io.IOException;

import static io.tornadofaces.component.util.ComponentUtils.encodeAjaxBehaviors;

public class TabParentRenderer extends CoreRenderer {
	
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
