package de.test;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAccessType;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class Root implements Serializable {

    private static final long serialVersionUID = 0L;

    @XmlElement
    List<Items> roots;

    public Root() {
    }

    public List<Items> getRoots() {
        return roots;
    }

    public void setRoots(List<Items> roots) {
        this.roots = roots;
    }

}
