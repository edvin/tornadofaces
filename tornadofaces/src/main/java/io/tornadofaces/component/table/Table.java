package io.tornadofaces.component.table;

import io.tornadofaces.component.api.Widget;
import io.tornadofaces.component.tab.Tab;
import io.tornadofaces.component.util.ComponentUtils;
import io.tornadofaces.component.util.Constants;
import io.tornadofaces.event.SelectionEvent;
import io.tornadofaces.event.TabChangeEvent;

import javax.el.ValueExpression;
import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.FacesComponent;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.FacesEvent;
import java.util.*;

import static io.tornadofaces.component.util.ComponentUtils.isRequestSource;

@ResourceDependencies({
	@ResourceDependency(library = "tornadofaces", name = "highlight.js"),
	@ResourceDependency(library = "tornadofaces", name = "table.js")
})
@FacesComponent(value = Table.COMPONENT_TYPE, createTag = true, tagName = "table", namespace = "http://tornadofaces.io/ui")
public class Table extends HtmlDataTable implements Widget {
	public static final String COMPONENT_TYPE = "io.tornadofaces.component.Table";

	public enum ReflowMode { block, span }
	public enum RowSelectionMode { single, multiple }

	private List<Object> selectedRowKeys = new ArrayList<>();

	public Table() {
		super();
		setRendererType(TableRenderer.RENDERER_TYPE);
		setVar("it");
	}

	public String getFamily() {
		return ComponentUtils.COMPONENT_FAMILY;
	}

	public String getDefaultEventName() {
		return "rowSelect";
	}

	public Collection<String> getEventNames() {
		return Collections.singletonList(getDefaultEventName());
	}

	public String getStyleClass() { return (String) getStateHelper().eval("styleClass"); }
	public void setStyleClass(String styleClass) { getStateHelper().put("styleClass", styleClass); }
	public Object getRowKey() { return getStateHelper().eval("rowKey"); }
	public void setRowKey(Object rowKey) { getStateHelper().put("rowKey", rowKey); }
	public RowSelectionMode getSelectionMode() { return (RowSelectionMode) getStateHelper().eval("selectionMode"); }
	public void setSelectionMode(RowSelectionMode selectionMode) { getStateHelper().put("selectionMode", selectionMode); }
	public Object getSelection() { return getStateHelper().eval("selection"); }
	public void setSelection(Object selection) { getStateHelper().put("selection", selection); }
	public Boolean getReflow() { return (Boolean) getStateHelper().eval("reflow", true); }
	public void setReflow(Boolean reflow) { getStateHelper().put("reflow", reflow); }
	public Boolean getBordered() { return (Boolean) getStateHelper().eval("bordered"); }
	public ReflowMode getReflowMode() { return (ReflowMode) getStateHelper().eval("reflowMode"); }
	public void setReflowMode(ReflowMode reflowMode) { getStateHelper().put("reflowMode", reflowMode); }
	public void setBordered(Boolean bordered) { getStateHelper().put("bordered", bordered); }
	public Boolean getZebra() { return (Boolean) getStateHelper().eval("zebra"); }
	public void setZebra(Boolean zebra) { getStateHelper().put("zebra", zebra); }
	public Boolean getCompact() { return (Boolean) getStateHelper().eval("compact"); }
	public void setCompact(Boolean compact) { getStateHelper().put("compact", compact); }
	public String getWidgetVar() { return (String) getStateHelper().eval("widgetVar"); }
	public void setWidgetVar(String widgetVar) { getStateHelper().put("widgetVar", widgetVar); }
	public String getFilterFn() { return (String) getStateHelper().eval("filterFn"); }
	public void setFilterFn(String filterFn) { getStateHelper().put("filterFn", filterFn); }
	public Boolean getHighlightFilter() { return (Boolean) getStateHelper().eval("highlightFilter", true); }
	public void setHighlightFilter(Boolean highlightFilter) { getStateHelper().put("highlightFilter", highlightFilter); }

	/**
	 * Check if the current rowIndex represents a selected row
	 *
	 * @return True if the current rowIndex represents a selectedRow
	 */
	public boolean isRowSelected() {
		if (!isSelectionEnabled())
			return false;

		Object rowKey = getRowKey();

		return rowKey != null && selectedRowKeys.contains(rowKey);
	}

	/**
	 * Check if a selection mode is configured
	 *
	 * @return true if selection is enabled for this table
	 */
	public boolean isSelectionEnabled() {
		return getSelectionMode() != null;
	}

	/**
	 * Update the selectedRowKeys list based on the current selection
	 */
	public void updateSelectedRowKeys() {
		selectedRowKeys.clear();
		Object selection = getSelection();

		if (selection != null) {
			String var = getVar();
			Map<String, Object> requestMap = getFacesContext().getExternalContext().getRequestMap();

			if (selection instanceof List) {
				for (Object selected : (List) selection) {
					requestMap.put(var, selected);
					selectedRowKeys.add(getRowKey());
				}
			} else {
				requestMap.put(var, selection);
				selectedRowKeys.add(getRowKey());
			}

			requestMap.remove(var);
		}
	}

	/**
	 * Update selection based on selected row keys string from the request
	 * @param selectionString The request parameter containing the selection
	 */
	public void updateSelection(String selectionString) {
		List<String> requestSelectedRowKeys = Arrays.asList(selectionString.split(","));
		List values = (List) getValue();
		List<Object> newSelection = new ArrayList<>();
		if (values != null) {
			String var = getVar();
			Map<String, Object> requestMap = getFacesContext().getExternalContext().getRequestMap();
			for (Object value : values) {
				requestMap.put(var, value);
				if (requestSelectedRowKeys.contains(String.valueOf(getRowKey())))
					newSelection.add(value);
			}
			requestMap.remove(var);
		}

		Object setAsSelection = null;

		if (isSingleSelect()) {
			if (!newSelection.isEmpty())
				setAsSelection = newSelection.get(0);
		} else {
			setAsSelection = newSelection;
		}

		ValueExpression selectionExpr = getValueExpression("selection");

		if (selectionExpr != null)
			selectionExpr.setValue(getFacesContext().getELContext(), setAsSelection);
		else
			setSelection(setAsSelection);
	}

	private boolean isSingleSelect() {
		return RowSelectionMode.single.equals(getSelectionMode());
	}

	public void queueEvent(FacesEvent event) {
		FacesContext context = getFacesContext();

		if (isRequestSource(context, this) && event instanceof AjaxBehaviorEvent) {
			Map<String, String> params = context.getExternalContext().getRequestParameterMap();
			String eventName = params.get(Constants.RequestParams.PARTIAL_BEHAVIOR_EVENT_PARAM);
			String clientId = getClientId(context);
			AjaxBehaviorEvent behaviorEvent = (AjaxBehaviorEvent) event;

			if (eventName.equals("rowSelect")) {
				String selectedRowKey = params.get(clientId + "_selected");
				SelectionEvent se = new SelectionEvent(this, behaviorEvent.getBehavior(), getRowData(selectedRowKey));
				se.setPhaseId(behaviorEvent.getPhaseId());
				super.queueEvent(se);
				return;
			}
		}

		super.queueEvent(event);
	}

	/**
	 * Get the data matching the specified row key
	 * @param rowKey The row key
	 * @return The data object matching the row key
	 */
	private Object getRowData(String rowKey) {
		String var = getVar();
		Map<String, Object> requestMap = getFacesContext().getExternalContext().getRequestMap();
		for (Object value : (List) getValue()) {
			requestMap.put(var, value);
			if (rowKey.equals(String.valueOf(getRowKey())))
				return value;
		}
		requestMap.remove(var);
		return null;
	}

}