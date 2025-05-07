package com.disha.votezy.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
public class Candidate {
 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Candidate's name is required!")
	private String cname;
	
	@NotBlank(message = "Party's name is required!")
	private String pname;
	private int voteCount=0;
	
	@OneToMany(mappedBy = "candidate",cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Vote> votes;
}
