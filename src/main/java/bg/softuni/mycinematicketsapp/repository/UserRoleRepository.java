package bg.softuni.mycinematicketsapp.repository;

import bg.softuni.mycinematicketsapp.models.entities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
}
