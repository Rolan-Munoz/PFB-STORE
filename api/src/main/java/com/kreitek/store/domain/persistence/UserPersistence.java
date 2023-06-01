package com.kreitek.store.domain.persistence;


import com.kreitek.store.domain.entity.User;

import java.util.List;
import java.util.Optional;


public interface UserPersistence {
    List<User> getAllUsers();
    Optional<User> getUserById(Long userId);
    Optional<User> getUserByEmail(String email);
    boolean existsUserByEmail(String email);
    Optional<User> getUserByNick(String nick);
    boolean existsUserByNick(String nick);
    User saveUser(User user);
    void deleteUser(Long userId);
}
