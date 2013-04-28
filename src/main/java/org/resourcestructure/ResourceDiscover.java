package org.resourcestructure;

import java.lang.reflect.Method;

public interface ResourceDiscover {
    
    public Structure getStructure(Method method);
    
    public Operation getOperation(Method method);

}
