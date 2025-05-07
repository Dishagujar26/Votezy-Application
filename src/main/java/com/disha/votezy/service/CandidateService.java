package com.disha.votezy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.disha.votezy.dto.CandidateRequestDTO;
import com.disha.votezy.entity.Candidate;
import com.disha.votezy.entity.Vote;
import com.disha.votezy.exception.ResourceNotFoundException;
import com.disha.votezy.repository.CandidateRepository;

@Service
public class CandidateService {
	private CandidateRepository candidateRepository;
    @Autowired
	public CandidateService(CandidateRepository candidateRepository) {

		this.candidateRepository = candidateRepository;
	}
    
    public Candidate addCandidate(Candidate candidate) {
    	return candidateRepository.save(candidate);
    }
    
    public List<Candidate>getAllCandidates(){
    	return candidateRepository.findAll();
    }
    
    public Candidate getCandidateById(Long id) {
    	Candidate candidate=candidateRepository.findById(id).orElse(null); //finder method returns optional and optional has method called orElse()
    	if(candidate==null) {
    		throw new ResourceNotFoundException("Candidate with id: "+id+" not found");
    	}
    	return candidate;
    }
    
    public Candidate updateCandidate(Long id,CandidateRequestDTO updatedCandidateDto) {
    	Candidate candidate=getCandidateById(id);
    	//only update fields when its not null
    	if(updatedCandidateDto.getCname()!=null) {
    		candidate.setCname(updatedCandidateDto.getCname());
    	}
    	if(updatedCandidateDto.getPname()!=null) {
    		candidate.setPname(updatedCandidateDto.getPname());
    	}
    	
    	
    	return candidateRepository.save(candidate);    	
    }
    
    public void deleteCandidate(Long id) {
        // Fetch the candidate object by ID
        Candidate candidate = getCandidateById(id);

        // Get the list of votes associated with the candidate
        List<Vote> votes = candidate.getVotes();

        // Break the bidirectional relationship by setting the candidate reference in each vote to null
        for (Vote v : votes) {
            v.setCandidate(null); // Back-reference is set to null to avoid foreign key constraint issues
        }

        // Clear the votes list in the candidate to fully disconnect the relationship
        candidate.getVotes().clear();

        // Now smoothly delete the candidate from the database
        candidateRepository.delete(candidate);
    }

}
