package spring_batch_json.example.batch_app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring_batch_json.example.batch_app.model.Voiture;

@Repository
public interface VoitureRepository extends JpaRepository<Voiture, Long> {
    Page<Voiture> findAll(Pageable pageable);
}
