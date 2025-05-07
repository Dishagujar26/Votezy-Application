package com.disha.votezy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.disha.votezy.dto.VoterRequestDTO;
import com.disha.votezy.dto.VoterResponseDTO;
import com.disha.votezy.entity.Voter;
import com.disha.votezy.mapper.VoterMapper;
import com.disha.votezy.service.VoterService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/voters")
@CrossOrigin
public class VoterController {
	 private VoterService voterService;
	    
	    @Autowired
		public VoterController(VoterService voterService) {
			this.voterService = voterService;
		}
	    
	    @PostMapping("/register")
	     public ResponseEntity<VoterResponseDTO> registerVoter(@RequestBody @Valid VoterRequestDTO voterRequest){
	    	Voter savedVoter = voterService.registerVoter(VoterMapper.toEntity(voterRequest));
	        VoterResponseDTO response = VoterMapper.toDto(savedVoter);
	        return new ResponseEntity<>(response, HttpStatus.CREATED);
	     }
	    
	    @GetMapping("/{id}")
	     public ResponseEntity<VoterResponseDTO>getVoterById(@PathVariable Long id){
	    	    Voter voter = voterService.getVoterById(id);
	    	    return new ResponseEntity<>(VoterMapper.toDto(voter), HttpStatus.OK);
	     }
	    
	    @GetMapping()
	    public ResponseEntity<List<VoterResponseDTO>> getAllVoters() {
	        List<Voter> voters = voterService.getAllVoters();
	        List<VoterResponseDTO> responseList = voters.stream()
	            .map(VoterMapper::toDto)
	            .toList();
	        return new ResponseEntity<>(responseList, HttpStatus.OK);
	    }

	    @PutMapping("/update/{id}")
	    public ResponseEntity<VoterResponseDTO> updateVoter(@PathVariable Long id,@Valid
	                                                        @RequestBody VoterRequestDTO requestDto) {
	        Voter updatedVoter = voterService.updateVoter(id, requestDto);
	        VoterResponseDTO response = VoterMapper.toDto(updatedVoter);
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }


	    @DeleteMapping("/delete/{id}")
	    public ResponseEntity<String> deleteVoter(@PathVariable Long id){
	    	voterService.deleteVoter(id);
	    	return new ResponseEntity<>("Voter with id:"+id+" deleted",HttpStatus.OK);
	    }
}

