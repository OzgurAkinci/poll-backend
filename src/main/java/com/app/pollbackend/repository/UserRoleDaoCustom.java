package com.app.pollbackend.repository;


import com.app.pollbackend.domain.SPermission;
import com.app.pollbackend.domain.SRole;

import java.util.List;

public interface UserRoleDaoCustom {
    public List<SRole> findByUser(Integer userId);

    public List<SRole> findByPermission(Integer permissionId);

    public List<SPermission> findPermissionByRole(Integer roleId);
}
