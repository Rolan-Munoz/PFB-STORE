package com.kreitek.store.application.mapper;

import com.kreitek.store.application.dto.CategoryDTO;
import com.kreitek.store.domain.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper  extends EntityMapper<CategoryDTO, Category> {

    //de esta manera decimos que si el id viene nulo nos devuelva nulo y en caso contrario que recoja ese id y lo genere automaticamente
    default Category fromId(Long id) {

        if( id == null) return null;
        Category category = new Category();
        category.setId(id);
        return category;
    }
}
