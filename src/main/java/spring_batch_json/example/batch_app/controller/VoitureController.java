package spring_batch_json.example.batch_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import spring_batch_json.example.batch_app.model.Voiture;
import spring_batch_json.example.batch_app.service.VoitureService;

@RestController
@RequestMapping("/api/voitures")
public class VoitureController {

    private final VoitureService voitureService;

    @Autowired
    public VoitureController(VoitureService voitureService) {
        this.voitureService = voitureService;
    }

    @GetMapping
    public Page<Voiture> getVoitures(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size) {
        return voitureService.getAllVoitures(page, size);
    }

    @GetMapping("/{id}")
    public Voiture getVoitureById(@PathVariable Long id) {
        return voitureService.getVoitureById(id);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Voiture addVoiture(@RequestBody Voiture voiture) {
        return voitureService.addVoiture(voiture);
    }

    @PutMapping("/{id}")
    public Voiture updateVoiture(@PathVariable Long id, @RequestBody Voiture voiture) {
        return voitureService.updateVoiture(id, voiture);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVoiture(@PathVariable Long id) {
        voitureService.deleteVoiture(id);
    }
}
