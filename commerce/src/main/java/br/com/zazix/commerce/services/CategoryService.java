package br.com.zazix.commerce.services;

import br.com.zazix.commerce.DTOs.CategoryDTO;
import br.com.zazix.commerce.entities.Category;
import br.com.zazix.commerce.repositories.CategoryRepository;
import br.com.zazix.commerce.services.exceptions.DatabaseException;
import br.com.zazix.commerce.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
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
    public Page<CategoryDTO> findAll(Pageable pageable) {
        Page<Category> list = repository.findAll(pageable);
        //return list.stream().map(x -> new ProductDTO(x)).collect(Collectors.toList());
        return list.map(x -> new CategoryDTO(x));
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
        }catch(DataIntegrityViolationException e){
            throw new DatabaseException("Violação de integridade!");
        }
    }


    private void copyDtoToEntity(CategoryDTO dto, Category entity) {
        entity.setName(dto.getName());
    }


}
