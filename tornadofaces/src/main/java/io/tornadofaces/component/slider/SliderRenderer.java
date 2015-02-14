package io.tornadofaces.component.slider;

import io.tornadofaces.component.util.ComponentUtils;
import io.tornadofaces.component.util.StyleClass;
import io.tornadofaces.util.WidgetBuilder;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;
import java.io.IOException;

@FacesRenderer(rendererType = SliderRenderer.RENDERER_TYPE, componentFamily = ComponentUtils.COMPONENT_FAMILY)
public class SliderRenderer extends Renderer {
	public static final String RENDERER_TYPE = "io.tornadofaces.component.SliderRenderer";

	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		Slider slider = (Slider) component;
		writer.startElement("div", component);
		writer.writeAttribute("id", slider.getClientId(), null);
		StyleClass.of("slider").add(slider.getStyleClass()).write(writer);
	}

	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		Slider slider = (Slider) component;
		ResponseWriter writer = context.getResponseWriter();

		writer.endElement("div");

		new WidgetBuilder(context, slider)
			.init()
			.attr("lowerId", slider.getChildren().get(0).getClientId())
			.attr("upperId", slider.getChildren().get(1).getClientId())
			.nativeAttr("settings", slider.getSettings().toString())
			.nativeAttr("onSlide", slider.getOnSlide())
			.finish();
	}

}
