package io.tornadofaces.component.list;

import javax.faces.component.FacesComponent;

@FacesComponent(value = Breadcrumbs.COMPONENT_TYPE, createTag = true, tagName = "breadcrumbs", namespace = "http://tornadofaces.io/ui")
public class Breadcrumbs extends Ul {
	public static final String COMPONENT_TYPE = "io.tornadofaces.component.Breadcrumbs";

	public Breadcrumbs() {
		super();
		setBreadcrumb(true);
	}
}
