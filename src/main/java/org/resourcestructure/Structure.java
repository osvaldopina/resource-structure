package org.resourcestructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.resourcestructure.annotations.ResourceStructure;
import org.resourcestructure.annotations.StructureAttribute;

public class Structure {

    private String name;

    private Map<String,Attribute> attributes = new HashMap<String,Attribute>();
    
    public static Structure create(String identity, List<Attribute> attributes) {
        return new Structure(identity, attributes);
    }

    public static Structure create(List<Attribute> attributes) {
        return new Structure(attributes);
    }
    
    public static Structure create(String name) {
        return new Structure(name);
    }

    public static Structure create(ResourceStructure resourceIdentity) {
        if (resourceIdentity == null) {
            return null;
        }
        else {
          return new Structure(resourceIdentity);
        }
    }

    private Structure(String name, List<Attribute> attributes) {
        this.name = name;
        for(Attribute attribute:attributes) {
          this.attributes.put(attribute.getName(),attribute);
        }
    }
    

    private Structure(List<Attribute> attributes) {
        for(Attribute attribute:attributes) {
            this.attributes.put(attribute.getName(),attribute);
          }
    }


    private Structure(String name) {
        this.name = name;
    }

    private Structure(ResourceStructure resourceIdentity) {
        this.name = resourceIdentity.name();
        getAttributesFromAnnotation(resourceIdentity);
    }

    private void getAttributesFromAnnotation(ResourceStructure resourceStructure) {
        for (StructureAttribute resourceAttribute : resourceStructure.attributes()) {
            attributes.put(resourceAttribute.name(), new Attribute(resourceAttribute));
        }
    }

    public String getName() {
        return name;
    }

    public List<Attribute> getAttributes() {
        return new ArrayList<Attribute>(attributes.values());
    }
    
    public Attribute getAttribute(String name) {
        return attributes.get(name);
    }


    public String getHeader() {
        return getHeaderName() + ": " + getHeaderValue();
    }

    public String getHeaderName() {
        return "Resource-Structure";

    }

    public String getHeaderValue() {
        return getUrn();
    }

    public String getUrn() {
        StringBuffer tmp = new StringBuffer("urn:rs");
        for (Attribute attribute : attributes.values()) {
            tmp.append(":");
            tmp.append(attribute.getName());
            tmp.append("=");
            tmp.append(attribute.getValue());
        }
        tmp.append(":");
        tmp.append(getName());
        return tmp.toString();
    }

    public Structure merge(Structure other) {
        return new Structure(mergeOnlyIdentity(other), mergeOnlyAttributes(other));
    }

    public String mergeOnlyIdentity(Structure other) {
        if (hasIdentity()) {
            return getName();
        } else if (other.hasIdentity()){
            return other.getName();
        } else {
        	return null;
        }
    }

    public List<Attribute> mergeOnlyAttributes(Structure other) {
        if (other == null) {
            return getAttributes();
        } else {
            Map<String, Attribute> otherAttributeMap = other.toAttributeMap();
            otherAttributeMap.putAll(toAttributeMap());
            return new ArrayList<Attribute>(otherAttributeMap.values());
        }
    }

    private Map<String, Attribute> toAttributeMap() {
        Map<String, Attribute> attributeMap = new HashMap<String, Attribute>();
        for (Attribute attribute : getAttributes()) {
            attributeMap.put(attribute.getName(), attribute);
        }
        return attributeMap;
    }

    private boolean hasIdentity() {
        return getName() != null && (!getName().trim().equals(""));
    }

}
