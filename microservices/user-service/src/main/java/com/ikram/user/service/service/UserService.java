package com.ikram.user.service.service;

import com.ikram.user.service.exception.UserException;
import com.ikram.user.service.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface UserService {

    User createUser(User user);
    User getUserById(Long id) throws UserException;
    List<User> getUsers();
    String deleteUserById(Long id) throws UserException;
    User updateUser(User user,Long id) throws UserException;
}
