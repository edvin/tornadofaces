package io.tornadofaces.renderkit;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.application.Resource;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;
import javax.servlet.ServletContext;
import java.io.IOException;
import java.util.ListIterator;

import static java.lang.String.format;

/**
 * Code copied from PrimeFaces and modified
 * <p>
 * Renders head content based on the following order
 * - First Facet
 * - Theme CSS
 * - JSF Ajax
 * - JQuery
 * - TornadoFaces JS
 * - PrimeFaces JS (for compatibility in projects that have both libs)
 * - Registered Resources
 * - Head Content
 * - Last Facet
 */
public class HeadRenderer extends Renderer {

	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		writer.startElement("head", component);

		UIComponent first = component.getFacet("first");
		if (first != null) {
			first.encodeAll(context);
		}

		appendTornadoFacesResources(context);

		UIComponent middle = component.getFacet("middle");
		if (middle != null)
			middle.encodeAll(context);

		UIViewRoot viewRoot = context.getViewRoot();
		for (UIComponent resource : viewRoot.getComponentResources(context, "head"))
			resource.encodeAll(context);
	}

	/**
	 * Get a config value by first looking at ViewRoot attributes, and then for init parameters.
	 * <p>
	 * If a value is found, it is resolved as an EL expression
	 * *
	 *
	 * @param context The current FacesContext
	 * @param key     The key to lookup
	 * @return The resolved value or null if not found
	 */
	private String getConfigValue(FacesContext context, String key, String defaultValue) {
		UIViewRoot viewRoot = context.getViewRoot();
		Object value = viewRoot.getAttributes().get(key);

		if (value == null)
			value = context.getExternalContext().getInitParameter(key);

		if (value != null) {
			ELContext elContext = context.getELContext();
			ExpressionFactory expressionFactory = context.getApplication().getExpressionFactory();
			ValueExpression ve = expressionFactory.createValueExpression(elContext, value.toString(), String.class);

			return (String) ve.getValue(elContext);
		}

		return defaultValue;
	}

	private void appendTornadoFacesResources(FacesContext context) throws IOException {
		if ("true".equals(getConfigValue(context, "tornadofaces.DISABLE", "false")))
			return;

		String theme = getConfigValue(context, "tornadofaces.THEME", "tornado");

		if (!theme.equals("none"))
			encodeCSS(context, "tornadofaces-" + theme, "theme.css");

		if (!hasResourceBeenInstalled(context, "javax.faces", "jsf.js"))
			encodeJS(context, "javax.faces", "jsf.js");

		if (!"true".equals(getConfigValue(context, "tornadofaces.SKIP_JQUERY", "false")))
			encodeJS(context, "tornadofaces", "jquery.min.js");

		encodeJS(context, "tornadofaces", "tornadofaces.js");
	}

	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		ResponseWriter writer = context.getResponseWriter();

		UIComponent last = component.getFacet("last");
		if (last != null)
			last.encodeAll(context);

		writer.endElement("head");
	}

	protected void encodeCSS(FacesContext context, String library, String resource) throws IOException {
		ResponseWriter writer = context.getResponseWriter();

		ServletContext ctx = (ServletContext) context.getExternalContext().getContext();

		writer.startElement("link", null);
		writer.writeAttribute("type", "text/css", null);
		writer.writeAttribute("rel", "stylesheet", null);
		writer.writeAttribute("href", format("%s/javax.faces.resource/%s.xhtml?ln=%s", ctx.getContextPath(), resource, library), null);
		writer.endElement("link");
	}

	protected void encodeJS(FacesContext context, String library, String resource) throws IOException {
		ResponseWriter writer = context.getResponseWriter();

		Resource jsResource = context.getApplication().getResourceHandler().createResource(resource, library);

		if (jsResource == null)
			throw new FacesException("Error loading js, cannot find \"" + resource + "\" resource of \"" + library + "\" library");

		else {
			writer.startElement("script", null);
			writer.writeAttribute("src", jsResource.getRequestPath(), null);
			writer.endElement("script");
		}
	}

	public static boolean hasResourceBeenInstalled(FacesContext ctx,
	                                               String name,
	                                               String library) {

		UIViewRoot viewRoot = ctx.getViewRoot();
		ListIterator iter = (viewRoot.getComponentResources(ctx, "head")).listIterator();
		while (iter.hasNext()) {
			UIComponent resource = (UIComponent) iter.next();
			String rname = (String) resource.getAttributes().get("name");
			String rlibrary = (String) resource.getAttributes().get("library");
			if (name.equals(rname) && library.equals(rlibrary)) {
				// Set the context to record script as included
				return true;
			}
		}
		iter = (viewRoot.getComponentResources(ctx, "body")).listIterator();
		while (iter.hasNext()) {
			UIComponent resource = (UIComponent) iter.next();
			String rname = (String) resource.getAttributes().get("name");
			String rlibrary = (String) resource.getAttributes().get("library");
			if (name.equals(rname) && library.equals(rlibrary)) {
				// Set the context to record script as included
				return true;
			}
		}
		iter = (viewRoot.getComponentResources(ctx, "form")).listIterator();
		while (iter.hasNext()) {
			UIComponent resource = (UIComponent) iter.next();
			String rname = (String) resource.getAttributes().get("name");
			String rlibrary = (String) resource.getAttributes().get("library");
			if (name.equals(rname) && library.equals(rlibrary)) {
				// Set the context to record script as included
				return true;
			}
		}

		return false;

	}

}