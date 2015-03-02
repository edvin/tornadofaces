package io.tornadofaces.renderkit;

import javax.faces.context.FacesContext;
import javax.faces.context.PartialResponseWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TFPartialResponseWriter extends PartialResponseWriter {
	private static final Map<String, String> extensionMap = new HashMap<>();
	
	static {
		extensionMap.put("id", "tornadofaces");
	}
	
	public TFPartialResponseWriter(PartialResponseWriter wrapped) {
		super(wrapped);
	}

	public void endDocument() throws IOException {
		startExtension(extensionMap);
		write("{\"validationFailed\": " + FacesContext.getCurrentInstance().isValidationFailed() + "}");
		endExtension();
		super.endDocument();
	}
}
