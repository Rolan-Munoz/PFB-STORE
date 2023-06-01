package com.kreitek.store.application.service;


import com.kreitek.store.application.dto.UserDTO;


import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserDTO> getAllUsers();

    Optional<UserDTO> getUserById(Long userId);

    Optional<UserDTO> getUserByEmail(String email);

    boolean existsUserByEmail(String email);

    Optional<UserDTO> getUserByNick(String nick);

    boolean existsUserByNick(String nick);

    UserDTO saveUser(UserDTO userId);

    void deleteUser(Long userId);





}
