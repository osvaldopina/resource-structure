package org.resourcestructure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.lang.reflect.Method;

import org.junit.Test;
import org.resourcestructure.annotations.OperationType;


public class CachedResourceDiscoverTest {
    
    
    @Test
    public void getResourceIdNextReturningNull() throws Exception {
        Method method = ResourceDiscoverMock.class.getMethod("m1");
        ResourceDiscoverMock resourceDiscoverMock = new ResourceDiscoverMock();
        
        CachedResourceDiscover cachedResourceDiscover = new CachedResourceDiscover(resourceDiscoverMock);
        
        assertNull(cachedResourceDiscover.getStructure(method));
        assertEquals(1,resourceDiscoverMock.timesGetResoureId);
        assertEquals(0,resourceDiscoverMock.timesGetOperation);

        assertEquals(method,resourceDiscoverMock.lastMethodParameter);
    }
    
    @Test
    public void getResourceIdNextReturningNonNull() throws Exception {
        Method method = ResourceDiscoverMock.class.getMethod("m1");
        ResourceDiscoverMock resourceDiscoverMock = new ResourceDiscoverMock();
        resourceDiscoverMock.structure = Structure.create("structures");
        
        // first call
        CachedResourceDiscover cachedResourceDiscover = new CachedResourceDiscover(resourceDiscoverMock);
        
        assertEquals(resourceDiscoverMock.structure, cachedResourceDiscover.getStructure(method));
        assertEquals(1,resourceDiscoverMock.timesGetResoureId);
        assertEquals(0,resourceDiscoverMock.timesGetOperation);
        assertEquals(method,resourceDiscoverMock.lastMethodParameter);
        
        // second call
        assertEquals(resourceDiscoverMock.structure, cachedResourceDiscover.getStructure(method));
        assertEquals(1,resourceDiscoverMock.timesGetResoureId);
        assertEquals(0,resourceDiscoverMock.timesGetOperation);
        assertEquals(method,resourceDiscoverMock.lastMethodParameter);
        
    }
 
    @Test
    public void getResourceOperationNextReturningNull() throws Exception {
        Method method = ResourceDiscoverMock.class.getMethod("m1");
        ResourceDiscoverMock resourceDiscoverMock = new ResourceDiscoverMock();
        
        CachedResourceDiscover cachedResourceDiscover = new CachedResourceDiscover(resourceDiscoverMock);
        
        assertNull(cachedResourceDiscover.getOperation(method));
        assertEquals(1,resourceDiscoverMock.timesGetOperation);
        
        assertEquals(method,resourceDiscoverMock.lastMethodParameter);
    }
    
    @Test
    public void getResourceOperationNextReturningNonNull() throws Exception {
        Method method = ResourceDiscoverMock.class.getMethod("m1");
        ResourceDiscoverMock resourceDiscoverMock = new ResourceDiscoverMock();
        resourceDiscoverMock.operation = Operation.create(OperationType.CREATE);
        
        // first call
        CachedResourceDiscover cachedResourceDiscover = new CachedResourceDiscover(resourceDiscoverMock);
        
        assertEquals(resourceDiscoverMock.operation, cachedResourceDiscover.getOperation(method));
        assertEquals(0,resourceDiscoverMock.timesGetResoureId);
        assertEquals(1,resourceDiscoverMock.timesGetOperation);
        assertEquals(method,resourceDiscoverMock.lastMethodParameter);
        
        // second call
        assertEquals(resourceDiscoverMock.operation, cachedResourceDiscover.getOperation(method));
        assertEquals(0,resourceDiscoverMock.timesGetResoureId);
        assertEquals(1,resourceDiscoverMock.timesGetOperation);
        assertEquals(method,resourceDiscoverMock.lastMethodParameter);
        
    }
    
    
    
   public static class ResourceDiscoverMock implements ResourceDiscover {
       
       public int timesGetResoureId = 0;

       public int timesGetOperation = 0;
       
       public Method lastMethodParameter;
       
       public Structure structure;
       
       public Operation operation;
       
    public void m1() {
        
    }
       
    @Override
    public Structure getStructure(Method method) {
        lastMethodParameter = method;
        timesGetResoureId ++;
        return structure;
    }

    @Override
    public Operation getOperation(Method method) {
        lastMethodParameter = method;
        timesGetOperation ++;
        return operation;
    }
       
   }
 
}
