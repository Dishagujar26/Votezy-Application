package com.disha.votezy.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.disha.votezy.dto.VoteRequestDTO;
import com.disha.votezy.dto.VoteResponseDTO;
import com.disha.votezy.entity.Vote;
import com.disha.votezy.mapper.VoteMapper;
import com.disha.votezy.service.VoteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/votes")
@CrossOrigin
public class VoteController {

	private final VoteService voteService;
    @Autowired
    public VoteController(VoteService voteService) {
    	this.voteService=voteService;
    }
    
    @PostMapping("/cast")
    public ResponseEntity<VoteResponseDTO> castVote(@RequestBody @Valid VoteRequestDTO voteRequest){
    	Vote vote = voteService.castVote(voteRequest.getVoterId(), voteRequest.getCandidateId());
    	VoteResponseDTO voteResponse = new VoteResponseDTO("Vote successfully counted ", true, vote.getVoterId(), vote.getCandidateId());
    	return new ResponseEntity<>(voteResponse, HttpStatus.CREATED);
    
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<VoteResponseDTO>> getAllVote() {
        List<Vote> list = voteService.getAllVote();
        List<VoteResponseDTO> dtoList = list.stream()
                                            .map(VoteMapper::toDtoForFetch)
                                            .collect(Collectors.toList());
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

}

