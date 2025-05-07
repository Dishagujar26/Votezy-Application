package com.disha.votezy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.disha.votezy.entity.Candidate;

public interface CandidateRepository extends JpaRepository<Candidate,Long>{
	 List<Candidate> findAllByOrderByVoteCountDesc();
}
