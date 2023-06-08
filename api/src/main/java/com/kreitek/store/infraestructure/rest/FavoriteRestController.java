package com.kreitek.store.infraestructure.rest;

import com.kreitek.store.application.dto.FavoriteDTO;
import com.kreitek.store.application.service.FavoriteService;
import com.kreitek.store.infraestructure.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favorites")
public class FavoriteRestController {

    private final FavoriteService favoriteService;
    private final SessionManager sessionManager;

    @Autowired
    public FavoriteRestController(FavoriteService favoriteService, SessionManager sessionManager) {
        this.favoriteService = favoriteService;
        this.sessionManager = sessionManager;
    }

    @CrossOrigin
    @PostMapping(value = "/users/{userId}/favorites/{itemId}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<FavoriteDTO> addFavorite(@PathVariable Long userId, @PathVariable Long itemId, @RequestHeader("Authorization") String sessionToken) {
        if (sessionManager.isValid(sessionToken)) {
            boolean isFavorite = favoriteService.isFavorite(userId, itemId);
            if (isFavorite) {
                throw new IllegalArgumentException("Item is already marked as favorite");
            }
            FavoriteDTO favorite = favoriteService.addFavorite(userId, itemId);
            return ResponseEntity.ok(favorite);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @CrossOrigin
    @DeleteMapping(value = "/users/{userId}/favorites/{itemId}")
    public ResponseEntity<Void> removeFavorite(@PathVariable Long userId, @PathVariable Long itemId, @RequestHeader("Authorization") String sessionToken) {
        if (sessionManager.isValid(sessionToken)) {
            favoriteService.removeFavorite(userId, itemId);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @CrossOrigin
    @GetMapping(value = "/users/{userId}/favorites", produces = "application/json")
    public ResponseEntity<List<FavoriteDTO>> getFavoritesByUser(@PathVariable Long userId, @RequestHeader("Authorization") String sessionToken) {
        if (sessionManager.isValid(sessionToken)) {
            List<FavoriteDTO> favorites = favoriteService.getFavoritesByUser(userId);
            return ResponseEntity.ok(favorites);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @CrossOrigin
    @GetMapping(value = "/users/{userId}/items/{itemId}", produces = "application/json")
    public ResponseEntity<Boolean> isFavorite(@PathVariable Long userId, @PathVariable Long itemId) {
        boolean isFavorite = favoriteService.isFavorite(userId, itemId);
        return ResponseEntity.ok(isFavorite);
    }




}
