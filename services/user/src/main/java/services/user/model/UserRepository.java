package services.user.model;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> existsByUsername(String name);
    User findByUsername(String userName);
}
