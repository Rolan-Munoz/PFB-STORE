package com.kreitek.store.infraestructure.rest;


import com.kreitek.store.application.dto.UserDTO;
import com.kreitek.store.application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
public class UserRestController {

    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @CrossOrigin
    @GetMapping(value = "/users", produces = "application/json")
    ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @CrossOrigin
    @GetMapping("/users/{usersId}")
    ResponseEntity<UserDTO> getUserById(@PathVariable Long userId) {
        Optional<UserDTO> user = userService.getUserById(userId);
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        }
        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @CrossOrigin
    @PostMapping(value = "/users", produces = "application/json", consumes = "application/json")
    ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        if (userService.existsUserByEmail(userDTO.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        if (userService.existsUserByNick(userDTO.getNick())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        UserDTO savedCliente = this.userService.saveUser(userDTO);
        return new ResponseEntity<>(savedCliente, HttpStatus.OK);
    }

    @CrossOrigin
    @PatchMapping(value = "/users", produces = "application/json", consumes = "application/json")
    ResponseEntity<UserDTO> updateUser( @RequestBody UserDTO userDTO) {
        UserDTO updateUSer = this.userService.saveUser(userDTO);
        return new ResponseEntity<>(updateUSer, HttpStatus.OK);
    }

    @CrossOrigin
    @DeleteMapping("/users/{userId}")
    ResponseEntity<?> deleteUSerById(@PathVariable Long userId) {
        Optional<UserDTO> user = userService.getUserById(userId);
        this.userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
