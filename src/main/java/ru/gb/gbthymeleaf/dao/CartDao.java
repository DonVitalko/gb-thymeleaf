package ru.gb.gbthymeleaf.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.gbthymeleaf.entity.Cart;

import java.util.Optional;

public interface CartDao extends JpaRepository<Cart, Long> { ;
    Optional<Cart> findById(Long id);

    Cart findCartsById(Long id);
}
