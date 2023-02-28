package br.com.zazix.commerce.controllers;

import br.com.zazix.commerce.DTOs.CategoryDTO;
import br.com.zazix.commerce.entities.Category;
import br.com.zazix.commerce.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "categories")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @GetMapping
    public ResponseEntity<List<Category>> findAll(){
        List<Category> list = new ArrayList<>();
        return null;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Category> findById(@PathVariable Long id){
        return null;
    }

    @PostMapping
    public ResponseEntity<Category> create(@RequestBody Category category){
        return null;
    }
}
