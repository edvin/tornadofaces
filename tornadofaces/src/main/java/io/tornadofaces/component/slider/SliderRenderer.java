package io.tornadofaces.component.slider;

import io.tornadofaces.component.util.ComponentUtils;
import io.tornadofaces.component.util.StyleClass;
import io.tornadofaces.json.JSONArray;
import io.tornadofaces.json.JSONObject;
import io.tornadofaces.util.WidgetBuilder;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;
import java.io.IOException;

import static io.tornadofaces.component.util.ComponentUtils.encodeAjaxBehaviors;
import static io.tornadofaces.component.util.ComponentUtils.findComponent;

@FacesRenderer(rendererType = SliderRenderer.RENDERER_TYPE, componentFamily = ComponentUtils.COMPONENT_FAMILY)
public class SliderRenderer extends Renderer {
	public static final String RENDERER_TYPE = "io.tornadofaces.component.SliderRenderer";

	public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
		for (UIComponent child : component.getChildren())
			if (!(child instanceof SliderHeader))
				child.encodeAll(context);
	}

	public boolean getRendersChildren() {
		return true;
	}

	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		Slider slider = (Slider) component;
		writer.startElement("div", component);
		writer.writeAttribute("id", slider.getClientId(), null);
		StyleClass.of("slider").add(slider.getStyleClass()).write(writer);

		writer.startElement("input", component);
		writer.writeAttribute("type", "hidden", null);
		String lowerId = slider.getLowerClientId(context);
		writer.writeAttribute("id", lowerId, null);
		writer.writeAttribute("name", lowerId, null);
		Integer lowerValue = slider.getLower();
		if (lowerValue != null)
			writer.writeAttribute("value", lowerValue.toString(), "lower");
		writer.endElement("input");

		writer.startElement("input", component);
		writer.writeAttribute("type", "hidden", null);
		String upperId = slider.getUpperClientId(context);
		writer.writeAttribute("id", upperId, null);
		writer.writeAttribute("name", upperId, null);
		Integer upperValue = slider.getUpper();
		if (upperValue != null)
			writer.writeAttribute("value", upperValue.toString(), "upper");
		writer.endElement("input");

		// Support rendering of dynamic SliderHeader
		for (UIComponent child : component.getChildren())
			if (child instanceof SliderHeader)
				child.encodeAll(context);

		if (slider.getHeader()) {
			writer.startElement("div", slider);
			writer.writeAttribute("id", slider.getClientId(context) + "_header", null);
			writer.writeAttribute("class", "slider-header", null);

			// min
			writer.startElement("span", slider);
			writer.writeAttribute("class", "slider-min", null);
			writer.writeText(String.format("%s", slider.getMin()), null);
			writer.endElement("span");

			// max
			writer.startElement("span", slider);
			writer.writeAttribute("class", "slider-max", null);
			writer.writeText(String.format("%s", slider.getMax()), null);
			writer.endElement("span");

			// value
			writer.startElement("h4", slider);
			writer.writeText(String.format("%s", slider.getLower()), null);
			Integer upper = slider.getUpper();
			if (upper != null)
				writer.writeText(" - " + upper, null);
			writer.endElement("h4");

			writer.endElement("div");
		}

		writer.startElement("div", slider);
		writer.writeAttribute("class", "slider-content", null);
	}

	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		Slider slider = (Slider) component;
		ResponseWriter writer = context.getResponseWriter();

		writer.endElement("div"); // slider-content
		writer.endElement("div"); // slider

		WidgetBuilder builder = new WidgetBuilder(context, slider)
			.initWithDomReady()
			.attr("lowerId", slider.getLowerClientId(context))
			.attr("upperId", slider.getUpperClientId(context))
			.attr("throttle", slider.getThrottle())
			.attr("header", slider.getHeader())
			.nativeAttr("settings", slider.getSettings().toString())
			.nativeAttr("onSlide", slider.getOnSlide())
			.nativeAttr("labelFormatter", slider.getLabelFormatter());

		String lowerTarget = slider.getLowerTarget();
		if (lowerTarget != null)
			builder.attr("lowerTarget", slider.getParent().findComponent(lowerTarget).getClientId());
		
		String upperTarget = slider.getUpperTarget();
		if (upperTarget != null)
			builder.attr("upperTarget", slider.getParent().findComponent(upperTarget).getClientId());

		JSONArray flipBehaviors = encodeAjaxBehaviors(context, "change", slider);
		if (flipBehaviors != null) {
			JSONObject behaviors = new JSONObject();
			behaviors.put("change", flipBehaviors);
			builder.nativeAttr("behaviors", behaviors.toString());
		}

		builder.finish();
	}

}