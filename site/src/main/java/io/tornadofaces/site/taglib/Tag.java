package io.tornadofaces.site.taglib;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
public class Tag implements Serializable {
	private String description;
	@XmlElement(name = "tag-name")
	private String tagName;
	private Component component;
	@XmlElement(name = "attribute")
	private List<Attribute> attributes;

	public String toString() {
		return tagName;
	}
}
