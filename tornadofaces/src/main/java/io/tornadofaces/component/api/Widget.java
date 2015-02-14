/*
 * Copyright 2009-2014 PrimeTek.
 *
 * Licensed under PrimeFaces Commercial License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.primefaces.org/elite/license.xhtml
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.tornadofaces.component.api;

import javax.faces.component.UIComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;

public interface Widget {

    default String getWidgetClass() {
        return getClass().getSimpleName();
    }

    default String resolveWidgetVar(FacesContext context) {
        UIComponent component = (UIComponent) this;
        String userWidgetVar = (String) component.getAttributes().get("widgetVar");

        if (userWidgetVar != null)
            return userWidgetVar;
        else
            return component.getClientId(context).replaceAll("-|" + UINamingContainer.getSeparatorChar(context), "_");
    }

    String getWidgetVar();
    void setWidgetVar(String widgetVar);
}
