package com.app.pollbackend.repository;

import com.app.pollbackend.domain.Response;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ResponseDao extends JpaRepository<Response, Integer>{
	@Query("SELECT count(id) FROM Response")
	Long getTotalCount() throws Exception;

	@Query(value = "select count(id) from response WHERE response @> CAST(:optionId as jsonb)", nativeQuery = true)
	Integer getCountByOptionId(@Param("optionId") String optionId) throws Exception;

	@Query(value = "select count(id) from response WHERE user_id=:userId and question_id=:questionId and response @> CAST(:optionId as jsonb)", nativeQuery = true)
	Integer getCountByOptionIdAndUserIdAndQuestionId(@Param("userId") Integer userId, @Param("questionId") Integer questionId, @Param("optionId") String optionId) throws Exception;
}	

