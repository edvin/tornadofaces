package io.tornadofaces.component.slider;

import io.tornadofaces.component.api.Widget;
import io.tornadofaces.component.common.Direction;
import io.tornadofaces.component.common.Orientation;
import io.tornadofaces.component.util.ComponentUtils;
import io.tornadofaces.json.JSONArray;
import io.tornadofaces.json.JSONObject;

import javax.el.ValueExpression;
import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.FacesComponent;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIPanel;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.context.FacesContext;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@ResourceDependencies({
	@ResourceDependency(library = "tornadofaces", name = "jquery.nouislider.min.js"),
	@ResourceDependency(library = "tornadofaces", name = "slider.js")
})
@FacesComponent(value = Slider.COMPONENT_TYPE, createTag = true, tagName = "slider", namespace = "http://tornadofaces.io/ui")
public class Slider<T extends Integer> extends UIPanel implements Widget, NamingContainer, ClientBehaviorHolder {
	public static final String COMPONENT_TYPE = "io.tornadofaces.component.Slider";

	public Slider() {
		super();
		setRendererType(SliderRenderer.RENDERER_TYPE);
	}

	public String getLowerClientId(FacesContext context) {
		return getClientId(context) + "_lower";
	}

	public String getUpperClientId(FacesContext context) {
		return getClientId(context) + "_upper";
	}
	
	public void decode(FacesContext context) {
		Map<String, String> params = context.getExternalContext().getRequestParameterMap();

		String lowerId = getLowerClientId(context);
		if (params.containsKey(lowerId)) {
			ValueExpression ve = getValueExpression("lower");
			if (ve != null) {
				Object newValue = params.get(lowerId);
				ve.setValue(context.getELContext(), newValue);
			}
		}

		String upperId = getUpperClientId(context);
		if (params.containsKey(upperId)) {
			ValueExpression upperEx = getValueExpression("upper");
			if (upperEx != null) {
				Object newValue = params.get(upperId);
				upperEx.setValue(context.getELContext(), newValue);
			}
		}

		String eventName = params.get("javax.faces.behavior.event");
		if ("change".equals(eventName)) {
			List<ClientBehavior> listeners = getClientBehaviors().get("change");
			if (listeners != null)
				for (ClientBehavior behavior : listeners)
					behavior.decode(context, this);
		}

	}

	public <X> X getData() {
		return (X) getStateHelper().eval("data");
	}

	public <X> void setData(X data) {
		getStateHelper().put("data", data);
	}

	public T getValue() {
		return getLower();
	}

	public void setValue(T value) {
		setLower(value);
	}

	public List<SliderRangeValue<T>> getRange() {
		return (List<SliderRangeValue<T>>) getStateHelper().eval("range");
	}

	public void setRange(List<SliderRangeValue<T>> range) {
		getStateHelper().put("range", range);
	}

	public T getMargin() {
		return (T) getStateHelper().eval("margin");
	}

	public void setMargin(T margin) {
		getStateHelper().put("margin", margin);
	}

	public T getLimit() {
		return (T) getStateHelper().eval("limit");
	}

	public void setLimit(T limit) {
		getStateHelper().put("limit", limit);
	}

	public T getMin() {
		return (T) getStateHelper().eval("min");
	}

	public void setMin(T min) {
		getStateHelper().put("min", min);
	}

	public T getMax() {
		return (T) getStateHelper().eval("max");
	}

	public void setMax(T max) {
		getStateHelper().put("max", max);
	}

	public String getConnect() {
		return (String) getStateHelper().eval("connect", null);
	}

	public void setConnect(String connect) {
		getStateHelper().put("connect", connect);
	}

	public T getLower() {
		return (T) getStateHelper().eval("lower");
	}

	public void setLower(T lower) {
		getStateHelper().put("lower", lower);
	}

	public T getUpper() {
		return (T) getStateHelper().eval("upper");
	}

	public void setUpper(T upper) {
		getStateHelper().put("upper", upper);
	}

	public T getStep() {
		return (T) getStateHelper().eval("step");
	}

	public void setStep(T step) {
		getStateHelper().put("step", step);
	}

	public Direction getDirection() {
		return (Direction) getStateHelper().eval("direction");
	}

	public void setDirection(Direction direction) {
		getStateHelper().put("direction", direction);
	}

	public Orientation getOrientation() {
		return (Orientation) this.getStateHelper().eval("orientation", null);
	}

	public void setOrientation(Orientation orientation) {
		this.getStateHelper().put("orientation", orientation);
	}

	public String getStyleClass() {
		return (String) getStateHelper().eval("styleClass");
	}

	public void setStyleClass(String styleClass) {
		getStateHelper().put("styleClass", styleClass);
	}

	public String getBehaviour() {
		return (String) getStateHelper().eval("behaviour");
	}

	public void setBehaviour(String behaviour) {
		getStateHelper().put("behaviour", behaviour);
	}

	public String getFormat() {
		return (String) getStateHelper().eval("format", "int");
	}

	public void setFormat(String format) {
		getStateHelper().put("format", format);
	}

	public Boolean getAnimate() {
		return (Boolean) getStateHelper().eval("animate");
	}

	public void setAnimate(Boolean animate) {
		getStateHelper().put("animate", animate);
	}

	public Boolean getSnap() {
		return (Boolean) getStateHelper().eval("snap");
	}
	public void setSnap(Boolean snap) {
		getStateHelper().put("snap", snap);
	}

	public Boolean getHeader() {
		return (Boolean) getStateHelper().eval("header", false);
	}
	public void setHeader(Boolean header) {
		getStateHelper().put("header", header);
	}

	public void setOnSlide(String onSlide) {
		getStateHelper().put("onSlide", onSlide);
	}

	public String getOnSlide() {
		return (String) getStateHelper().eval("onSlide");
	}

	public String getFamily() {
		return ComponentUtils.COMPONENT_FAMILY;
	}

	public String getWidgetVar() {
		return (String) getStateHelper().eval("widgetVar", null);
	}

	public void setWidgetVar(String widgetVar) {
		getStateHelper().put("widgetVar", widgetVar);
	}

	public String getLowerTarget() { return (String) getStateHelper().eval("lowerTarget", null); }
	public void setLowerTarget(String lowerTarget) { getStateHelper().put("lowerTarget", lowerTarget); }
	public String getUpperTarget() { return (String) getStateHelper().eval("upperTarget", null); }
	public void setUpperTarget(String upperTarget) { getStateHelper().put("upperTarget", upperTarget); }
	
	public JSONObject getSettings() {
		JSONObject settings = new JSONObject();

		// start
		JSONArray start = new JSONArray();
		start.put(getLower());
		T upper = getUpper();
		if (upper != null)
			start.put(upper);
		settings.put("start", start);

		// connect
		String connect = getConnect();
		if (connect != null) {
			if ("true".equals(connect) || "false".equals(connect))
				settings.put("connect", Boolean.valueOf(connect));
			else
				settings.put("connect", connect);
		}

		// range
		List<SliderRangeValue<T>> range = getRange();
		if (range == null)
			range = Collections.emptyList();

		JSONObject rangeObject = new JSONObject();

		rangeObject.put("min", getMin());

		if (!range.isEmpty()) {
			for (SliderRangeValue<T> r : range) {
				if (r.getIncrement() == null)
					rangeObject.put(r.getPct() + "%", r.getValue());
				else {
					JSONArray a = new JSONArray();
					a.put(r.getValue());
					a.put(r.getIncrement());
					rangeObject.put(r.getPct() + "%", a);
				}
			}
		}
		rangeObject.put("max", getMax());
		settings.put("range", rangeObject);
		settings.put("step", getStep());
		settings.put("snap", getSnap());
		settings.put("behaviour", getBehaviour());
		settings.put("format", getFormat());
		settings.put("limit", getLimit());
		settings.put("margin", getMargin());

		Orientation orientation = getOrientation();
		if (orientation != null)
			settings.put("orientation", orientation.toString());

		Direction direction = getDirection();
		if (direction != null)
			settings.put("direction", direction.toString());

		settings.put("animate", getAnimate());


		return settings;
	}

	public String getDefaultEventName() {
		return "change";
	}

	public Collection<String> getEventNames() {
		return Collections.singletonList("change");
	}
}