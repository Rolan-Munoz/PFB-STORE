package com.kreitek.store.domain.persistence;

import com.kreitek.store.domain.entity.Favorite;

import java.util.List;
import java.util.Optional;

public interface FavoritePersistence {
    List<Favorite> getFavoritesByUser(Long userId);

    Optional<Favorite> getFavoriteByUserAndItem(Long userId, Long itemId);

    Favorite saveFavorite(Favorite favorite);

    void deleteFavorite(Long favoriteId);
}
