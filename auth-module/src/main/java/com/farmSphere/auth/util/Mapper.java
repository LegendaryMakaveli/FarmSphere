package com.farmSphere.auth.util;

import com.farmSphere.auth.data.model.GENDER;
import com.farmSphere.auth.data.model.ROLE;
import com.farmSphere.auth.data.model.User;
import com.farmSphere.auth.dto.request.UserRegisterRequest;


import java.time.LocalDateTime;

public class Mapper {

    public static User mapToRegisterUser(UserRegisterRequest request) {
        User user = new User();
        user.setFirstName(request.getFirstName().trim().toLowerCase());
        user.setSecondName(request.getSecondName().trim().toLowerCase());
        user.setAge(request.getAge());
        user.setEmail(request.getEmail().trim().toLowerCase());
        user.setPhoneNumber(request.getPhoneNumber().trim());
        user.setAddress(request.getAddress().trim().toLowerCase());
        user.setGender(GENDER.valueOf(request.getGender().name()));
        user.setRole(ROLE.USER);
        user.setActive(true);
        user.setDateCreated(LocalDateTime.now());
        return user;
    }
}
