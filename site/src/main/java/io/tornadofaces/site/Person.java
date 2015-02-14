package io.tornadofaces.site;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Person implements Serializable {
	private Integer id;
	private String name;

	public Person() {
	}

	public Person(Integer id, String name) {
		this.id = id;
		this.name = name;
	}
}
