package br.com.zazix.commerce.repositories;

import br.com.zazix.commerce.entities.Category;
import br.com.zazix.commerce.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
