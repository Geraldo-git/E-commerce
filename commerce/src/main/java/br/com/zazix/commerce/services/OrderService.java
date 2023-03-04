package br.com.zazix.commerce.services;

import br.com.zazix.commerce.DTOs.OrderDTO;
import br.com.zazix.commerce.entities.Order;
import br.com.zazix.commerce.repositories.OrderRepository;
import br.com.zazix.commerce.services.exceptions.DatabaseException;
import br.com.zazix.commerce.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
public class OrderService {

    @Autowired
    OrderRepository repository;

    @Transactional(readOnly = true)
    public OrderDTO findById(Long id) {
        Order order = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Produto não encontrado!"));
        return new OrderDTO(order);
    }

    @Transactional(readOnly = true)
    public Page<OrderDTO> findAll(Pageable pageable) {
        Page<Order> list = repository.findAll(pageable);
        //return list.stream().map(x -> new ProductDTO(x)).collect(Collectors.toList());
        return list.map(x -> new OrderDTO(x));
    }

    @Transactional
    public OrderDTO insert(OrderDTO dto) {

        Order entity = new Order();
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);

        return new OrderDTO(entity);
    }

    @Transactional
    public OrderDTO update(Long id, OrderDTO dto) {
        try {
            Order entity = repository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            entity = repository.save(entity);
            return new OrderDTO(entity);
        } catch ( EntityNotFoundException e) {
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


    private void copyDtoToEntity(OrderDTO dto, Order entity) {

        entity.setStatus(dto.getStatus());
        entity.setMoment(dto.getMoment());
    }
}
