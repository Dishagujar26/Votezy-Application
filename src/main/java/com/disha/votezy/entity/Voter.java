package com.disha.votezy.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class Voter {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
//	@NotNull(message = "Age is required!")
//	private Integer age; 
    
	@NotBlank(message = "Name is required!")
	private String name;
	
	@NotBlank(message = "Email is required!")
	@Email(message = "Invalid email format")
	private String email;
	
	@OneToOne(mappedBy = "voter",cascade = CascadeType.ALL) // saving the voter will cascade the save operation to the associated vote. 
	@JsonIgnore	
	private Vote vote;
	
	private boolean hasVoted = false;
	

}
