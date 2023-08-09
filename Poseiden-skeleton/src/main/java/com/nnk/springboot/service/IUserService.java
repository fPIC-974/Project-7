package com.nnk.springboot.service;

import com.nnk.springboot.domain.User;

import java.util.List;

public interface IUserService {
    List<User> getUsers();

    User getUserById(int id);

    User addUser(User user);

    User updateUser(int id, User user);

    void deleteUserById(int id);
}
