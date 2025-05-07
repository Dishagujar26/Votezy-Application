package com.disha.votezy.mapper;

import com.disha.votezy.dto.VoterRequestDTO;
import com.disha.votezy.dto.VoterResponseDTO;
import com.disha.votezy.entity.Voter;

public class VoterMapper {

	public static Voter toEntity(VoterRequestDTO dto) {
        Voter voter = new Voter();
        voter.setName(dto.getName());
        voter.setEmail(dto.getEmail());
//        voter.setAge(dto.getAge());
        // hasVoted is not included in request, so we don’t set it here.
        return voter;
    }

	public static VoterResponseDTO toDto(Voter voter) {
        VoterResponseDTO dto = new VoterResponseDTO();
        dto.setId(voter.getId());
        dto.setName(voter.getName());
        dto.setEmail(voter.getEmail());
//        dto.setAge(voter.getAge());
        dto.setHasVoted(voter.isHasVoted());
        return dto;
    }
}
