package com.disha.votezy.mapper;

import com.disha.votezy.dto.CandidateRequestDTO;
import com.disha.votezy.dto.CandidateResponseDTO;
import com.disha.votezy.entity.Candidate;

public class CandidateMapper {
     
	// Convert CandidateRequestDTO to Candidate entity
    public static Candidate toEntity(CandidateRequestDTO dto) {
        Candidate candidate = new Candidate();
        candidate.setCname(dto.getCname());
        candidate.setPname(dto.getPname());
        return candidate;
    }

    // Convert Candidate entity to CandidateResponseDTO
    public static CandidateResponseDTO toDto(Candidate candidate) {
        CandidateResponseDTO dto = new CandidateResponseDTO();
        dto.setId(candidate.getId());
        dto.setCname(candidate.getCname());
        dto.setPname(candidate.getPname());
        return dto;
    }
}
