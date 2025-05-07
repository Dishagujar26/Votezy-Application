package com.disha.votezy.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CandidateRequestDTO {
	@Size(min = 1, message = "Candidate Name must contain atleast atleast 1 character !")
	private String cname;
	
	@Size(min = 1, message = "Part Name must contain atleast atleast 1 character !")
	private String pname;
}
