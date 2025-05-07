package com.disha.votezy.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.disha.votezy.entity.Candidate;
import com.disha.votezy.entity.Vote;
import com.disha.votezy.entity.Voter;
import com.disha.votezy.exception.ResourceNotFoundException;
import com.disha.votezy.exception.VoteNotAllowedException;
import com.disha.votezy.repository.CandidateRepository;
import com.disha.votezy.repository.VoterRepository;
import com.disha.votezy.repository.VotingRepository;

import ch.qos.logback.core.joran.conditional.IfAction;
import jakarta.transaction.Transactional;

@Service
public class VoteService {
	private VoterRepository voterRepository;
	private CandidateRepository candidateRepository;
	private VotingRepository voteRepository;
	
	@Autowired
	public VoteService(VoterRepository voterRepository, CandidateRepository candidateRepository,
			VotingRepository voteRepository) {
		
		this.voterRepository = voterRepository;
		this.candidateRepository = candidateRepository;
		this.voteRepository = voteRepository;
	}
	
	//findBy fetches entities, return type - Optional<T>  or List<T>
    // while existsBy only checks existence, return type - boolean (true if the entity exists, false if not).
	
	@Transactional
	public Vote castVote(Long voterID, Long CandidadateID) {
		if(!voterRepository.existsById(voterID)) {
			throw new ResourceNotFoundException("Voter with "+ voterID +" not found !");
		}
		if(!candidateRepository.existsById(CandidadateID)) {
			throw new ResourceNotFoundException("Candidate with "+ CandidadateID +" not found !");
		}
		
		Voter voter = voterRepository.findById(voterID).get();
		if(voter.isHasVoted()) {
		        throw new VoteNotAllowedException("Voter with "+ voterID +" has already voted. Multiple votes are not allowed.");
		 }

		Candidate candidate = candidateRepository.findById(CandidadateID).get();  // see, here return our entity object wrapped inside the optional so, use get() method of optional to retrieve that object 
	    
		Vote vote = new Vote();
	    vote.setCandidate(candidate);
	    vote.setVoter(voter);
	    
	    candidate.setVoteCount(candidate.getVoteCount()+1);
	    candidateRepository.save(candidate);
	    voter.setVote(vote);
	    voter.setHasVoted(true);
	    voterRepository.save(voter);
	    return vote;
	    
	    //If you don’t call voter.setVote(vote), voter is blind to vote → vote won't be saved via voterRepository.save(voter)
	    //
	   
	}
	public List<Vote> getAllVote(){
		List<Vote> allVotes = voteRepository.findAll();
		return allVotes.stream()
                .filter(vote -> vote.getCandidate() != null)  // Filter out votes with null candidates
                .collect(Collectors.toList());
	}
   
}
