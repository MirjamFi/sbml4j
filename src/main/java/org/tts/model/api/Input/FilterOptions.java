package org.tts.model.api.Input;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.hateoas.ResourceSupport;
import org.tts.model.common.GraphEnum.NetworkMappingType;

public class FilterOptions  extends ResourceSupport {
	
	String mappingUuid;
	
	List<String> relationTypes;
	
	List<String> relationSymbols;
	
	List<String> nodeTypes;
	
	List<String> nodeSymbols;

	int minSize;
	int maxSize;

	boolean terminateAtDrug;
	
	// Node Annotation
	Map<String, Object> nodeAnnotation; 
	
	String nodeAnnotationName;
	
	String nodeAnnotationType;
	
	// RelationAnnotation
	
	Map<String, Object> relationAnnotation;
	
	String relationAnnotationName;
	
	String relationAnnotationType;
	
	
	public int getMinSize() {
		return minSize;
	}

	public void setMinSize(int minSize) {
		this.minSize = minSize;
	}

	public int getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}

	NetworkMappingType networkType;
	
	
	public String getMappingUuid() {
		return mappingUuid;
	}

	public void setMappingUuid(String mappingUuid) {
		this.mappingUuid = mappingUuid;
	}

	public List<String> getRelationTypes() {
		return relationTypes;
	}

	public void setRelationTypes(List<String> relationTypes) {
		this.relationTypes = relationTypes;
	}

	public List<String> getRelationSymbols() {
		return relationSymbols;
	}

	public void setRelationSymbols(List<String> relationSymbols) {
		this.relationSymbols = relationSymbols;
	}

	public List<String> getNodeTypes() {
		return nodeTypes;
	}

	public void setNodeTypes(List<String> nodeTypes) {
		this.nodeTypes = nodeTypes;
	}

	public List<String> getNodeSymbols() {
		return nodeSymbols;
	}

	public void setNodeSymbols(List<String> nodeSymbols) {
		this.nodeSymbols = nodeSymbols;
	}

	public NetworkMappingType getNetworkType() {
		return networkType;
	}

	public void setNetworkType(NetworkMappingType networkType) {
		this.networkType = networkType;
	}
	
	public Map<String, Object> getNodeAnnotation() {
		return nodeAnnotation;
	}

	public void setNodeAnnotation(Map<String, Object> nodeAnnotation) {
		this.nodeAnnotation = nodeAnnotation;
	}

	public String getNodeAnnotationName() {
		return nodeAnnotationName;
	}

	public void setNodeAnnotationName(String nodeAnnotationName) {
		this.nodeAnnotationName = nodeAnnotationName;
	}

	public String getNodeAnnotationType() {
		return nodeAnnotationType;
	}

	public void setNodeAnnotationType(String nodeAnnotationType) {
		this.nodeAnnotationType = nodeAnnotationType;
	}

	public Map<String, Object> getRelationAnnotation() {
		return relationAnnotation;
	}

	public void setRelationAnnotation(Map<String, Object> relationAnnotation) {
		this.relationAnnotation = relationAnnotation;
	}

	public String getRelationAnnotationName() {
		return relationAnnotationName;
	}

	public void setRelationAnnotationName(String relationAnnotationName) {
		this.relationAnnotationName = relationAnnotationName;
	}

	public String getRelationAnnotationType() {
		return relationAnnotationType;
	}

	public void setRelationAnnotationType(String relationAnnotationType) {
		this.relationAnnotationType = relationAnnotationType;
	}

	/**
	 * TODO
	 * Needs to also include
	 * NodeAnnotation
	 * RelationshipAnnotation
	 * isTerminateAtDrug
	 * relationSymbols
	 * @param other
	 * @return
	 */
	public String toString() {
		String ret = "Filter options ";
		if (this.mappingUuid != null) {
			ret += "(uuid: ";
			ret += this.mappingUuid;
			ret += ") ";
		}		
		ret += "are: (";
		if(this.relationTypes != null) {
			ret += "relationTypes: ";
			for (String type : this.relationTypes) {
				ret += type;
				ret += ", ";
			}
		}
		ret = ret.substring(0, ret.length() - 2);
		ret += "); ";
		
		if(this.nodeTypes != null) {
			ret += "(nodeTypes: ";
			for (String nodeType : this.nodeTypes) {
				ret += nodeType;
				ret += ", ";
			}
		}
		ret = ret.substring(0, ret.length() - 2);
		ret += "); ";
		if(this.nodeSymbols != null) {
			ret += "(nodeSymbols: ";
			for(String nodeSymbol : this.nodeSymbols) {
				ret += nodeSymbol;
				ret += ", ";
			}
		}
		ret = ret.substring(0, ret.length() - 2);
		ret += "); ";
		if (this.networkType != null) {
			ret += "(networkType: ";
			ret += this.networkType;
			ret += ")";
		}
		
		return ret;
	}
	
	
	/**
	 * TODO
	 * Needs to also include
	 * NodeAnnotation
	 * RelationshipAnnotation
	 * isTerminateAtDrug
	 * relationSymbols
	 * @param other
	 * @return
	 */
	public boolean equals(FilterOptions other) {
		
		if(this.mappingUuid != null && 
				other.getMappingUuid() != null && 
				!this.mappingUuid.equals(other.getMappingUuid())) {
			return false;
		}
		
		// compare NetworkType
		if(!this.networkType.equals(other.networkType)) {
			return false;
		}
		// compare NodeTypes
		List<String> thisNodeTypes = new ArrayList<>(this.getNodeTypes());
		for (String nodeType : other.getNodeTypes()) {
			if(thisNodeTypes.remove(nodeType) != true) {
				return false;
			}
		}
		if (thisNodeTypes.size() != 0) {
			return false;
		}
		// compare relationTypes
		List<String> thisTransitionTypes = new ArrayList<>(this.getRelationTypes());
		for (String transitionType : other.getRelationTypes()) {
			if (thisTransitionTypes.remove(transitionType) != true) {
				return false;
			}
		}
		
		List<String> thisNodeSymbols = new ArrayList<>(this.getNodeSymbols());
		for (String nodeSymbol : other.getNodeSymbols()) {
			if (thisNodeSymbols.remove(nodeSymbol) != true) {
				return false;
			}
		}
		
		if (thisTransitionTypes.size() != 0) {
			return false;
		}
		// all comparisions valid, filterOptions are equal		
		return true;
	}

	public boolean isTerminateAtDrug() {
		return terminateAtDrug;
	}

	public void setTerminateAtDrug(boolean terminateAtDrug) {
		this.terminateAtDrug = terminateAtDrug;
	}
	
}
