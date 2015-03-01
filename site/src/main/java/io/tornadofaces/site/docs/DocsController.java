package io.tornadofaces.site.docs;

import io.tornadofaces.event.FlipPanelEvent;
import io.tornadofaces.event.SwitchEvent;
import io.tornadofaces.site.taglib.Tag;
import lombok.Getter;
import lombok.Setter;

import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static javax.faces.application.FacesMessage.SEVERITY_INFO;
import static javax.faces.application.FacesMessage.SEVERITY_WARN;

@Model
public class DocsController {
	@Inject DocumentationCache cache;
	@Getter private Tag tag;
	@Getter private List<Movie> movies;
	@Getter @Setter Integer lower = 15;
	
	public void onFlip(AjaxBehaviorEvent e) {
		FlipPanelEvent event = (FlipPanelEvent) e;
		Boolean isFlipped = event.getFlipPanel().isFlipped();
		Severity severity = isFlipped ? SEVERITY_INFO : SEVERITY_WARN;
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, "Flip status", isFlipped + ""));
	}

	public void onSwitch(AjaxBehaviorEvent e) {
		SwitchEvent event = (SwitchEvent) e;
		Boolean isChecked = event.getSwitch().isChecked();
		Severity severity = isChecked ? SEVERITY_INFO : SEVERITY_WARN;
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, "Switch status", isChecked + ""));
	}
	
	public DocsController() {
		movies = Arrays.asList(
			new Movie(1, "Star Wars: Episode I - The Phantom Menace"),
			new Movie(2, "Star Wars: Episode II - Attack of the Clones"),
			new Movie(3, "Star Wars: Episode III - Revenge of the Sith"));
	}

	public void takeTwo() throws InterruptedException {
		TimeUnit.SECONDS.sleep(2);
	}
	
	public void loadTag(String tagName) throws IOException {
		this.tag = cache.getTag(tagName);
	}

	public String expr(String expr) {
		return "#{" + expr + "}";
	}

	public String getNow() {
		return new Date().toString();
	}
}
