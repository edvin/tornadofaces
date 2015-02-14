/*
 * Copyright 2009-2014 PrimeTek.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.tornadofaces.util;

import io.tornadofaces.component.api.Widget;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import java.io.IOException;

/**
 * Helper to generate javascript code of an ajax call
 */
@SuppressWarnings("UnusedDeclaration")
public class WidgetBuilder {
    private Boolean endFunction = false;
    private FacesContext context;
    private ResponseWriter output;
    private Widget widget;

    public WidgetBuilder(FacesContext context, Widget widget) {
        if (!(widget instanceof UIComponent))
            throw new IllegalArgumentException("All Widgets must extend UIComponent");

        this.output = context.getResponseWriter();
        this.context = context;
        this.widget = widget;
    }

    public WidgetBuilder init() throws IOException {
        renderScriptBlock();
        UIComponent component = (UIComponent) widget;

        output.write("TornadoFaces.cw(\"");
        output.write(widget.getWidgetClass());
        output.write("\",{id:\"" + component.getClientId(context) + "\"");

        attr("widgetVar", widget.resolveWidgetVar(context));

        return this;
    }

    public WidgetBuilder initWithDomReady() throws IOException {
        this.endFunction = true;
    	this.renderScriptBlock();
    	output.write("$(function(){");
    	this.init();
        return this;
    }

    public WidgetBuilder initWithWindowLoad() throws IOException {
        this.endFunction = true;
        this.renderScriptBlock();
    	output.write("$(window).load(function(){");
    	this.init();
        return this;
    }

    public WidgetBuilder initWithComponentLoad(String targetId) throws IOException {
        this.endFunction = true;
    	this.renderScriptBlock();
    	output.write("$(TornadoFaces.escapeClientId(\"" + targetId + "\")).load(function(){");
    	this.init();
        return this;
    }

    private void renderScriptBlock() throws IOException {
        output.startElement("script", null);
        String id = ((UIComponent) widget).getClientId();
        output.writeAttribute("id", id + "_s", null);
    }

    public WidgetBuilder attr(String name, String value) throws IOException {
        if (value != null) {
            output.write(",");
            output.write(name);
            output.write(":\"");
        	output.write(value);
            output.write("\"");
        }

        return this;
    }
    
    public WidgetBuilder nativeAttr(String name, String value) throws IOException {
        if (value != null) {
            output.write(",");
            output.write(name);
            output.write(":");
        	output.write(value);
        }
        
        return this;
    }
    
    public WidgetBuilder nativeAttr(String name, String value, String defaultValue) throws IOException {
        if(value != null && !value.equals(defaultValue)) {
            output.write(",");
            output.write(name);
            output.write(":");
            output.write(value);
        }
        
        return this;
    }

    public WidgetBuilder attr(String name, Boolean value) throws IOException {
        if (value != null) {
            output.write(",");
            output.write(name);
            output.write(":");
            output.write(Boolean.toString(value));
        }
        
        return this;
    }
    
    public WidgetBuilder attr(String name, Number value) throws IOException {
        if (value != null) {
            output.write(",");
            output.write(name);
            output.write(":");
        	output.write(value.toString());
        }
        
        return this;
    }
        
    public WidgetBuilder attr(String name, String value, String defaultValue) throws IOException {
        if(value != null && !value.equals(defaultValue)) {
            output.write(",");
	        output.write(name);
	        output.write(":\"");
	        output.write(value);
	        output.write("\"");
        }
        
        return this;
    }
    
    public WidgetBuilder attr(String name, double value, double defaultValue) throws IOException {
        if(value != defaultValue) {
            output.write(",");
	        output.write(name);
	        output.write(":");
	        output.write(Double.toString(value));
        }
        
        return this;
    }
    
    public WidgetBuilder attr(String name, int value, int defaultValue) throws IOException {
        if(value != defaultValue) {
            output.write(",");
	        output.write(name);
	        output.write(":");
	        output.write(Integer.toString(value));
        }
        
        return this;
    }
        
    public WidgetBuilder attr(String name, boolean value, boolean defaultValue) throws IOException {
        if(value != defaultValue) {
            output.write(",");
	        output.write(name);
	        output.write(":");
	        output.write(Boolean.toString(value));
        }
        
        return this;
    }
    
    public WidgetBuilder callback(String name, String signature, String callback) throws IOException {
        if(callback != null) {
            output.write(",");
	        output.write(name);
	        output.write(":");
	        output.write(signature);
	        output.write("{");
	        output.write(callback);
	        output.write("}");
        }
        
        return this;
    }
    
    public WidgetBuilder callback(String name, String callback) throws IOException {
        if(callback != null) {
            output.write(",");
	        output.write(name);
	        output.write(":");
	        output.write(callback);
        }
        
        return this;
    }

    public WidgetBuilder append(String str) throws IOException {
    	output.write(str);
        
        return this;
    }

    public WidgetBuilder append(char chr) throws IOException {
    	output.write(chr);
        
        return this;
    }

    public WidgetBuilder append(Number number) throws IOException {
    	output.write(number.toString());
        
        return this;
    }
    
    public void finish() throws IOException {
        output.write("});");

        if(endFunction)
            output.write("});");

        output.endElement("script");
    }
}
