package com.kreitek.store.infraestructure.rest;


import com.kreitek.store.application.dto.UserDTO;
import com.kreitek.store.application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RestController
public class UserRestController {

    private final UserService userService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


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
    @GetMapping(value = "/users/{userId}", produces = "application/json")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long userId) {
        Optional<UserDTO> userOptional = userService.getUserById(userId);
        if (userOptional.isPresent()) {
            UserDTO user = userOptional.get();
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
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
        String encodedPassword = passwordEncoder().encode(userDTO.getPassword());
        userDTO.setPassword(encodedPassword);
        UserDTO savedCliente = this.userService.saveUser(userDTO);
        return new ResponseEntity<>(savedCliente, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping(value = "/login", produces = "application/json", consumes = "application/json")
    public ResponseEntity<UserDTO> loginUser(@RequestBody UserDTO userDTO, HttpServletResponse response) {
        // Obtener el usuario por su nick
        Optional<UserDTO> userOptional = userService.getUserByNick(userDTO.getNick());

        if (userOptional.isPresent()) {
            UserDTO user = userOptional.get();
            // Verificar la contraseña cifrada
            if (passwordEncoder().matches(userDTO.getPassword(), user.getPassword())) {
                // Generar y enviar la cookie al cliente para mantener la sesión abierta
                String sessionValue = UUID.randomUUID().toString();
                Cookie sessionCookie = new Cookie("sessionId", sessionValue);
                sessionCookie.setMaxAge(24 * 60 * 60); // Configurar la expiración de la cookie (en segundos)
                sessionCookie.setPath("/");
                response.addCookie(sessionCookie);

                // Devolver el usuario autenticado
                return ResponseEntity.ok(user);
            }
        }

        // La autenticación falló
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
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
