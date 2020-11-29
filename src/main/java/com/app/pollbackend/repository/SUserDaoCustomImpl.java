package com.app.pollbackend.repository;


import com.app.pollbackend.domain.SPermission;
import com.app.pollbackend.domain.SUser;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@SuppressWarnings("unchecked")
public class SUserDaoCustomImpl implements SUserDaoCustom {
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<SPermission> findPermissionsByUser(Integer userId) {
        StringBuffer queryString = new StringBuffer();
        queryString.append("select * from s_permission p where EXISTS (select 1 from s_role_permission rp where rp.role_id in (select ur.role_id from s_user_role ur where ur.user_id= ?1 ) and rp.permission_id=p.id)");
        queryString.append(" union ");
        queryString.append("select * from s_permission p where EXISTS (select 1 from s_user_permission up where up.user_id = ?1 and up.permission_id=p.id)");
        queryString.append(" union ");
        queryString.append(" select * from s_permission p where EXISTS (select 1 from s_user_role ur where ur.user_id = ?1 and exists (select 1 from s_role r where r.id = ur.role_id and lower(r.role_name)='admin')) ");
        Query q = entityManager.createNativeQuery(queryString.toString(), SPermission.class);
        q.setParameter(1, userId);
        return q.getResultList();
    }

    @Override
    public SUser findUserByUserName(String userName) {
        Query q = entityManager.createNativeQuery("select * from s_user where username='"+userName+"'", SUser.class);
        return (SUser) q.getSingleResult();
    }


    @Override
    public List<SUser> findUsersOrder(String userName, String name, String surname) {
        StringBuffer qString = new StringBuffer();
        qString.append("select * from s_user p where (p.username = :username or :username is null) "
                + "and (nls_upper(p.name, 'NLS_SORT=xTURKISH') like nls_upper('%' || :name || '%', 'NLS_SORT=xTURKISH')) "
                + "and (nls_upper(p.surname, 'NLS_SORT=xTURKISH') like nls_upper('%' || :surname || '%', 'NLS_SORT=xTURKISH')) order by p.name,p.surname");
        Query q = entityManager.createNativeQuery(qString.toString(), SUser.class)
                .setParameter("username", userName)
                .setParameter("name", name)
                .setParameter("surname", surname);
        return q.getResultList();
    }

    @Override
    public List<SUser> findByPermission(Integer permissionId) {
        Query q = entityManager.createNativeQuery("select r.* from S_USER r,s_user_permission rp where rp.user_id=r.id and rp.permission_id= ?1 ", SUser.class);
        q.setParameter(1, permissionId);
        return q.getResultList();
    }

    @Override
    public List<SUser> findUsersByRoleId(Integer roleId) {
        Query q = entityManager.createNativeQuery("select u.* from s_user u where EXISTS (select 1 from s_user_role ur where ur.role_id= ?1 and ur.user_id=u.id) ", SUser.class);
        q.setParameter(1, roleId);
        return q.getResultList();
    }

    @Override
    public List<SUser> findByUserNameOrEmail(String userName, String email) {
        return entityManager.createQuery("select t from SUser t where t.username=:prmUserName or t.email=:prmEmail").setParameter("prmUserName", userName).setParameter("prmEmail", email).getResultList();
    }


}
