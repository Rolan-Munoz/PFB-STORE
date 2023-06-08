package com.kreitek.store.infraestructure.rest;


import com.kreitek.store.application.dto.UserDTO;
import com.kreitek.store.application.service.UserService;
import com.kreitek.store.infraestructure.session.Session;
import com.kreitek.store.infraestructure.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;


@RestController
public class UserRestController {

    private final UserService userService;
    private final SessionManager sessionManager;


    @Autowired
    public UserRestController(UserService userService, SessionManager sessionManager) {
        this.userService = userService;

        this.sessionManager = sessionManager;
    }

    @CrossOrigin
    @GetMapping(value = "/users", produces = "application/json")
    ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @CrossOrigin
    @GetMapping(value = "/users/{userId}", produces = "application/json")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long userId, @RequestHeader("Authorization") String sessionToken) {
        if (sessionManager.isValid(sessionToken)) {
            Optional<UserDTO> userOptional = userService.getUserById(userId);
            if (userOptional.isPresent()) {
                UserDTO user = userOptional.get();
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


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

<<<<<<< HEAD

        String sessionValue = UUID.randomUUID().toString();
        savedCliente.setSessionId(sessionValue);

=======
        String sessionToken = UUID.randomUUID().toString();
        Session session = new Session(sessionToken, savedCliente.getId());
        sessionManager.addSession(sessionToken, session);

        savedCliente.setSessionId(sessionToken);
>>>>>>> release/HITO2-2.0.0
        return ResponseEntity.ok(savedCliente);
    }


    @CrossOrigin
    @PostMapping(value = "/login", produces = "application/json", consumes = "application/json")
    public ResponseEntity<UserDTO> loginUser(@RequestBody UserDTO userDTO) {
        Optional<UserDTO> userOptional = userService.getUserByNick(userDTO.getNick());

        if (userOptional.isPresent()) {
            UserDTO user = userOptional.get();

            if (passwordEncoder().matches(userDTO.getPassword(), user.getPassword())) {
                String sessionValue = UUID.randomUUID().toString();
<<<<<<< HEAD
                user.setSessionId(sessionValue);

                // Guardar el usuario con el token en la base de datos o en algún lugar persistente
                // Por ejemplo, userService.saveUser(user);

                return ResponseEntity.ok(user);
            }
        }

=======
                Session session = new Session(sessionValue, user.getId());
                sessionManager.addSession(sessionValue, session);
                user.setSessionId(sessionValue);

                return ResponseEntity.ok(user);
            }
        }
>>>>>>> release/HITO2-2.0.0
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


    @CrossOrigin
    @PostMapping(value = "/logout", produces = "application/json")
<<<<<<< HEAD
    public ResponseEntity<Void> logoutUser() {


        return ResponseEntity.ok().build();
    }



=======
    public ResponseEntity<Void> logoutUser(@RequestHeader("Authorization") String sessionToken) {
        sessionManager.removeSession(sessionToken);
        return ResponseEntity.ok().build();
    }

>>>>>>> release/HITO2-2.0.0

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

    @CrossOrigin
    @GetMapping(value = "/users/exists/email/{email}", produces = "application/json")
    public ResponseEntity<Boolean> checkEmailExists(@PathVariable String email) {
        boolean exists = userService.existsUserByEmail(email);
        return ResponseEntity.ok(exists);
    }

    @CrossOrigin
    @GetMapping(value = "/users/exists/nick/{nick}", produces = "application/json")
    public ResponseEntity<Boolean> checkNickExists(@PathVariable String nick) {
        boolean exists = userService.existsUserByNick(nick);
        return ResponseEntity.ok(exists);
    }

<<<<<<< HEAD
=======
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
>>>>>>> release/HITO2-2.0.0



}
