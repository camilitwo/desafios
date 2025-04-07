package com.desafio.solucion_desafio_1.util.validator;

import com.desafio.solucion_desafio_1.exception.UserAlreadyExistsException;
import com.desafio.solucion_desafio_1.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserValidator {
    private final UserRepository userRepository;

    public UserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void validate(String username, String email) {
        validateCondition(
                !userRepository.existsByUsername(username),
                "Username already exists: " + username
        );
        validateCondition(
                !userRepository.existsByEmail(email),
                "Email already exists: " + email
        );
    }

    private void validateCondition(boolean condition, String errorMessage) {
        Optional.of(condition)
                .filter(valid -> valid)
                .orElseThrow(() -> new UserAlreadyExistsException(errorMessage));
    }
}
