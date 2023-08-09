package com.nnk.springboot.service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getUsers() {
        /*List<User> userList = userRepository.findAll();

        if (userList.isEmpty()) {
            throw new RuntimeException("No users found");
        }*/

        return userRepository.findAll();
    }

    @Override
    public User getUserById(int id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user"));
    }

    @Override
    public User addUser(User user) {
        if (user == null) {
            throw new RuntimeException("User is null");
        }

        User toSave = userRepository.findByUsername(user.getUsername());

        if (toSave != null) {
            throw new RuntimeException("User already exists");
        }

        return userRepository.save(user);
    }

    @Override
    public User updateUser(int id, User user) {
        User toUpdate = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user"));

        toUpdate.setPassword(user.getPassword());
        toUpdate.setFullname(user.getFullname());
        toUpdate.setRole(user.getRole());
        toUpdate.setUsername(user.getUsername());

        return userRepository.save(toUpdate);
    }

    @Override
    public void deleteUserById(int id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }

        userRepository.deleteById(id);
    }
}
