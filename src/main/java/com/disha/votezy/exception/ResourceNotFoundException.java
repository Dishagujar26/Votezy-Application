package com.disha.votezy.exception;


//Handles cases where an entity (Voter, Candidate, Vote, ElectionResult) is not found.
public class ResourceNotFoundException extends RuntimeException{
	public ResourceNotFoundException(String message){
		  super(message);
	}

}
