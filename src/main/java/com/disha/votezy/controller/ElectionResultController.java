package com.disha.votezy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.disha.votezy.dto.ElectionResultRequestDTO;
import com.disha.votezy.dto.ElectionResultResponseDTO;
import com.disha.votezy.entity.ElectionResult;
import com.disha.votezy.service.ElectionResultService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/api/election-result")
@CrossOrigin
public class ElectionResultController{
        
	   private final ElectionResultService electionResultService;
	   
	   @Autowired
	   public ElectionResultController(ElectionResultService electionResultService) {
		   this.electionResultService = electionResultService;
	   }
	   
	   @PostMapping("/declare")
	   public ResponseEntity<ElectionResultResponseDTO> declareResult(@RequestBody @Valid ElectionResultRequestDTO electionResultDTO){
		   ElectionResult result = electionResultService.declareResult(electionResultDTO.getElectionName());
		   ElectionResultResponseDTO responseDTO = new ElectionResultResponseDTO();
		   responseDTO.setElectionName(result.getEname());
		   responseDTO.setTotalVotes(result.getTotalVote());
		   responseDTO.setWinnerID(result.getWinnerId());
		   responseDTO.setWinnerVotes(result.getWinner().getVoteCount());
		   return new ResponseEntity<>(responseDTO,HttpStatus.OK);
	   }
	   //one election at a time will be conducted 
	   @GetMapping
	   public ResponseEntity<List<ElectionResult>> getAllResult(){
		   List<ElectionResult> list = electionResultService.getAllResults();
		   return ResponseEntity.ok(list);
	   }
	   
}
