package br.com.zazix.commerce.services;

import br.com.zazix.commerce.DTOs.CategoryDTO;
import br.com.zazix.commerce.DTOs.ProductDTO;
import br.com.zazix.commerce.entities.Category;
import br.com.zazix.commerce.repositories.CategoryRepository;
import br.com.zazix.commerce.services.exceptions.DatabaseException;
import br.com.zazix.commerce.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository repository;

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {
        Category category = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Categoria não encontrada!"));
        return new CategoryDTO(category);
    }

    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll() {
        List<Category> list = repository.findAll();
        return list.stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());
        // return list.map(x -> new CategoryDTO(x));
    }

    @Transactional
    public CategoryDTO insert(CategoryDTO dto) {

        Category entity = new Category();
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);

        return new CategoryDTO(entity);
    }

    @Transactional
    public CategoryDTO update(Long id, CategoryDTO dto) {
        try {
            Category entity = repository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            entity = repository.save(entity);
            return new CategoryDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Categoria não encontrada!");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id não encontrado!");
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Violação de integridade!");
        }
    }

    private void copyDtoToEntity(CategoryDTO dto, Category entity) {
        entity.setName(dto.getName());
    }


}
