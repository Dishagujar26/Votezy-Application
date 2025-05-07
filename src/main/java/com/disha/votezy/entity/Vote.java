package com.disha.votezy.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class Vote {
      
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    
	 @OneToOne   //“Each Vote is associated with exactly one Voter, and vice versa.”
	 @JoinColumn(name = "voter_id",unique = true) // this will create or map to a foreign key column in DB
	 @JsonIgnore  //protects your APIs from blowing up in endless loops
	 private Voter voter; // field name should be object reference, not the column
	 
	 @ManyToOne(fetch = FetchType.LAZY) //Links multiple votes to one candidate.
	 @JoinColumn(name="candidate_id")
	 @JsonIgnore
	 private Candidate candidate;
	 
	    //@JsonProperty This annotation tells Jac-kson to map these Java getter methods to the specified JSON property names 
		//(voterId and candidateId) when converting the object to JSON. -- jac-kson annotation 
		
		@JsonProperty("voterId")
		public Long getVoterId() {
			return voter!=null ? voter.getId():null;
		}
		@JsonProperty("candidateId")
		public Long getCandidateId() {
			return candidate!=null ? candidate.getId():null;
		}
		
	 
	
	
}
