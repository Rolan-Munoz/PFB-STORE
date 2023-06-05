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

    @PostMapping(value = "/{userId}/{itemId}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<FavoriteDTO> addFavorite(@PathVariable Long userId, @PathVariable Long itemId) {
        FavoriteDTO favorite = favoriteService.addFavorite(userId, itemId);
        return ResponseEntity.ok(favorite);
    }

    @DeleteMapping(value = "/{userId}/{itemId}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Void> removeFavorite(@PathVariable Long userId, @PathVariable Long itemId) {
        favoriteService.removeFavorite(userId, itemId);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{userId}", produces = "application/json")
    public ResponseEntity<List<FavoriteDTO>> getFavoritesByUser(@PathVariable Long userId) {
        List<FavoriteDTO> favorites = favoriteService.getFavoritesByUser(userId);
        return ResponseEntity.ok(favorites);
    }

    @GetMapping(value = "/{userId}/{itemId}", produces = "application/json")
    public ResponseEntity<Boolean> isFavorite(@PathVariable Long userId, @PathVariable Long itemId) {
        boolean isFavorite = favoriteService.isFavorite(userId, itemId);
        return ResponseEntity.ok(isFavorite);
    }

}
