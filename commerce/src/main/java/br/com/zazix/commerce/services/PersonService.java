package br.com.zazix.commerce.services;

import br.com.zazix.commerce.DTOs.RoleDTO;
import br.com.zazix.commerce.DTOs.PersonDTO;
import br.com.zazix.commerce.entities.Role;
import br.com.zazix.commerce.entities.Person;
import br.com.zazix.commerce.repositories.RoleRepository;
import br.com.zazix.commerce.repositories.PersonRepository;
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

@Service
public class PersonService {

    @Autowired
    private PersonRepository repository;
    @Autowired
    private RoleRepository roleRepository;

    @Transactional(readOnly = true)
    public PersonDTO findById(Long id) {
        Person user = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Produto não encontrado!"));
        return new PersonDTO(user);
    }

    @Transactional(readOnly = true)
    public Page<PersonDTO> findAll(Pageable pageable) {
        Page<Person> list = repository.findAll(pageable);
        //return list.stream().map(x -> new ProductDTO(x)).collect(Collectors.toList());
        return list.map(x -> new PersonDTO(x));
    }

    @Transactional
    public PersonDTO insert(PersonDTO dto) {

        Person entity = new Person();
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);

        return new PersonDTO(entity);
    }

    @Transactional
    public PersonDTO update(Long id, PersonDTO dto) {
        try {
            Person entity = repository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            entity = repository.save(entity);
            return new PersonDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Produto não encontrado!");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        try {
            repository.deleteById(id);
        }catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id não encontrado!");
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Violação de integridade!");
        }
    }


    private void copyDtoToEntity(PersonDTO dto, Person entity) {

        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setBirthDate(dto.getBirthDate());


    }
}
