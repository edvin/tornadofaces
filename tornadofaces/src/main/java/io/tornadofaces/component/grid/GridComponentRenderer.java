package io.tornadofaces.component.grid;

import io.tornadofaces.component.CoreRenderer;
import io.tornadofaces.component.util.ComponentUtils;
import io.tornadofaces.component.util.StyleClass;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import java.io.IOException;

@FacesRenderer(rendererType = GridComponentRenderer.RENDERER_TYPE, componentFamily = ComponentUtils.COMPONENT_FAMILY)
public class GridComponentRenderer extends CoreRenderer {
	public static final String RENDERER_TYPE = "io.tornadofaces.component.GridComponentRenderer";

	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		writer.startElement("div", component);
		GridComponent gridComponent = (GridComponent) component;

		String id = component.getId();
		if (!id.startsWith("j_idt"))
			writer.writeAttribute("id", component.getClientId(), null);
		
		Integer small = gridComponent.getSmall();
		Integer medium = gridComponent.getMedium();
		Integer large = gridComponent.getLarge();
		
		StyleClass sc = StyleClass.of(gridComponent.getComponentStyleClass())
			.add(gridComponent.getStyleClass());
		
		if (small != null)
			sc.add("small-" + small);
		
		if (medium != null)
			sc.add("medium-" + medium);
		
		if (large != null)
			sc.add("large-" + large);
		
		if (gridComponent instanceof GridBlock) {
			GridBlock gridblock = (GridBlock) gridComponent;
			sc.add(gridblock.getOrientation());
		}
		
		sc.write(writer);
	}

	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		writer.endElement("div");
	}
}