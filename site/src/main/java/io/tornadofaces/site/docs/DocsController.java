package io.tornadofaces.site.docs;

import io.tornadofaces.component.accordion.Accordion;
import io.tornadofaces.component.notification.NotificationMessage;
import io.tornadofaces.component.tab.Tab;
import io.tornadofaces.event.FlipPanelEvent;
import io.tornadofaces.event.SelectionEvent;
import io.tornadofaces.event.SwitchEvent;
import io.tornadofaces.event.TabChangeEvent;
import io.tornadofaces.site.taglib.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
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
	@Getter @Setter private List<Movie> selectedMovies;
	@Getter @Setter Integer lower = 15;
	@Getter @Setter Movie selectedMovie;
	@Getter @Setter Boolean showPanel = false;

	public void runCommand() {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_INFO, "Remote command run!", "Execution was successful."));
	}

	public void onTabChange(AjaxBehaviorEvent e) {
		TabChangeEvent event = (TabChangeEvent) e;
		Accordion accordion = (Accordion) event.getSource();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_WARN, "Tab changed", "Active: " + accordion.getActiveIndex()));
	}
	
	public void episodeTwoActivated(AjaxBehaviorEvent e) {
		System.out.println("Episode two activated!");
	}
	
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
			new Movie(1, "Star Wars: Episode I - The Phantom Menace", "Two Jedi Knights escape a hostile blockade to find allies and come across a young boy who may bring balance to the Force, but the long dormant Sith resurface to reclaim their old glory."),
			new Movie(2, "Star Wars: Episode II - Attack of the Clones", "Ten years after initially meeting, Anakin Skywalker shares a forbidden romance with Padmé, while Obi-Wan investigates an assassination attempt on the Senator and discovers a secret clone army crafted for the Jedi."),
			new Movie(3, "Star Wars: Episode III - Revenge of the Sith", "As the Clone Wars near an end, the Sith Lord Darth Sidious steps out of the shadows, at which time Anakin succumbs to his emotions, becoming Darth Vader and putting his relationships with Obi-Wan and Padme at risk."));

		selectedMovies = new ArrayList<>();
		selectedMovies.add(movies.get(1));
	}

	public void onSelectMovie(AjaxBehaviorEvent event) {
		SelectionEvent<Movie> selectionEvent = (SelectionEvent<Movie>) event;
		Movie movie = selectionEvent.getSelection();

		FacesMessage message = new FacesMessage(SEVERITY_INFO, "Selected Movie", movie.getTitle());
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void takeTwo() throws InterruptedException {
		TimeUnit.MILLISECONDS.sleep(1000);
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

	public List<BankStatementEntry> getBankStatement() {
		return Arrays.asList(
			new BankStatementEntry(10, LocalDate.of(2015, 1, 1), 450),
			new BankStatementEntry(20, LocalDate.of(2015, 2, 1), 550),
			new BankStatementEntry(30, LocalDate.of(2015, 3, 1), 850),
			new BankStatementEntry(40, LocalDate.of(2015, 4, 1), 1450),
			new BankStatementEntry(50, LocalDate.of(2015, 5, 1), 1850),
			new BankStatementEntry(60, LocalDate.of(2015, 6, 1), 1200),
			new BankStatementEntry(70, LocalDate.of(2015, 7, 1), 800),
			new BankStatementEntry(80, LocalDate.of(2015, 8, 1), 300)
		);
	}

	@Getter
	@Setter
	@AllArgsConstructor
	public static class BankStatementEntry {
		private Integer id;
		private LocalDate date;
		private Integer balance;

		public Long getDateEpoch() {
			return date.atStartOfDay().toEpochSecond(ZoneOffset.UTC) * 1000;
		}

		public Integer getRandomBalance() {
			return new Double(balance + Math.random() * balance).intValue();
		}
	}
}
