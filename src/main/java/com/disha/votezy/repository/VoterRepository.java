package com.disha.votezy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.disha.votezy.entity.Voter;

public interface VoterRepository extends JpaRepository<Voter, Long>{
	boolean existsByEmail(String email);
}
