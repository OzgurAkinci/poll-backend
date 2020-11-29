package com.app.pollbackend.repository;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class AppDao {
    @Autowired
    SUserDao userDao;

    @Autowired
    SRoleDao roleDao;

    @Autowired
    SUserRoleDao userRoleDao;

    @Autowired
    PermissionDao permissionDao;

    @Autowired
    OauthTokenDao oauthTokenDao;

    @Autowired
    PollDao pollDao;

    @Autowired
    QuestionDao questionDao;

    @Autowired
    OptionDao optionDao;

    @Autowired
    ResponseDao responseDao;
}
