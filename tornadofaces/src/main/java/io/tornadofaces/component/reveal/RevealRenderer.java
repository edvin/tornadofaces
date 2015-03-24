package io.tornadofaces.component.reveal;

import io.tornadofaces.component.CoreRenderer;
import io.tornadofaces.component.util.ComponentUtils;
import io.tornadofaces.component.util.StyleClass;
import io.tornadofaces.util.WidgetBuilder;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import java.io.IOException;

@FacesRenderer(rendererType = RevealRenderer.RENDERER_TYPE, componentFamily = ComponentUtils.COMPONENT_FAMILY)
public class RevealRenderer extends CoreRenderer {
	public static final String RENDERER_TYPE = "io.tornadofaces.component.RevealRenderer";

	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
		Reveal reveal = (Reveal) component;
		ResponseWriter writer = context.getResponseWriter();

		writer.startElement("div", reveal);
		writer.writeAttribute("id", reveal.getClientId(context), null);
		String style = reveal.getStyle();
		if (style != null)
			writer.writeAttribute("style", style, null);

		StyleClass.of("reveal").add(reveal.getStyleClass()).write(writer);

		writer.startElement("div", reveal);
		writer.writeAttribute("class", "reveal-content", null);
	}

	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		Reveal reveal = (Reveal) component;
		ResponseWriter writer = context.getResponseWriter();

		writer.endElement("div");
		writer.endElement("div");

		Reveal.Effect effect = reveal.getEffect();

		new WidgetBuilder(context, reveal)
			.init()
			.attr("onload", reveal.getOnload())
			.attr("duration", reveal.getDuration())
			.attr("effect", effect != null ? effect.toString() : null)
			.finish();
	}
}
