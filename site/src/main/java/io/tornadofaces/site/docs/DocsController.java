package io.tornadofaces.site.docs;

import io.tornadofaces.site.taglib.Tag;
import lombok.Getter;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Model
public class DocsController {
	@Inject DocumentationCache cache;
	@Getter private Tag tag;
	@Getter private List<Movie> movies;

	public DocsController() {
		movies = Arrays.asList(
			new Movie(1, "Star Wars: Episode I - The Phantom Menace"),
			new Movie(2, "Star Wars: Episode II - Attack of the Clones"),
			new Movie(3, "Star Wars: Episode III - Revenge of the Sith"));
	}

	public void loadTag(String tagName) throws IOException {
		this.tag = cache.getTag(tagName);
	}

	public String expr(String expr) {
		return "#{" + expr + "}";
	}
}
