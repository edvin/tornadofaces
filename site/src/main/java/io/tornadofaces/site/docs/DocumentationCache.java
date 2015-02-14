package io.tornadofaces.site.docs;

import io.tornadofaces.site.taglib.Tag;
import io.tornadofaces.site.taglib.Taglib;

import javax.enterprise.context.ApplicationScoped;
import javax.xml.bind.JAXB;
import java.io.IOException;
import java.io.InputStream;

@ApplicationScoped
public class DocumentationCache {
	private Taglib taglib;

	public Tag getTag(String tagName) {
		if (taglib == null) {
			try (InputStream input = getClass().getResourceAsStream("/META-INF/tornadofaces.taglib.xml")) {
				taglib = JAXB.unmarshal(input, Taglib.class);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return taglib.getTags().stream().filter(t -> t.getTagName().equals(tagName)).findFirst().get();
	}
}
