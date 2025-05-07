package com.disha.votezy.dto;

import lombok.Data;

@Data
public class ElectionResultResponseDTO {

	private String electionName;
	private long winnerID;
	private int totalVotes;
	private int winnerVotes;
}
