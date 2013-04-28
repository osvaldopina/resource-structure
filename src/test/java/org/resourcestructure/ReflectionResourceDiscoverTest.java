package org.resourcestructure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.resourcestructure.annotations.AttributeName;
import org.resourcestructure.annotations.OperationType;
import org.resourcestructure.annotations.ResourceOperation;
import org.resourcestructure.annotations.ResourceStructure;
import org.resourcestructure.annotations.StructureAttribute;


public class ReflectionResourceDiscoverTest {
	
	
	@Test
	public void getResoureIdM1() throws Exception {
		ReflectionResourceDiscover reflectionResourceDiscover = new ReflectionResourceDiscover();
		
		Structure structure = reflectionResourceDiscover.getStructure(ResourceClass.class.getMethod("m1"));
		
		assertEquals("resource-method",structure.getName());
		assertEquals(3, structure.getAttributes().size());
		assertEquals("com.acme", structure.getAttribute(AttributeName.ORGANIZATION).getValue());
		assertEquals("human-resources", structure.getAttribute(AttributeName.SUBJECT).getValue());
		assertEquals("1.0.0", structure.getAttribute(AttributeName.VERSION).getValue());
		
	}
	
	@Test
	public void getResoureIdM2() throws Exception {
		ReflectionResourceDiscover reflectionResourceDiscover = new ReflectionResourceDiscover();
		
		assertNull(reflectionResourceDiscover.getStructure(ResourceClass.class.getMethod("m2")));
		
	}

	@Test
	public void getOperationM1() throws Exception {
		ReflectionResourceDiscover reflectionResourceDiscover = new ReflectionResourceDiscover();
		
		Operation operation = reflectionResourceDiscover.getOperation((ResourceClass.class.getMethod("m1")));
		
		assertEquals(OperationType.READ, operation.getType());
		assertEquals("by-name",operation.getNote());
		
		
	}
	
	@Test
	public void getOperationM2() throws Exception {
		ReflectionResourceDiscover reflectionResourceDiscover = new ReflectionResourceDiscover();
		
		assertNull(reflectionResourceDiscover.getOperation((ResourceClass.class.getMethod("m2"))));
		
		
	}

	
	@ResourceStructure(name = "resource-class", attributes = {
			@StructureAttribute(name = AttributeName.ORGANIZATION, value = "br.gov.bcb"),
			@StructureAttribute(name = AttributeName.VERSION, value = "1.0.0") })
	public static class ResourceClass {
		
		@ResourceStructure(name = "resource-method", attributes = {
				@StructureAttribute(name = AttributeName.ORGANIZATION, value = "com.acme"),
				@StructureAttribute(name = AttributeName.SUBJECT, value = "human-resources") })
		@ResourceOperation(type=OperationType.READ,note="by-name")
		public void m1() {
			
		}
		
		public void m2() {
			
		}

	}

}
