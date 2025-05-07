package com.disha.votezy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.disha.votezy.dto.VoterRequestDTO;
import com.disha.votezy.entity.Candidate;
import com.disha.votezy.entity.Vote;
import com.disha.votezy.entity.Voter;
import com.disha.votezy.exception.DuplicateResourceException;
import com.disha.votezy.exception.ResourceNotFoundException;
import com.disha.votezy.repository.CandidateRepository;
import com.disha.votezy.repository.VoterRepository;

import jakarta.transaction.Transactional;

@Service
public class VoterService {
	 private VoterRepository voterRepository;
     private CandidateRepository candidateRepository;
	
     @Autowired
     public VoterService(VoterRepository voterRepository, CandidateRepository candidateRepository) {
		this.voterRepository = voterRepository;
		this.candidateRepository = candidateRepository;
	}
     
    public Voter registerVoter(Voter voter){
    	 if(voterRepository.existsByEmail(voter.getEmail())) {
    		 throw new DuplicateResourceException("Voter with email id: "+voter.getEmail()+" already exists");
    	 }
    	 return voterRepository.save(voter);
     }
     
    public List<Voter>getAllVoters(){
    	 return voterRepository.findAll();
     }
     
    public Voter getVoterById(Long id) {
    	 Voter voter=voterRepository.findById(id).orElse(null);
    	 if(voter==null) {
    		 throw new ResourceNotFoundException("Voter with id:"+id+" not found");
    	 }
    	 return voter;
     }
     
    public Voter updateVoter(Long id, VoterRequestDTO voterDto) {
        Voter voter = voterRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Voter with id:" + id + " not found"));

        if (voterDto.getName() != null) {
            voter.setName(voterDto.getName());
        }
        if (voterDto.getEmail() != null) {
            voter.setEmail(voterDto.getEmail());
        }

//        if (voterDto.getAge() != null) {
//	        if (voterDto.getAge() < 18) {
//	            throw new IllegalArgumentException("Age must be at least 18");
//	        }
//	        voter.setAge(voterDto.getAge());
//	    }
        
        return voterRepository.save(voter);
    }

	@Transactional
	//Ensure single atomic transaction — 
	//guaranteeing roll-back on failure and maintaining consistency between related entities like Voter, Vote, and Candidate.
	public void deleteVoter(Long id) {
		Voter voter=voterRepository.findById(id).orElse(null);
		if(voter==null) {
			throw new ResourceNotFoundException("Cannot delete voter with id :"+id+" as it doesn not exist");
		}
		Vote vote=voter.getVote();
		if(vote!=null) {
			Candidate candidate=vote.getCandidate();
			candidate.setVoteCount(candidate.getVoteCount()-1);
			candidateRepository.save(candidate);
		}		
		voterRepository.delete(voter);
	}
}
