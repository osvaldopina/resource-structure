package org.resourcestructure.annotations;

public enum OperationType {
    
    READ("read"),
    DELETE("delete"),
    UPDATE("update"),
    SEARCH("search"),
    CREATE("create");

    
    private String name;
    
    private OperationType(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return name;
    }
    
}
