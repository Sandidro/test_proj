package ru.production.mapper;


import javax.inject.Inject;
import javax.inject.Singleton;

import model.User;
import ru.production.pojo.UserPojo;

@Singleton
public class UserMapper {


    @Inject
    UserMapper() {}


    public User transform(UserPojo userPojo) {
        User user = null;
        if (userPojo != null) {
            user = new User();
            user.setTitle(userPojo.getLogin());
            user.setSubtitle(String.valueOf(userPojo.getId()));
            user.setImage(userPojo.getAvatarUrl());
        }
        return user;
    }


}
