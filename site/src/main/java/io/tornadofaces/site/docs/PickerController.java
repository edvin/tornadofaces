package io.tornadofaces.site.docs;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.String.format;

@Model
@Getter
@Setter
public class PickerController {
	private Date date;
	private String color;

	@PostConstruct
	public void init() {
		date = new Date();
		color = "#3fa2d8";
	}

	public void colorChanged() {
		FacesContext.getCurrentInstance().addMessage(null,
			new FacesMessage(format("New color is %s", color), "Color changed"));
	}

	public void save() {
		FacesContext.getCurrentInstance().addMessage(null,
			new FacesMessage(new SimpleDateFormat("yyyy-MM-dd").format(date), "Date saved"));
	}
}
