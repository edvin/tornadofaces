package io.tornadofaces.component.input;

import io.tornadofaces.component.util.GlobalId;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputSecret;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlInputTextarea;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagConfig;
import javax.faces.view.facelets.TagHandler;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Work in progress / experimentation only
 */
public class Input extends TagHandler {
	public static final List<String> instructionAttrs = Arrays.asList(
		"label", "type", "small", "medium", "large", "layout", "prefix", "suffix", "styleClass"
	);

	public Input(TagConfig config) {
		super(config);
	}

	public void apply(FaceletContext ctx, UIComponent parent) throws IOException {
		TagAttribute typeAttr = getAttribute("type");
		String type = typeAttr == null ? "text" : typeAttr.getValue();

		UIComponent input;

		FormElement formElem = new FormElement();

		switch (type) {
			case "password":
				input = new HtmlInputSecret();
				break;
			case "textarea":
				input = new HtmlInputTextarea();
				break;
			default:
				input = new HtmlInputText();
		}

		for (TagAttribute attr : tag.getAttributes().getAll()) {
			String key = attr.getLocalName();
			
			if (instructionAttrs.contains(key)) {
				if ("label".equals(key))
					setAttr(ctx, key, attr, input);

				setAttr(ctx, key, attr, formElem);
			} else {
				if ("inputStyleClass".equals(key))
					setAttr(ctx, "styleClass", attr, input);
				else if ("placeholder".equals(key))
					input.getPassThroughAttributes().put("placeholder", attr.getValue());
				else
					setAttr(ctx, key, attr, input);
			}
		}

		TagAttribute gid = getAttribute("gid");
		if (gid != null)
			GlobalId.register(ctx.getFacesContext(), input, gid.getValue());

		Object id = input.getAttributes().get("id");
		if (id != null)
			formElem.getAttributes().put("id", id.toString() + "_wrapper");

		if (gid != null)
			GlobalId.register(ctx.getFacesContext(), input, gid.getValue() + "_wrapper");

		formElem.getChildren().add(input);
		parent.getChildren().add(formElem);
	}
	
	public void setAttr(FaceletContext ctx, String name, TagAttribute attr, UIComponent target) {
		String value = attr.getValue(ctx);
		target.getAttributes().put(name, value);


//		value = new Object[] { ctx.getExpressionFactory().coerceToType(str, method.getParameterTypes()[0]) };
//
//		try {
//			method.invoke(instance, this.value);
//		} catch (InvocationTargetException e) {
//			throw new TagAttributeException(this.attribute, e.getCause());
//		} catch (Exception e) {
//			throw new TagAttributeException(this.attribute, e);
//		}


//		if (targetExpr != null) {
//			ValueExpression srcExpr = attr.getValueExpression(ctx, targetExpr.getType(elContext));
//			targetExpr.setValue(elContext, value);
//		} else {
//			ExpressionFactory exprFactory = ctx.getFacesContext().getApplication().getExpressionFactory();
//			targetExpr = exprFactory.createValueExpression(elContext, "#{" + name + "}", value.getClass());
//			target.setValueExpression(name, targetExpr);
//			targetExpr.setValue(elContext, value);
//		}
	}
}
