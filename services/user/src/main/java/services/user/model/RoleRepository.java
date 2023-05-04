package services.user.model;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface RoleRepository extends JpaRepository<Role, Long> { 
    Optional<Role> findById(Long primaryId);
    Optional<Role> findByLevel(int level);
    Optional<Role> findByType(String type);

    boolean existsById(Long id);
    boolean existsByLevel(int level);
    boolean existsByType(String type);

}
