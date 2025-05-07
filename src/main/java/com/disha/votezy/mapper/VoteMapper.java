package com.disha.votezy.mapper;

import com.disha.votezy.dto.VoteResponseDTO;
import com.disha.votezy.entity.Vote;

public class VoteMapper {

    // For casting a vote (POST request)
    public static VoteResponseDTO toDto(Vote vote) {
        return new VoteResponseDTO(
            "Vote successfully counted",   // Default success message
            true,                          // Success is true when the vote is successfully cast
            vote.getVoter().getId(),       // Voter ID from the Vote object
            vote.getCandidate().getId()    // Candidate ID from the Vote object
        );
    }

    // For fetching vote records (GET request)
    public static VoteResponseDTO toDtoForFetch(Vote vote) {
        return new VoteResponseDTO(
            "Vote Successfully feteched!",                       
            true,                          
            vote.getVoter().getId(),        
            vote.getCandidate().getId()    
        );
    }

}

