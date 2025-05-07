package com.disha.votezy.dto;

import lombok.Data;

@Data
public class VoterResponseDTO {
	
	private Long id;
//    private Integer age;
    private String name;
    private String email;
    private boolean hasVoted;

    // Getters and Setters

}
