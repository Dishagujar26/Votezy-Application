package com.disha.votezy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.disha.votezy.entity.Vote;

public interface VotingRepository  extends JpaRepository<Vote,Long>{
      
      
}
