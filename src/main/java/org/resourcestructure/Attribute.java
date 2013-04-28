package org.resourcestructure;

import org.resourcestructure.annotations.StructureAttribute;

public class Attribute {
    
    private String name;
    
    private String value;
    
    public Attribute(String name, String value) {
        this.name = name;
        this.value = value;
    }
    
    public Attribute(StructureAttribute resourceAttribute) {
        this(resourceAttribute.name(),resourceAttribute.value());
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

}
