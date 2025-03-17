package spring_batch_json.example.batch_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import spring_batch_json.example.batch_app.model.Voiture;
import spring_batch_json.example.batch_app.repository.VoitureRepository;

@RestController
@RequestMapping("/api/voitures")

public class VoitureController {
    private final VoitureRepository voitureRepository;

    @Autowired
    public VoitureController(VoitureRepository voitureRepository) {
        this.voitureRepository = voitureRepository;
    }
    // 🔄 Récupérer toutes les voitures avec pagination
    @GetMapping
    public Page<Voiture> getVoitures(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size) {
        return voitureRepository.findAll(PageRequest.of(page, size));
    }

    @PostMapping
    public Voiture addVoiture(@RequestBody Voiture voiture) {
        return voitureRepository.save(voiture);
    }

    @PutMapping("/{id}")
    public Voiture updateVoiture(@PathVariable Long id, @RequestBody Voiture voiture) {
        return voitureRepository.findById(id)
                .map(v -> {
                    v.setMarque(voiture.getMarque());
                    v.setModele(voiture.getModele());
                    v.setAnnee(voiture.getAnnee());
                    return voitureRepository.save(v);
                }).orElseThrow(() -> new RuntimeException("Voiture non trouvée"));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteVoiture(@PathVariable Long id) {
        voitureRepository.deleteById(id);
    }


}
