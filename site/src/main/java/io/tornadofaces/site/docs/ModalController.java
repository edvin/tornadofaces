package io.tornadofaces.site.docs;

import lombok.Getter;
import lombok.Setter;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class ModalController implements Serializable {
	@Getter @Setter private String name;

	public void prepareData() {
		this.name = "John Smith";
	}
}
