package org.resourcestructure;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CachedResourceDiscover implements ResourceDiscover {

	private Map<Method, Structure> identities = new HashMap<Method, Structure>();
	private Map<Method, Map<String, Attribute>> attributesToMerge = new HashMap<Method, Map<String, Attribute>>();
	private Map<Method, Operation> operations = new HashMap<Method, Operation>();
	private ResourceDiscover next;

	public CachedResourceDiscover(ResourceDiscover next) {
		this.next = next;
	}

	public void addAttributeToMerge(Method method, Attribute attribute) {
		Map<String, Attribute> methodAttributes = attributesToMerge.get(method);

		if (methodAttributes == null) {
			methodAttributes = new HashMap<String, Attribute>();
			attributesToMerge.put(method, methodAttributes);
		}

		methodAttributes.put(attribute.getName(), attribute);
	}

	@Override
	public Structure getStructure(Method method) {
		Structure identity = identities.get(method);

		if (identity == null) {
			identity = next.getStructure(method);
			if (identity != null) {
				identity = identity.merge(getStructureToMerge((method)));
				identities.put(method, identity);
			}
		}
		return identity;
	}

	private Structure getStructureToMerge(Method method) {
		Map<String, Attribute> attributeMapToMergeForMethod = attributesToMerge.get(method);
		
		if (attributeMapToMergeForMethod == null) {
			return null;
		}
		else {
			List<Attribute> attributeListToMergeForMethod = new ArrayList<Attribute>(attributeMapToMergeForMethod.values());
			if (attributeListToMergeForMethod.size() == 0) {
				return null;
			}
			else {
				return Structure.create(attributeListToMergeForMethod);
			}
		}
	}

	@Override
	public Operation getOperation(Method method) {
		Operation operation = operations.get(method);

		if (operation == null) {
			operation = next.getOperation(method);
			if (operation != null) {
				operations.put(method, operation);
			}
		}
		return operation;
	}

}
 