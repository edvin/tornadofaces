package io.tornadofaces.component.form;

import io.tornadofaces.component.CoreRenderer;
import io.tornadofaces.component.panel.PanelGroup;
import io.tornadofaces.component.util.StyleClass;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlForm;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;
import java.io.IOException;

import static io.tornadofaces.component.util.ComponentUtils.COMPONENT_FAMILY;

@FacesRenderer(rendererType = FormRenderer.RENDERER_TYPE, componentFamily = COMPONENT_FAMILY)
public class FormRenderer extends CoreRenderer {
	public static final String RENDERER_TYPE = "io.tornadofaces.component.FormRenderer";

	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		Form form = (Form) component;

		if (form.getShow()) {
			getDefaultFormRenderer(context).encodeBegin(context, component);
		} else {
			writer.startElement("form", form);
			writer.writeAttribute("id", form.getClientId(context), null);
		}
	}

	public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
		Form form = (Form) component;
		if (!form.getShow())
			return;

		getDefaultFormRenderer(context).encodeChildren(context, component);
	}

	private Renderer getDefaultFormRenderer(FacesContext context) {
		return context.getRenderKit().getRenderer(HtmlForm.COMPONENT_FAMILY, "javax.faces.Form");
	}

	public boolean getRendersChildren() {
		return true;
	}

	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		getDefaultFormRenderer(context).encodeEnd(context, component);
	}

}
