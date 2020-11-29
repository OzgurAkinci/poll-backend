package com.app.pollbackend.repository;


import com.app.pollbackend.domain.SUser;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface SUserDao extends JpaRepository<SUser, Integer>, SUserDaoCustom{
	@Query("SELECT count(id) FROM SUser")
	public Long getTotalCount() throws Exception;	
	
	@Query("SELECT e FROM SUser e WHERE e.username like %:username%")
	public List<SUser> findUserByFilters(@Param("username") String username, Pageable pageable) throws Exception;

	@Query("SELECT e FROM SUser e WHERE e.username = :username")
	public SUser findByUserName(@Param("username") String username);


	
	
}

