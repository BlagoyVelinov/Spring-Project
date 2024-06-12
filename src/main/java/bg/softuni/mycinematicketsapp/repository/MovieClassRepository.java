package bg.softuni.mycinematicketsapp.repository;

import bg.softuni.mycinematicketsapp.models.entities.MovieClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieClassRepository extends JpaRepository<MovieClass, Long> {
}
