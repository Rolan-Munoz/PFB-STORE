package com.kreitek.store.infraestructure.persistence;

import com.kreitek.store.domain.entity.Favorite;
import com.kreitek.store.domain.persistence.FavoritePersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public class FavoritePersistenceImpl implements FavoritePersistence {
    private final FavoriteRepository favoriteRepository;

    @Autowired
    public FavoritePersistenceImpl(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    @Override
    public List<Favorite> getFavoritesByUser(Long userId) {
        return favoriteRepository.findAllByUserId(userId);
    }

    @Override
    public Optional<Favorite> getFavoriteByUserAndItem(Long userId, Long itemId) {
        return favoriteRepository.findByUserIdAndItemId(userId, itemId);
    }

    @Override
    public Favorite saveFavorite(Favorite favorite) {
        return favoriteRepository.save(favorite);
    }

    @Override
    public void deleteFavorite(Long favoriteId) {
        favoriteRepository.deleteById(favoriteId);
    }
}
