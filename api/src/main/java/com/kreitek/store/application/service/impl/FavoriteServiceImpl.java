package com.kreitek.store.application.service.impl;

import com.kreitek.store.application.dto.FavoriteDTO;
import com.kreitek.store.application.mapper.FavoriteMapper;
import com.kreitek.store.application.service.FavoriteService;
import com.kreitek.store.domain.entity.Favorite;
import com.kreitek.store.domain.entity.Item;
import com.kreitek.store.domain.entity.User;
import com.kreitek.store.domain.persistence.FavoritePersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoritePersistence favoritePersistence;
    private final FavoriteMapper favoriteMapper;

    @Autowired
    public FavoriteServiceImpl(FavoritePersistence favoritePersistence, FavoriteMapper favoriteMapper) {
        this.favoritePersistence = favoritePersistence;
        this.favoriteMapper = favoriteMapper;
    }

    @Override
    public FavoriteDTO addFavorite(Long userId, Long itemId) {
        // Verificar si el favorito ya existe para el usuario y el item
        if (isFavorite(userId, itemId)) {
            throw new IllegalArgumentException("Item is already a favorite");
        }

        Favorite favorite = new Favorite();
        User user = new User();
        user.setId(userId);
        favorite.setUser(user);
        Item item = new Item();
        item.setId(itemId);
        favorite.setItem(item);
        Favorite savedFavorite = favoritePersistence.saveFavorite(favorite);
        return favoriteMapper.toDto(savedFavorite);
    }


    @Override
    public void removeFavorite(Long userId, Long itemId) {
        // Obtener el Optional de Favorite a partir del userId y itemId
        Optional<Favorite> favoriteOptional = favoritePersistence.getFavoriteIdByUserAndItem(userId, itemId);

        if (favoriteOptional.isPresent()) {
            // Obtener el favoriteId del Optional y eliminar el favorito
            Long favoriteId = favoriteOptional.get().getId();
            favoritePersistence.deleteFavorite(favoriteId);
        } else {
            throw new IllegalArgumentException("Favorite not found");
        }
    }




    @Override
    public List<FavoriteDTO> getFavoritesByUser(Long userId) {
        List<Favorite> favorites = favoritePersistence.getFavoritesByUser(userId);
        return favorites.stream()
                .map(favoriteMapper::toDto)
                .collect(Collectors.toList());
    }



    @Override
    public boolean isFavorite(Long userId, Long itemId) {
        Optional<Favorite> favoriteOptional = favoritePersistence.getFavoriteIdByUserAndItem(userId, itemId);
        return favoriteOptional.isPresent();
    }


}