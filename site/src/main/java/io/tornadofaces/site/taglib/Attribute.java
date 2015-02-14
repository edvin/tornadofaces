package io.tornadofaces.site.taglib;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;

@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
public class Attribute implements Serializable {
	private String description;
	private String name;
	private Boolean required;

	public String toString() {
		return name;
	}
}
