package io.tornadofaces.component.table;

import io.tornadofaces.component.api.Widget;
import io.tornadofaces.component.column.Column;
import io.tornadofaces.component.util.ComponentUtils;
import io.tornadofaces.component.util.Constants;
import io.tornadofaces.event.SelectionEvent;

import javax.el.ValueExpression;
import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitResult;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.FacesEvent;
import java.util.*;

import static io.tornadofaces.component.util.ComponentUtils.getRequestParam;
import static io.tornadofaces.component.util.ComponentUtils.isRequestSource;

@ResourceDependencies({
	@ResourceDependency(library = "tornadofaces", name = "highlight.js"),
	@ResourceDependency(library = "tornadofaces", name = "table.js")
})
@FacesComponent(value = Table.COMPONENT_TYPE, createTag = true, tagName = "table", namespace = "http://tornadofaces.io/ui")
public class Table extends HtmlDataTable implements Widget {
	public static final String COMPONENT_TYPE = "io.tornadofaces.component.Table";

	public long getColumnCount() {
		return getChildren().stream().filter(c -> c instanceof Column).count();
	}

	public boolean isEmpty() {
		Object list = getValue();

		if (list == null)
			return true;

		if (list instanceof List)
			return ((List) list).isEmpty();

		if (list instanceof Object[])
			return ((Object[])list).length == 0;

		return false;
	}

	public enum ReflowAtSize { small, medium, large, reflow, none }
	public enum ReflowMode { block, span }
	public enum RowSelectionMode { single, multi }

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
		return Arrays.asList("rowSelect", "rowToggle");
	}

	public String getStyleClass() { return (String) getStateHelper().eval("styleClass"); }
	public void setStyleClass(String styleClass) { getStateHelper().put("styleClass", styleClass); }
	public String getEmptyText() { return (String) getStateHelper().eval("emptyText", "No data"); }
	public void setEmptyText(String emptyText) { getStateHelper().put("emptyText", emptyText); }
	public String getRowClass() { return (String) getStateHelper().eval("rowClass"); }
	public void setRowClass(String rowClass) { getStateHelper().put("rowClass", rowClass); }
	public Object getRowKey() { return getStateHelper().eval("rowKey"); }
	public void setRowKey(Object rowKey) { getStateHelper().put("rowKey", rowKey); }
	public RowSelectionMode getSelectionMode() { return (RowSelectionMode) getStateHelper().eval("selectionMode"); }
	public void setSelectionMode(RowSelectionMode selectionMode) { getStateHelper().put("selectionMode", selectionMode); }
	public Object getSelection() { return getStateHelper().eval("selection"); }
	public void setSelection(Object selection) { getStateHelper().put("selection", selection); }
	public ReflowAtSize getReflow() { return (ReflowAtSize) getStateHelper().eval("reflow", ReflowAtSize.small); }
	public void setReflow(ReflowAtSize reflow) { getStateHelper().put("reflow", reflow); }
	public Boolean getBordered() { return (Boolean) getStateHelper().eval("bordered"); }
	public ReflowMode getReflowMode() { return (ReflowMode) getStateHelper().eval("reflowMode"); }
	public void setReflowMode(ReflowMode reflowMode) { getStateHelper().put("reflowMode", reflowMode); }
	public void setBordered(Boolean bordered) { getStateHelper().put("bordered", bordered); }
	public Boolean getZebra() { return (Boolean) getStateHelper().eval("zebra"); }
	public void setZebra(Boolean zebra) { getStateHelper().put("zebra", zebra); }
	public Boolean getHover() { return (Boolean) getStateHelper().eval("hover"); }
	public void setHover(Boolean hover) { getStateHelper().put("hover", hover); }
	public Boolean getCompact() { return (Boolean) getStateHelper().eval("compact"); }
	public void setCompact(Boolean compact) { getStateHelper().put("compact", compact); }
	public String getWidgetVar() { return (String) getStateHelper().eval("widgetVar"); }
	public void setWidgetVar(String widgetVar) { getStateHelper().put("widgetVar", widgetVar); }
	public String getFilterFn() { return (String) getStateHelper().eval("filterFn"); }
	public void setFilterFn(String filterFn) { getStateHelper().put("filterFn", filterFn); }
	public Boolean getHighlightFilter() { return (Boolean) getStateHelper().eval("highlightFilter", true); }
	public void setHighlightFilter(Boolean highlightFilter) { getStateHelper().put("highlightFilter", highlightFilter); }
	public Boolean getClickFirstLinkOnEnter() { return (Boolean) getStateHelper().eval("clickFirstLinkOnEnter", true); }
	public void setClickFirstLinkOnEnter(Boolean clickFirstLinkOnEnter) { getStateHelper().put("clickFirstLinkOnEnter", clickFirstLinkOnEnter); }
	public String getOnEnterPressed() { return (String) getStateHelper().eval("onEnterPressed"); }
	public void setOnEnterPressed(String onEnter) { getStateHelper().put("onEnterPressed", onEnter); }
	public String getOnSelectRow() { return (String) getStateHelper().eval("onSelectRow"); }
	public void setOnSelectRow(String onEnter) { getStateHelper().put("onSelectRow", onEnter); }

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

			String expandedRowKey = getRequestedExpandRowKey(context);
			if (expandedRowKey != null)
				setRowIndexForRowKey(expandedRowKey);

			if (eventName.equals("rowSelect")) {
				String selectedRowKey = params.get(clientId + "_selected");
				setRowIndexForRowKey(selectedRowKey);
				SelectionEvent se = new SelectionEvent(this, behaviorEvent.getBehavior(), getRowData());
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
	public Object getRowData(String rowKey) {
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

	/**
	 * Set the row index to the row that corresponds to the given rowKey
	 * @param rowKey The row key to look up
	 */
	public void setRowIndexForRowKey(String rowKey) {
		String var = getVar();
		Map<String, Object> requestMap = getFacesContext().getExternalContext().getRequestMap();
		int rowIndex = 0;
		for (Object value : (List) getValue()) {
			requestMap.put(var, value);
			if (rowKey.equals(String.valueOf(getRowKey()))) {
				setRowIndex(rowIndex);
				break;
			}
			rowIndex++;
		}
		requestMap.remove(var);
	}

	public String getRequestedExpandRowKey(FacesContext context) {
		return getRequestParam(context, getClientId(context) + "_expand");
	}

	public boolean visitTree(VisitContext context, VisitCallback callback) {
		// Perform UIComponent's version of visitTree, then UIData's visitTree. If we don't,
		// the RowExpansion will not be found, so it won't be rendered. This should be better
		// understood and optimized.

		if (!isVisitable(context))
			return false;

		// Push ourselves to EL before visiting
		FacesContext facesContext = context.getFacesContext();
		pushComponentToEL(facesContext, null);

		try {
			// Visit ourselves.  Note that we delegate to the
			// VisitContext to actually perform the visit.
			VisitResult result = context.invokeVisitCallback(this, callback);

			// If the visit is complete, short-circuit out and end the visit
			if (result == VisitResult.COMPLETE)
				return true;

			// Visit children if necessary
			if (result == VisitResult.ACCEPT) {
				Iterator<UIComponent> kids = this.getFacetsAndChildren();

				while(kids.hasNext()) {
					boolean done = kids.next().visitTree(context, callback);

					// If any kid visit returns true, we are done.
					if (done)
						return true;
				}
			}
		}
		finally {
			// Pop ourselves off the EL stack
			popComponentFromEL(facesContext);
		}

		return super.visitTree(context, callback);
	}
}