package com.app.pollbackend.repository;

import com.app.pollbackend.domain.Poll;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;


@RepositoryRestResource
public interface PollDao extends JpaRepository<Poll, Integer>{
	@Query("SELECT count(id) FROM Poll")
	public Long getTotalCount() throws Exception;	
	
	@Query("SELECT e FROM Poll e WHERE e.pollTitle like %:pollTitle%")
	public List<Poll> findPollByFilters(@Param("pollTitle") String pollTitle, Pageable pageable) throws Exception;
}	

