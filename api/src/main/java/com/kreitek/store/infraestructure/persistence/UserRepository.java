package com.kreitek.store.infraestructure.persistence;


import com.kreitek.store.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    User findByNick(String nick);
    boolean existsByNick(String nick);

}
