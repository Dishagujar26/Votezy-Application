package com.disha.votezy.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.disha.votezy.entity.ElectionResult;

public interface ElectionResultRepository extends JpaRepository<ElectionResult,Long>{
	Optional<ElectionResult> findByEname(String ename);

}
