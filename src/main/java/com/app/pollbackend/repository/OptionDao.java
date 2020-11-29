package com.app.pollbackend.repository;

import com.app.pollbackend.domain.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface OptionDao extends JpaRepository<Option, Integer>{
	@Query("SELECT count(id) FROM Option")
	Long getTotalCount() throws Exception;
}	

