package services.user.model;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface RoleRepository extends JpaRepository<Role, Long> { 
    Optional<Role> findById(Long primaryId);

    boolean existsById(Long id);
    boolean existsByLevel(int level);
    boolean existsByType(String type);

    List<Role> findByLevel(int level);
    List<Role> findByType(String type);
}
