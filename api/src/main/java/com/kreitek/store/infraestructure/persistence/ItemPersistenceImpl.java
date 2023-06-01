package com.kreitek.store.infraestructure.persistence;

import com.kreitek.store.domain.entity.Item;
import com.kreitek.store.domain.persistence.ItemPersistence;
import com.kreitek.store.infraestructure.specs.ItemSpecification;
import com.kreitek.store.infraestructure.specs.shared.SearchCriteriaHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ItemPersistenceImpl implements ItemPersistence {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemPersistenceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public List<Item> getAllItems() {
        return this.itemRepository.findAll();
    }

    @Override
    public List<Item> getAllItemsByCategory(Long itemId) {
        return this.itemRepository.findAllByCategoryId(itemId);
    }

    @Override
    public Optional<Item> getItemById(Long itemId) {
        return this.itemRepository.findById(itemId);
    }

    @Override
    public Item saveItem(Item item) {
        return this.itemRepository.save(item);
    }

    @Override
    public void deleteItem(Long itemId) {
        this.itemRepository.deleteById(itemId);
    }

    @Override
    public Page<Item> finAll(Pageable pageable, String filters) {
        ItemSpecification specification = new ItemSpecification(SearchCriteriaHelper.fromFilterString(filters));
        return this.itemRepository.findAll(specification, pageable);
    }


}
