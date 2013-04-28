package org.resourcestructure;

import org.resourcestructure.annotations.OperationType;
import org.resourcestructure.annotations.ResourceOperation;

public class Operation {
    
    private OperationType type;
    
    private String note;
    
    
    public static Operation create(OperationType type, String note) {
        return new Operation(type, note);
    }
    
    public static Operation create(OperationType type) {
        return new Operation(type);
    }
    
    public static Operation create(ResourceOperation resourceOperation) { 
        if (resourceOperation == null) {
            return null;
        }
        else {
            return new Operation(resourceOperation);
        }
    }


    private Operation(OperationType type, String note) {
        this.type = type;
        if (note != null && (!note.trim().equals(""))) {
            this.note = note;
        }
    }
    
    private Operation(OperationType type) {
        this.type = type;
    }

    
    private Operation(ResourceOperation resourceOperation) {
        this.type = resourceOperation.type();
        this.note = resourceOperation.note()==""?null:resourceOperation.note();
    }


    public OperationType getType() {
        return type;
    }

    public String getNote() {
        return note;
    }
    
    public String getHeader() {
        return getHeaderName() + ": " + getHeaderValue();
    }
    
    public String getHeaderName() {
        return "Resource-Operation";
        
    }
    
    public String getHeaderValue() {
          return  type.toString() + getProcessedNote();
    }
  
    public String getProcessedNote() {
        if (note == null || note.trim().equals("")) {
            return "";
        }
        else {
            return " (" + note + ")";
        }
    }


}
