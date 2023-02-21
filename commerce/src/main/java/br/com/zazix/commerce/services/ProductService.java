package br.com.zazix.commerce.services;

import br.com.zazix.commerce.DTOs.ProductDTO;
import br.com.zazix.commerce.entities.Product;
import br.com.zazix.commerce.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    ProductRepository repository;

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Product product = repository.findById(id).get();
        return new ProductDTO(product);
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(Pageable pageable) {
        Page<Product> list = repository.findAll(pageable);
        //return list.stream().map(x -> new ProductDTO(x)).collect(Collectors.toList());
        return list.map(x -> new ProductDTO(x));
    }



}
