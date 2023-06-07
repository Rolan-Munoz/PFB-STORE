package com.kreitek.store.infraestructure.rest;

import com.kreitek.store.application.dto.FavoriteDTO;
import com.kreitek.store.application.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favorites")
public class FavoriteRestController {

    private final FavoriteService favoriteService;

    @Autowired
    public FavoriteRestController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @CrossOrigin
    @PostMapping(value = "/users/{userId}/favorites/{itemId}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<FavoriteDTO> addFavorite(@PathVariable Long userId, @PathVariable Long itemId) {
        boolean isFavorite = favoriteService.isFavorite(userId, itemId);
        if (isFavorite) {
            throw new IllegalArgumentException("Item is already marked as favorite");
        }
        FavoriteDTO favorite = favoriteService.addFavorite(userId, itemId);
        return ResponseEntity.ok(favorite);
    }

    @CrossOrigin
    @DeleteMapping(value = "/users/{userId}/favorites/{itemId}")
    public ResponseEntity<Void> removeFavorite(@PathVariable Long userId, @PathVariable Long itemId) {
        favoriteService.removeFavorite(userId, itemId);
        return ResponseEntity.ok().build();
    }
    @CrossOrigin
    @GetMapping(value = "/users/{userId}/favorites", produces = "application/json")
    public List<FavoriteDTO> getFavoritesByUser(@PathVariable Long userId) {
        return favoriteService.getFavoritesByUser(userId);
    }


    @CrossOrigin
    @GetMapping(value = "/users/{userId}/items/{itemId}", produces = "application/json")
    public ResponseEntity<Boolean> isFavorite(@PathVariable Long userId, @PathVariable Long itemId) {
        boolean isFavorite = favoriteService.isFavorite(userId, itemId);
        return ResponseEntity.ok(isFavorite);
    }




}
