package br.com.zazix.commerce.services;

import br.com.zazix.commerce.DTOs.ProductDTO;
import br.com.zazix.commerce.entities.Product;
import br.com.zazix.commerce.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository repository;

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Product product = repository.findById(id).get();
        return new ProductDTO(product);

    }
}
