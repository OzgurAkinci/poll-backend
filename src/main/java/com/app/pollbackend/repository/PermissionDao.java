package com.app.pollbackend.repository;

import com.app.pollbackend.domain.SPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface PermissionDao extends JpaRepository<SPermission, Integer> {
    List<SPermission> findAllByOrderByNameAsc();
    List<SPermission> findByparentPermissionIsNullOrderByNameAsc();
}
