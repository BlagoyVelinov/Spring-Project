package bg.softuni.mycinematicketsapp.repository;

import bg.softuni.mycinematicketsapp.models.entities.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {
}
