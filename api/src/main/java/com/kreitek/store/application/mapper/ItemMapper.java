package com.kreitek.store.application.mapper;

import com.kreitek.store.application.dto.ItemDTO;
import com.kreitek.store.domain.entity.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = CategoryMapper.class)
public interface ItemMapper extends EntityMapper<ItemDTO, Item> {

    // aqui tenemos que ayudar porque no queremos que nos devuelva el objeto category entero
    // si no solo el id y el nombre, por lo que a estas dos no las puede mapear automaticamente

    @Override
    @Mapping(source = "categoryId", target = "category")
    Item toEntity(ItemDTO dto);

    @Override
    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.name", target = "categoryName")
    ItemDTO toDto(Item entity);
}
