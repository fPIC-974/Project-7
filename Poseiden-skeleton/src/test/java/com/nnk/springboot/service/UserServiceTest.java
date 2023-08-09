package com.nnk.springboot.service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void getValidListOfUsers() {
        List<User> userList = new ArrayList<>();
        userList.add(new User());
        userList.add(new User());

        when(userRepository.findAll()).thenReturn(userList);

        List<User> toCheck = userService.getUsers();

        assertEquals(2, toCheck.size());
    }

    @Test
    public void getNullListOfUsers() {
        when(userRepository.findAll()).thenReturn(new ArrayList<>());

        List<User> toCheck = userService.getUsers();

        assertTrue(toCheck.isEmpty());
    }

    @Test
    public void getValidUser() {
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(new User()));

        User toCheck = userService.getUserById(1);

        assertNotNull(toCheck);
        assertDoesNotThrow(() -> {
        });
    }

    @Test
    public void cantGetUserNotFound() {
        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());

        RuntimeException runtimeException = assertThrows(RuntimeException.class,
                () -> userService.getUserById(1));

        assertTrue(runtimeException.getMessage().contains("Invalid user"));
    }

    @Test
    public void addValidUser() {
        User user = new User("name", "pass", "fullName", "role");
        user.setId(1);

        when(userRepository.save(any(User.class))).thenReturn(user);

        User toCheck = userService.addUser(user);

        assertDoesNotThrow(() -> {});
        assertEquals("name", toCheck.getUsername());
        assertEquals("pass", toCheck.getPassword());
        assertEquals("fullName", toCheck.getFullname());
        assertEquals("role", toCheck.getRole());
    }

    @Test
    public void cantAddUserNull() {

        RuntimeException runtimeException = assertThrows(RuntimeException.class,
                () -> userService.addUser(null));

        assertTrue(runtimeException.getMessage().contains("User is null"));
    }

    @Test
    public void updateValidUser() {
        User toUpdate = new User("name", "pass", "fullName", "role");
        toUpdate.setId(1);

        User user = new User("Updated", "UPDATED", "fullName", "role");

        when(userRepository.findById(1)).thenReturn(Optional.of(toUpdate));
        when(userRepository.save(any(User.class))).thenReturn(user);

        User toCheck = userService.updateUser(1, user);

        assertEquals("Updated", toCheck.getUsername());
        assertEquals("UPDATED", toCheck.getPassword());
        assertEquals("fullName", toCheck.getFullname());
    }

    @Test
    public void cantUpdateUserNull() {
        RuntimeException runtimeException = assertThrows(RuntimeException.class,
                () -> userService.updateUser(1, null));

        assertTrue(runtimeException.getMessage().contains("Invalid user"));
    }

    @Test
    public void cantUpdateUserNotFound() {
        User user = new User("name", "pass", "fullName", "role");

        when(userRepository.findById(1)).thenReturn(Optional.empty());

        RuntimeException runtimeException = assertThrows(RuntimeException.class,
                () -> userService.updateUser(1, user));

        assertTrue(runtimeException.getMessage().contains("Invalid user"));
    }

    @Test
    public void deleteValidUserById() {
        when(userRepository.existsById(anyInt())).thenReturn(true);

        assertDoesNotThrow(() -> userService.deleteUserById(1));
    }

    @Test
    public void cantDeleteUserByIdNotFound() {
        when(userRepository.existsById(anyInt())).thenReturn(false);

        RuntimeException runtimeException = assertThrows(RuntimeException.class,
                () -> userService.deleteUserById(1));

        assertEquals("User not found", runtimeException.getMessage());
    }
}