package spring_batch_json.example.batch_app.batch;

import org.springframework.batch.item.ItemReader;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import spring_batch_json.example.batch_app.model.Voiture;
import spring_batch_json.example.batch_app.service.MinIOService;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
public class VoitureJsonReader implements ItemReader<Voiture> {
    private final String filePath;
    private final MinIOService minIOService;
    private List<Voiture> voitures;
    private int currentIndex = 0;

    public VoitureJsonReader(@Value("${voiture.file-path}") String filePath, MinIOService minIOService) {
        this.filePath = filePath;
        this.minIOService = minIOService;
        this.voitures = loadVoitures();
    }

    //charger fichier json et convertir en objet java
    private List<Voiture> loadVoitures() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<Voiture> voitures = objectMapper.readValue(new File(filePath),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Voiture.class));

            // Transfert fichier json vers minio
            minIOService.uploadFileToMinIO(filePath, "success/" + new File(filePath).getName());
            return voitures;
        } catch (IOException e) {
            throw new RuntimeException("Erreur de lecture du fichier JSON", e);
        }
    }

    @Override
    public Voiture read() {
        if (currentIndex < voitures.size()) {
            return voitures.get(currentIndex++);
        } else {
            return null;
        }
    }
}

