package com.farmSphere.auth.util;

import com.farmSphere.auth.data.model.GENDER;
import com.farmSphere.auth.data.model.ROLE;
import com.farmSphere.auth.data.model.User;
import com.farmSphere.auth.dto.request.UserRegisterRequest;

import java.time.format.DateTimeFormatter;

public class Mapper {

    public static User mapToRegisterUser(UserRegisterRequest request){
        User user = new User();
        user.setFirstName(request.getFirstName().trim().toLowerCase());
        user.setSecondName(request.getSecondName().trim().toLowerCase());
        user.setEmail(request.getEmail().trim().toLowerCase());
        user.setPhoneNumber(request.getPhoneNumber().trim().toLowerCase());
        user.setAddress(request.getAddress().trim().toLowerCase());
        user.setGender(GENDER.valueOf(request.getGender().name()));
        user.setRole(ROLE.BUYER);
        user.setDateCreated(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(java.time.LocalDateTime.now()));
        return user;
    }
}
