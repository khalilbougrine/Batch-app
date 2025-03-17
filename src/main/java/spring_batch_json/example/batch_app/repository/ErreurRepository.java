package spring_batch_json.example.batch_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring_batch_json.example.batch_app.model.Erreur;

@Repository
public interface ErreurRepository extends JpaRepository<Erreur, Long> {
}
