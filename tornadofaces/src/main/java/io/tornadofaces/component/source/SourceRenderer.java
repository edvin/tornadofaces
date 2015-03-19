package io.tornadofaces.component.source;

import io.tornadofaces.component.CoreRenderer;
import io.tornadofaces.component.grid.GridBlock;
import io.tornadofaces.component.grid.GridComponent;
import io.tornadofaces.component.util.ComponentUtils;
import io.tornadofaces.component.util.StyleClass;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import java.io.IOException;

@FacesRenderer(rendererType = SourceRenderer.RENDERER_TYPE, componentFamily = ComponentUtils.COMPONENT_FAMILY)
public class SourceRenderer extends CoreRenderer {
	public static final String RENDERER_TYPE = "io.tornadofaces.component.SourceRenderer";

	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
		Source source = (Source) component;
		ResponseWriter writer = context.getResponseWriter();

		writer.startElement("pre", component);
		StyleClass.of(source.getStyleClass()).add("line-numbers", source.getLineNumbers()).write(writer);

		writer.startElement("code", component);
		StyleClass.of("language-" + source.getLanguage()).write(writer);

		String id = component.getId();
		if (!id.startsWith("j_idt"))
			writer.writeAttribute("id", component.getClientId(), null);
	}

	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		writer.endElement("code");
		writer.endElement("pre");
	}
}