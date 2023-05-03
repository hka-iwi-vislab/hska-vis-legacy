package services.user.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String name);
    User findByUsername(String userName);
}
