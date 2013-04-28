package org.resourcestructure;

import java.lang.reflect.Method;

import org.resourcestructure.annotations.ResourceOperation;
import org.resourcestructure.annotations.ResourceStructure;

public class ReflectionResourceDiscover implements ResourceDiscover{
    

    @Override
    public Structure getStructure(Method method) {
        ResourceStructure classResourceIdentity = method.getDeclaringClass().getAnnotation(ResourceStructure.class);
        ResourceStructure methodResourceIdentity = method.getAnnotation(ResourceStructure.class);
        if (methodResourceIdentity != null) {
            return Structure.create(methodResourceIdentity).merge(Structure.create(classResourceIdentity));
        }
        else {
            return null;
            
        }
    }

    @Override
    public Operation getOperation(Method method) {
        ResourceOperation resourceOperation = method.getAnnotation(ResourceOperation.class);
        return Operation.create(resourceOperation);
    }

}
