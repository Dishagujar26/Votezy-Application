package com.disha.votezy.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
public class ElectionResult {
	//One election at a time - our project design 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Election name is requied")
	private String ename;
	
	private int totalVote;
	
	@OneToOne
	@JoinColumn(name = "winner_id")
	@JsonIgnore
	private Candidate winner;
	//one-to-one relationship cause winner will be only one, no candidate list
	//our application will have exactly one winner 
	
	@JsonProperty("winnerId")
	 public Long getWinnerId() {
		 return winner!=null?winner.getId():null;
	 }

}
