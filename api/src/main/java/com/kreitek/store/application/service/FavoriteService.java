package com.kreitek.store.application.service;

import com.kreitek.store.application.dto.FavoriteDTO;

import java.util.List;

public interface FavoriteService {

    FavoriteDTO addFavorite(Long userId, Long itemId);

    void removeFavorite(Long userId, Long itemId);

    List<FavoriteDTO> getFavoritesByUser(Long userId);

    boolean isFavorite(Long userId, Long itemId);

}
