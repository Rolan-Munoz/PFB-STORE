package com.kreitek.store.application.service.impl;


import com.kreitek.store.application.dto.UserDTO;
import com.kreitek.store.application.mapper.UserMapper;
import com.kreitek.store.application.service.UserService;

import com.kreitek.store.domain.entity.User;
import com.kreitek.store.domain.persistence.UserPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserPersistence userPersistence;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserPersistence userPersistence, UserMapper userMapper) {
        this.userPersistence = userPersistence;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userPersistence.getAllUsers();
        return this.userMapper.toDto(users);
    }

    @Override
    public Optional<UserDTO> getUserById(Long userId) {
        return this.userPersistence.getUserById(userId).map(userMapper::toDto);
    }

    @Override
    public Optional<UserDTO> getUserByEmail(String email) {
        return this.userPersistence.getUserByEmail(email).map(userMapper::toDto);

    }

    @Override
    public boolean existsUserByEmail(String email) {
        return userPersistence.existsUserByEmail(email);
    }

    @Override
    public boolean existsUserByNick(String nick) {
        return this.userPersistence.existsUserByNick(nick);
    }


    @Override
    public Optional<UserDTO> getUserByNick(String nick) {
        return this.userPersistence.getUserByNick(nick).map(userMapper::toDto);

    }


    @Override
    public UserDTO saveUser(UserDTO userDTO) {
        User user = this.userMapper.toEntity(userDTO);
        User userSaved = this.userPersistence.saveUser(user);
        return this.userMapper.toDto(userSaved);
    }

    @Override
    public void deleteUser(Long userId) {
        this.userPersistence.deleteUser(userId);
    }



}


