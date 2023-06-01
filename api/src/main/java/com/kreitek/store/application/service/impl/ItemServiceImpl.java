package com.kreitek.store.application.service.impl;

import com.kreitek.store.application.dto.ItemDTO;
import com.kreitek.store.application.mapper.ItemMapper;
import com.kreitek.store.application.service.ItemService;
import com.kreitek.store.domain.entity.Item;
import com.kreitek.store.domain.persistence.ItemPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemPersistence itemPersistence;
    private final ItemMapper itemMapper;

    @Autowired
    public ItemServiceImpl(ItemPersistence itemPersistence, ItemMapper itemMapper) {
        this.itemPersistence = itemPersistence;
        this.itemMapper = itemMapper;
    }



    @Override
    public List<ItemDTO> getAllItems() {
        List<Item> items = this.itemPersistence.getAllItems();
        return this.itemMapper.toDto(items);
    }

    @Override
    public List<ItemDTO> getAllItemsCategories(Long categoryId) {
        List<Item> items = this.itemPersistence.getAllItemsByCategory(categoryId);
        return this.itemMapper.toDto(items);
    }

    @Override
    public Optional<ItemDTO> getItemById(Long itemId) {
        return this.itemPersistence.getItemById(itemId).map(itemMapper::toDto);
    }

    @Override
    public ItemDTO saveItem(ItemDTO itemDTO) {
        Item itemSaved = this.itemPersistence.saveItem(this.itemMapper.toEntity(itemDTO));
        return this.itemMapper.toDto(itemSaved);
    }

    @Override
    public void deleteItem(Long itemId) {
        this.itemPersistence.deleteItem(itemId);
    }

    @Override
    public Page<ItemDTO> getItemsByCriteriaStringPaged(Pageable pageable, String filter) {
        Page<Item> itemPage = this.itemPersistence.finAll(pageable, filter);
        return itemPage.map(itemMapper::toDto);
    }




}
