package io.tornadofaces.site;

import io.tornadofaces.component.card.Card;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Named
@ViewScoped
public class Index implements Serializable {
	private String name;
	private Integer progress;
	private List<Person> people;
	private Boolean mybool;
	private String ranges;
	private Date mydate;

	public Index() {
		people = new ArrayList<>();
		people.add(new Person(1, "Edvin Syse"));
		people.add(new Person(2, "Maisen Syse-Sender"));
		people.add(new Person(3, "Alyssa Syse-Sender"));
		people.add(new Person(4, "Pippi Syse-Sender"));
		name = "Alyssa Syse-Sender";
		progress = 50;
		mybool = true;
		mydate = new Date();
	}

	public Date getDate() {
		return new Date();
	}

	public void removePerson(Person person) {
		people.remove(person);
	}

	public void showNotification() {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Notification", "Some message"));
	}

	public void save() {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Saved", String.format("Saved %s, mybool is %s and progress is %s and my date is %s", name, mybool, progress, mydate)));
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getProgress() {
		return progress;
	}

	public void setProgress(Integer progress) {
		this.progress = progress;
	}

	public List<Person> getPeople() {
		return people;
	}

	public Boolean getMybool() {
		return mybool;
	}

	public void setMybool(Boolean mybool) {
		this.mybool = mybool;
	}

	public Date getMydate() {
		return mydate;
	}

	public void setMydate(Date mydate) {
		this.mydate = mydate;
	}
}