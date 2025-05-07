package com.disha.votezy.exception;

//Handles cases where a resource already exists (e.g., registering a voter with the same email).
public class DuplicateResourceException extends RuntimeException {
        public DuplicateResourceException(String message) {
        	super(message);
        }
}
