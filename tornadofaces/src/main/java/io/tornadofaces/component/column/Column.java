package io.tornadofaces.component.column;

import io.tornadofaces.component.table.RowToggler;
import io.tornadofaces.component.table.Table;
import io.tornadofaces.component.util.ComponentUtils;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIColumn;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import java.io.IOException;

@FacesComponent(value = Column.COMPONENT_TYPE, createTag = true, tagName = "column", namespace = "http://tornadofaces.io/ui")
public class Column extends UIColumn {
	public static final String COMPONENT_TYPE = "io.tornadofaces.component.Column";

	public Column() {
		super();
		setRendererType(null);
	}

	public String getFamily() {
		return ComponentUtils.COMPONENT_FAMILY;
	}

	public String getStyleClass() { return (String) getStateHelper().eval("styleClass"); }
	public void setStyleClass(String styleClass) { getStateHelper().put("styleClass", styleClass); }
	public String getHeaderClass() { return (String) getStateHelper().eval("headerClass"); }
	public void setHeaderClass(String headerClass) { getStateHelper().put("headerClass", headerClass); }
	public String getFooterClass() { return (String) getStateHelper().eval("footerClass"); }
	public void setFooterClass(String footerClass) { getStateHelper().put("footerClass", footerClass); }
	public String getStyle() { return (String) getStateHelper().eval("style"); }
	public void setStyle(String style) { getStateHelper().put("style", style); }
	public Object getText() { return getStateHelper().eval("text"); }
	public void setText(Object text) { getStateHelper().put("text", text); }
	public Boolean getEscape() { return (Boolean) getStateHelper().eval("escape", true); }
	public void setEscape(Boolean escape) { getStateHelper().put("escape", escape); }
	public Boolean getReflow() { return (Boolean) getStateHelper().eval("reflow", true); }
	public void setReflow(Boolean reflow) { getStateHelper().put("reflow", reflow); }
	public String getHeaderText() { return (String) getStateHelper().eval("headerText"); }
	public Table.ReflowMode getReflowMode() { return (Table.ReflowMode) getStateHelper().eval("reflowMode"); }
	public void setReflowMode(Table.ReflowMode reflowMode) { getStateHelper().put("reflowMode", reflowMode); }
	public void setHeaderText(String headerText) { getStateHelper().put("headerText", headerText); }
	public String getFootertext() { return (String) getStateHelper().eval("footerText"); }
	public void setFootertext(String footerText) { getStateHelper().put("footerText", footerText); }

	public void encodeChildren(FacesContext context) throws IOException {
		for (UIComponent child : getChildren())
			child.encodeAll(context);
	}

	public boolean containsRowToggler() {
		return getChildren().stream().filter(c -> c instanceof RowToggler).findAny().isPresent();
	}
}
