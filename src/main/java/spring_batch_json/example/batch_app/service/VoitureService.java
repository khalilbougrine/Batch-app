package spring_batch_json.example.batch_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring_batch_json.example.batch_app.model.Voiture;
import spring_batch_json.example.batch_app.repository.VoitureRepository;

import java.util.Optional;

@Service
public class VoitureService {

    private final VoitureRepository voitureRepository;

    @Autowired
    public VoitureService(VoitureRepository voitureRepository) {
        this.voitureRepository = voitureRepository;
    }

    // Ajouter une nouvelle voiture
    public Voiture addVoiture(Voiture voiture) {
        return voitureRepository.save(voiture);
    }

    // Mettre à jour une voiture
    @Transactional
    public Voiture updateVoiture(Long id, Voiture voiture) {
        Optional<Voiture> existingVoiture = voitureRepository.findById(id);
        if (existingVoiture.isPresent()) {
            Voiture v = existingVoiture.get();
            v.setMarque(voiture.getMarque());
            v.setModele(voiture.getModele());
            v.setAnnee(voiture.getAnnee());
            return voitureRepository.save(v);
        } else {
            throw new RuntimeException("Voiture non trouvée");
        }
    }

    // Supprimer une voiture
    public void deleteVoiture(Long id) {
        voitureRepository.deleteById(id);
    }

    // Récupérer toutes les voitures
    public Page<Voiture> getAllVoitures(int page, int size) {
        return voitureRepository.findAll(PageRequest.of(page, size));
    }

    // Récupérer une voiture par son ID
    public Voiture getVoitureById(Long id) {
        return voitureRepository.findById(id).orElseThrow(() -> new RuntimeException("Voiture non trouvée"));
    }
}
