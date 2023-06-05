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
        Favorite favorite = favoritePersistence.getFavoriteByUserAndItem(userId, itemId)
                .orElseThrow(() -> new IllegalArgumentException("Favorite not found"));
        favoritePersistence.deleteFavorite(favorite.getId());
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
        return favoritePersistence.getFavoriteByUserAndItem(userId, itemId).isPresent();
    }

}