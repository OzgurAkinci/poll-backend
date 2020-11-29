package com.app.pollbackend.repository;

import com.app.pollbackend.domain.Option;
import com.app.pollbackend.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface OptionDao extends JpaRepository<Option, Integer>{
	@Query("SELECT count(id) FROM Option")
	Long getTotalCount() throws Exception;

	@Query("SELECT e FROM Option e WHERE e.question.id = :questionId")
	public List<Option> findByQuestionId(@Param("questionId") Integer questionId);
}	

