package spring_batch_json.example.batch_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import spring_batch_json.example.batch_app.model.Erreur;
import spring_batch_json.example.batch_app.repository.ErreurRepository;

import java.util.List;

@RestController
@RequestMapping("/api/erreurs")
public class ErreurController {
    private final ErreurRepository erreurRepository;

    public ErreurController(ErreurRepository erreurRepository) {
        this.erreurRepository = erreurRepository;
    }

    @GetMapping
    public List<Erreur> getErreurs() {
        return erreurRepository.findAll();
    }
}

