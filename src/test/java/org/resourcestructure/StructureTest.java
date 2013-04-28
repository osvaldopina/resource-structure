package org.resourcestructure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.resourcestructure.annotations.AttributeName;
import org.resourcestructure.annotations.ResourceStructure;
import org.resourcestructure.annotations.StructureAttribute;

public class StructureTest {

    @Test
    public void createStructureOnlyName() throws Exception {
        Structure structure = Structure.create("structure");

        assertEquals("structure", structure.getName());
        assertTrue(structure.getAttributes().isEmpty());
    }

    @Test
    public void createStructureWithNameAndAttributeList() throws Exception {
        List<Attribute> attributes =
                Arrays.asList(new Attribute(AttributeName.ORGANIZATION, "br.gov.bcb"), new Attribute(
                        AttributeName.VERSION, "1.0.0"));

        Structure structure = Structure.create("structure", attributes);

        assertEquals("structure", structure.getName());

        assertEquals(2, structure.getAttributes().size());
        assertEquals(AttributeName.ORGANIZATION, structure.getAttributes().get(0).getName());
        assertEquals("br.gov.bcb", structure.getAttributes().get(0).getValue());

        assertEquals(AttributeName.VERSION, structure.getAttributes().get(1).getName());
        assertEquals("1.0.0", structure.getAttributes().get(1).getValue());
    }

    @Test
    public void createStructureWithAnnotation() throws Exception {

        ResourceStructure resourceStructureAnnotation =
                ResourceStructureWithAnnotation.class.getMethod("m1").getAnnotation(ResourceStructure.class);

        assertNotNull(resourceStructureAnnotation);

        Structure structure = Structure.create(resourceStructureAnnotation);

        assertEquals("resource1", structure.getName());

        assertEquals(1, structure.getAttributes().size());
        assertEquals(AttributeName.VERSION, structure.getAttributes().get(0).getName());
        assertEquals("1.0.0", structure.getAttributes().get(0).getValue());

    }

    @Test
    public void getHeaderName() throws Exception {
        List<Attribute> attributes =
                Arrays.asList(new Attribute(AttributeName.ORGANIZATION, "br.gov.bcb"), new Attribute(
                        AttributeName.VERSION, "1.0.0"));

        Structure structure = Structure.create("structure", attributes);

        assertEquals("Resource-Structure", structure.getHeaderName());
    }

    @Test
    public void getHeaderValue() throws Exception {
        List<Attribute> attributes =
                Arrays.asList(new Attribute(AttributeName.ORGANIZATION, "br.gov.bcb"), new Attribute(
                        AttributeName.VERSION, "1.0.0"));

        Structure structure = Structure.create("structure", attributes);

        assertEquals("urn:rs:Organization=br.gov.bcb:Version=1.0.0:structure", structure.getHeaderValue());
    }

    @Test
    public void getUrn() throws Exception {
        List<Attribute> attributes =
                Arrays.asList(new Attribute(AttributeName.ORGANIZATION, "br.gov.bcb"), new Attribute(
                        AttributeName.VERSION, "1.0.0"));

        Structure structure = Structure.create("structure", attributes);

        assertEquals("urn:rs:Organization=br.gov.bcb:Version=1.0.0:structure", structure.getUrn());
    }

    @Test
    public void mergeOtherNull() throws Exception {

        Structure structure = Structure.create("structure", Arrays.asList(new Attribute(AttributeName.ORGANIZATION, "br.gov.bcb")));

        Structure merged = structure.merge(null);

        assertEquals("structure", merged.getName());

        assertEquals(1, merged.getAttributes().size());
        assertEquals(AttributeName.ORGANIZATION, merged.getAttributes().get(0).getName());
        assertEquals("br.gov.bcb", merged.getAttributes().get(0).getValue());
    }

    @Test
    public void mergeOnlyName() throws Exception {

        Structure structure = Structure.create("structure", Arrays.asList(new Attribute(AttributeName.ORGANIZATION, "br.gov.bcb")));

        Structure merged = structure.merge(Structure.create("new-structure"));

        assertEquals("structure", merged.getName());

        assertEquals(1, merged.getAttributes().size());
        assertEquals(AttributeName.ORGANIZATION, merged.getAttributes().get(0).getName());
        assertEquals("br.gov.bcb", merged.getAttributes().get(0).getValue());
    }

    @Test
    public void mergeOnlyAttributeList() throws Exception {

        Structure structure =
                Structure.create("structure", Arrays.asList(new Attribute(AttributeName.ORGANIZATION, "br.gov.bcb"),
                        new Attribute(AttributeName.SUBJECT, "human-resources")));

        Structure otherStructure =
                Structure.create(Arrays.asList(new Attribute(AttributeName.VERSION, "1.0.0"), new Attribute(
                        AttributeName.ORGANIZATION, "com.acme")));

        Structure merged = structure.merge(otherStructure);

        assertEquals("structure", merged.getName());

        assertEquals(3, merged.getAttributes().size());

        assertEquals("br.gov.bcb", merged.getAttribute(AttributeName.ORGANIZATION).getValue());
        assertEquals("1.0.0", merged.getAttribute(AttributeName.VERSION).getValue());
        assertEquals("human-resources", merged.getAttribute(AttributeName.SUBJECT).getValue());
        
    }

    @Test
    public void getHeader() throws Exception {
        List<Attribute> attributes =
                Arrays.asList(new Attribute(AttributeName.ORGANIZATION, "br.gov.bcb"), new Attribute(
                        AttributeName.VERSION, "1.0.0"));

        Structure structure =  Structure.create("structure", attributes);

        assertEquals("Resource-Structure: urn:rs:Organization=br.gov.bcb:Version=1.0.0:structure", structure.getHeader());
    }

    public static class ResourceStructureWithAnnotation {

        @ResourceStructure(name = "resource1", attributes = {@StructureAttribute(name = AttributeName.VERSION, value = "1.0.0")})
        public void m1() {

        }
    }

}
