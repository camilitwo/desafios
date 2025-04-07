package com.desafio.solucion_desafio_1.repository;

import com.desafio.solucion_desafio_1.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, String> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
