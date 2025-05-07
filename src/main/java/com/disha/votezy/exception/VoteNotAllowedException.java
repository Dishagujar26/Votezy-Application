package com.disha.votezy.exception;

//Handles cases where a resource already exists (e.g., registering a voter with the same email).
public class VoteNotAllowedException extends RuntimeException {
       public VoteNotAllowedException(String message) {
    	   super(message);
       }
}
