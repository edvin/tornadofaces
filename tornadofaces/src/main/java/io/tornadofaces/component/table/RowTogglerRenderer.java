package io.tornadofaces.component.table;

import io.tornadofaces.component.CoreRenderer;
import io.tornadofaces.component.util.ComponentUtils;
import io.tornadofaces.component.util.StyleClass;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import java.io.IOException;

@FacesRenderer(rendererType = RowTogglerRenderer.RENDERER_TYPE, componentFamily = ComponentUtils.COMPONENT_FAMILY)
public class RowTogglerRenderer extends CoreRenderer {
	public static final String RENDERER_TYPE = "io.tornadofaces.component.RowTogglerRenderer";

	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		RowToggler toggler = (RowToggler) component;
		writer.startElement("a", toggler);
		writer.writeAttribute("href", "#", null);
		StyleClass.of("row-toggler")
			.add(toggler.getStyleClass())
			.write(writer);
		writer.endElement("a");
	}
}