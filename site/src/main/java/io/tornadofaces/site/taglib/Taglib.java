package io.tornadofaces.site.taglib;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;


@XmlRootElement
@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
public class Taglib implements Serializable {
	private String namespace;
	@XmlElement(name = "tag")
	private List<Tag> tags;

	public String toString() {
		return namespace;
	}
}
