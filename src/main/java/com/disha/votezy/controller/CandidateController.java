package com.disha.votezy.controller;

import java.util.List;
import java.util.stream.Collectors;

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

import com.disha.votezy.dto.CandidateRequestDTO;
import com.disha.votezy.dto.CandidateResponseDTO;
import com.disha.votezy.entity.Candidate;
import com.disha.votezy.mapper.CandidateMapper;
import com.disha.votezy.service.CandidateService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/candidate")
@CrossOrigin
public class CandidateController {
      private CandidateService candidateService;
      
      @Autowired
      public CandidateController(CandidateService candidateService) {
    	  this.candidateService=candidateService;
      }
      
      @GetMapping("/{id}")
      public ResponseEntity<CandidateResponseDTO> getCandidate(@PathVariable Long id){
    	  Candidate candidate = candidateService.getCandidateById(id);
          CandidateResponseDTO candidateResponseDTO = CandidateMapper.toDto(candidate);
          return new ResponseEntity<>(candidateResponseDTO, HttpStatus.OK);
      }
      
      @GetMapping()
      public ResponseEntity<List<CandidateResponseDTO>> getAllCandidate(){
    	  List<Candidate> candidates = candidateService.getAllCandidates();
          List<CandidateResponseDTO> responseList = candidates.stream() //Converts the candidates list into a stream for processing.
              .map(CandidateMapper::toDto) //Transforms each Candidate object into a CandidateResponseDTO by calling the toDto method of CandidateMapper. 
              .collect(Collectors.toList()); //Collects the transformed elements back into a list (List<CandidateResponseDTO>).
          return new ResponseEntity<>(responseList, HttpStatus.OK);
      }
      
      @PostMapping("/register")
      public ResponseEntity<CandidateResponseDTO> registerCandidate(@RequestBody @Valid CandidateRequestDTO candidateRequest){
    	  Candidate candidate = CandidateMapper.toEntity(candidateRequest);
          Candidate savedCandidate = candidateService.addCandidate(candidate);
          CandidateResponseDTO candidateResponseDTO = CandidateMapper.toDto(savedCandidate);
          return new ResponseEntity<>(candidateResponseDTO, HttpStatus.CREATED);
      }
            
      @PutMapping("/update/{id}")
      public ResponseEntity<CandidateResponseDTO> updateCandidate(@RequestBody @Valid CandidateRequestDTO candidateRequest, @PathVariable Long id){
		Candidate updatedCandidate = candidateService.updateCandidate(id, candidateRequest);
          CandidateResponseDTO candidateResponseDTO = CandidateMapper.toDto(updatedCandidate);
          return new ResponseEntity<>(candidateResponseDTO, HttpStatus.OK);
      }
     
      @DeleteMapping("/delete/{id}")
      public ResponseEntity<String> deleteCandidate( @PathVariable Long id){
    	  candidateService.deleteCandidate(id);
    	  return new ResponseEntity<>("Candidate with id: "+id+" deleted successfully",HttpStatus.OK);
      }     
}
