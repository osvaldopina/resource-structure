package org.resourcestructure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.resourcestructure.Operation;
import org.resourcestructure.annotations.OperationType;
import org.resourcestructure.annotations.ResourceOperation;

public class OperationTest {

    @Test
    public void createFromAnnotation() throws Exception {

        ResourceOperation resourceOperation =
                ResourceOperationWithAnnotation.class.getMethod("m1").getAnnotation(ResourceOperation.class);

        Operation operation = Operation.create(resourceOperation);

        assertEquals(OperationType.DELETE, operation.getType());
        assertEquals("a note" , operation.getNote());
    }
    
    @Test
    public void createFromNullAnnotation() throws Exception {

   
        assertNull((ResourceOperation)null);

    }


    @Test
    public void getHeaderName() {
        Operation operation = Operation.create(OperationType.CREATE);

        assertEquals("Resource-Operation", operation.getHeaderName());
    }

    @Test
    public void getHeaderValueAndHeaderNoNote() {
        Operation operation = Operation.create(OperationType.CREATE);

        assertEquals("create", operation.getHeaderValue());
        assertEquals("Resource-Operation: create", operation.getHeader());
    }

    @Test
    public void getHeaderValueAndHeaderWithNoNote() {
        Operation operation = Operation.create(OperationType.CREATE, "byName");

        assertEquals("create (byName)", operation.getHeaderValue());
        assertEquals("Resource-Operation: create (byName)", operation.getHeader());
    }

    public static class ResourceOperationWithAnnotation {

        @ResourceOperation(type = OperationType.DELETE, note = "a note")
        public void m1() {
        }
    }

}
