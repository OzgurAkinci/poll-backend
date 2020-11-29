package com.app.pollbackend.repository;

import com.app.pollbackend.domain.Question;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;


@RepositoryRestResource
public interface QuestionDao extends JpaRepository<Question, Integer>{
	@Query("SELECT count(id) FROM Question")
	public Long getTotalCount() throws Exception;	
	
	@Query("SELECT e FROM Question e WHERE e.questionTitle like %:questionTitle%")
	public List<Question> findQuestionByFilters(@Param("questionTitle") String questionTitle, Pageable pageable) throws Exception;

	@Query("SELECT e FROM Question e WHERE e.poll.id = :pollId")
	public List<Question> findByPollId(@Param("pollId") Integer pollId);
}	

