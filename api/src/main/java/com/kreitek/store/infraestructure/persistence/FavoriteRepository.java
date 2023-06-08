package com.kreitek.store.infraestructure.persistence;

import com.kreitek.store.domain.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    List<Favorite> findAllByUserId(Long userId);

    Optional<Favorite> findByUserIdAndItemId(Long userId, Long itemId);

}
