package io.tornadofaces.site.docs;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.text.SimpleDateFormat;
import java.util.Date;

@Model
@Getter
@Setter
public class DatepickerController {
	private Date date;
	
	@PostConstruct
	public void init() {
		date = new Date();
	}
	
	public void save() {
		FacesContext.getCurrentInstance().addMessage(null, 
			new FacesMessage(new SimpleDateFormat("yyyy-MM-dd").format(date), "Date saved"));
	}
}
