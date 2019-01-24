package org.tts.model;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.ResourceSupport;
import org.tts.controller.KeggController;

public class GeneResponseEntity extends ResourceSupport{
	
	private Gene gene;

	public GeneResponseEntity(Gene gene) {
		super();
		this.gene = gene;
	}

	public GeneResponseEntity() {
		// TODO Auto-generated constructor stub
	}

	public Gene getGene() {
		return gene;
	}

	public void setGene(Gene gene) {
		this.gene = gene;
	}

	public void addSelfLink() {
		
		this.add(linkTo(methodOn(KeggController.class)
				.getGeneByKegg4jId(this.gene.getKegg4jId())
			)
			.withSelfRel());
	}
	public void clearLinks() {
		this.removeLinks();
	}
	
}
