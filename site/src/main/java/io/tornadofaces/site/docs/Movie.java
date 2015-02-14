package io.tornadofaces.site.docs;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Movie implements Serializable {
	private Integer id;
	private String title;

	public Movie() {

	}

	public Movie(Integer id, String title) {
		this.id = id;
		this.title = title;
	}
}