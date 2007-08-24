package org.shiftone.jrat.provider.tree.ui.hierarchy;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;

import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.provider.tree.StackNode;

public class HirarchyViewBuilder {

	private Map nodes = new HashMap();

	public void build(StackNode rootNode, Set allMethodKeys) {
		
		// loop over the "all methods" set to initially populate the hierarchy
		Iterator iterator = allMethodKeys.iterator();
		while (iterator.hasNext()) {
			getNode((MethodKey)iterator.next());
		}
		
		// load stack "performance" data into hierarchy 
		
		// update coverage counts
		
	}

	private HirarchyNode getNode(MethodKey methodKey) {

		HirarchyNode node = (HirarchyNode)nodes.get(methodKey);
		if (node == null) {
			
		}
		return node;
	}
	
	
}