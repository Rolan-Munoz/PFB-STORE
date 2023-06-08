package com.kreitek.store.application.mapper;

import com.kreitek.store.application.dto.FavoriteDTO;
import com.kreitek.store.domain.entity.Favorite;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UserMapper.class, ItemMapper.class})
public interface FavoriteMapper extends EntityMapper<FavoriteDTO, Favorite> {
}