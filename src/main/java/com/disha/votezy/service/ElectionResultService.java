package com.disha.votezy.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.disha.votezy.entity.Candidate;
import com.disha.votezy.entity.ElectionResult;
import com.disha.votezy.repository.CandidateRepository;
import com.disha.votezy.repository.ElectionResultRepository;
import com.disha.votezy.repository.VotingRepository;

@Service
public class ElectionResultService {
     private CandidateRepository candidateRepository;
     private VotingRepository voteRepository;
     private ElectionResultRepository electionResultRepository;
     
     @Autowired
     public ElectionResultService(CandidateRepository candidateRepository, VotingRepository voteRepository, ElectionResultRepository electionResultRepository){
		this.candidateRepository=candidateRepository;
		this. voteRepository=voteRepository;
		this.electionResultRepository=electionResultRepository;
	}
     

    public ElectionResult declareResult(String electionName) {
          Optional<ElectionResult> existingResult = this.electionResultRepository.findByEname(electionName);
          if(existingResult.isPresent()) {
      		return existingResult.get();
       	 }
          //Checks if any votes exist at all.
          if(voteRepository.count() == 0) { // voteRepository.count(): Counts the total rows in the votes table.
        	  throw new IllegalStateException("Cann't declare results. Since, no votes has been casted yet !");
          }
          //Pulls all candidates from the DB, sorted from most votes to least.
          List<Candidate> candidates = candidateRepository.findAllByOrderByVoteCountDesc();
          Candidate winner = candidates.get(0);
          int total=0;
          for(Candidate candi:candidates) {
        	  total += candi.getVoteCount();
          }
          ElectionResult electionResult = new ElectionResult();
          electionResult.setWinner(winner);
          electionResult.setTotalVote(total);
          electionResult.setEname(electionName);
          return electionResultRepository.save(electionResult);
          
    }
    
    public List<ElectionResult> getAllResults(){
    	return electionResultRepository.findAll();
    }
}
