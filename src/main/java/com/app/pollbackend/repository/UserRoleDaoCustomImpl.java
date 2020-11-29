package com.app.pollbackend.repository;



import com.app.pollbackend.domain.SPermission;
import com.app.pollbackend.domain.SRole;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class UserRoleDaoCustomImpl implements UserRoleDaoCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<SRole> findByUser(Integer userId) {
        Query q = entityManager.createNativeQuery("select * from s_role r where EXISTS (select 1 from s_user_role ur where ur.user_id= ?1 )", SRole.class);
        q.setParameter(1, userId);
        return q.getResultList();
    }

    @Override
    public List<SRole> findByPermission(Integer permissionId) {
        Query q = entityManager.createNativeQuery("select r.* from S_ROLE r,s_role_permission rp where rp.role_id=r.id and rp.permission_id= ?1 ", SRole.class);
        q.setParameter(1, permissionId);
        return q.getResultList();

    }

    @Override
    public List<SPermission> findPermissionByRole(Integer roleId) {
        Query q = entityManager.createNativeQuery("select * from s_permission p where EXISTS (select 1 from s_role_permission up where up.role_id= ?1 and up.permission_id=p.id)", SPermission.class);
        q.setParameter(1, roleId);
        return q.getResultList();
    }

}
