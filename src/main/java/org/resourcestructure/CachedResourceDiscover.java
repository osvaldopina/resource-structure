package org.resourcestructure;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class CachedResourceDiscover implements ResourceDiscover{
    
    private Map<Method,Structure> identities = new HashMap<Method,Structure>();
    private Map<Method,Operation> operations = new HashMap<Method,Operation>();
    private ResourceDiscover next;
    
    public CachedResourceDiscover(ResourceDiscover next) {
        this.next = next;
    }
    
    @Override
    public Structure getStructure(Method method) {
        Structure identity = identities.get(method);
        
        if (identity == null)  {
            identity = next.getStructure(method);
            if (identity != null) {
                identities.put(method, identity);
            }
        }
        return identity;
    }

    @Override
    public Operation getOperation(Method method) {
        Operation operation = operations.get(method);
        
        if (operation == null)  {
            operation = next.getOperation(method);
            if (operation != null) {
                operations.put(method, operation);
            }
        }
        return operation;
    }

}
