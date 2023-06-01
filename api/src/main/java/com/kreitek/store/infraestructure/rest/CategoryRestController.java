package com.kreitek.store.infraestructure.rest;

import com.kreitek.store.application.dto.CategoryDTO;
import com.kreitek.store.application.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryRestController {

    private CategoryService categoryService;

    @Autowired
    public CategoryRestController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }



    @CrossOrigin
    @GetMapping(value = "/categories", produces = "application/json")
    ResponseEntity<List<CategoryDTO>> getAllCategories(@RequestParam(name = "partialName", required = false) String partialName) {
        List<CategoryDTO> categories;
        if(partialName == null) {
            categories = this.categoryService.getAllCategories();

        } else {
            categories = this.categoryService.getAllCategoriesMatchPartialName(partialName);

        }
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }



    @CrossOrigin
    @PostMapping(value = "/categories", produces = "application/json", consumes = "application/json" )
    ResponseEntity<CategoryDTO> insertCategory(@RequestBody CategoryDTO categoryDTO) {
        categoryDTO = this.categoryService.saveCategory(categoryDTO);
        return new ResponseEntity<>(categoryDTO, HttpStatus.CREATED);
    }

}
