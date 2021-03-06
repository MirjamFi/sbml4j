package org.tts.model.warehouse;

import org.neo4j.ogm.annotation.NodeEntity;
import org.tts.model.common.GraphEnum.FileNodeType;

@NodeEntity(label="FileNode")
public class FileNode extends WarehouseGraphNode {

	private FileNodeType fileNodeType;
	
	private String filename;
	
	private byte[] filecontent;

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public byte[] getFilecontent() {
		return filecontent;
	}

	public void setFilecontent(byte[] filecontent) {
		this.filecontent = filecontent;
	}

	public FileNodeType getFileNodeType() {
		return fileNodeType;
	}

	public void setFileNodeType(FileNodeType fileNodeType) {
		this.fileNodeType = fileNodeType;
	}
	
}
