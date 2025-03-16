package spring_batch_json.example.batch_app.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import spring_batch_json.example.batch_app.model.Voiture;

@Repository
public interface VoitureRepository extends JpaRepository<Voiture, Long> {
}
