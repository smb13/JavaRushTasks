package com.javarush.task.task33.task3309;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "first")
public class First {
    @XmlElement(name = "second")
    public String item1 = "some string";
    @XmlElement(name = "second")
    public String item2 = "some string";
    @XmlElement(name = "second")
    public String item3 = "need CDATA because of <and>";
    @XmlElement(name = "second")
    public String item4 = "need CDATA because of \"";
}

