package io.tornadofaces.site.taglib;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
public class Component implements Serializable {
	@XmlElement(name = "component-type")
	private String componentType;
	@XmlElement(name = "renderer-type")
	private String rendererType;
}
