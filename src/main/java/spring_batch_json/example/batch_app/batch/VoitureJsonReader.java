package spring_batch_json.example.batch_app.batch;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import spring_batch_json.example.batch_app.model.Voiture;
import spring_batch_json.example.batch_app.service.MinIOService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class VoitureJsonReader implements ItemReader<Voiture> {

    private final String directoryPath;
    private final MinIOService minIOService;
    private final List<Voiture> voitures = new ArrayList<>();
    private int currentIndex = 0;

    public VoitureJsonReader(@Value("${voiture.file-directory}") String directoryPath, MinIOService minIOService) {
        this.directoryPath = directoryPath;
        this.minIOService = minIOService;
        loadVoituresFromDirectory();
    }

    // Charger tous les fichiers JSON du dossier
    private void loadVoituresFromDirectory() {
        File directory = new File(directoryPath);
        if (!directory.exists() || !directory.isDirectory()) {
            throw new RuntimeException("Le dossier spécifié n'existe pas ou n'est pas un répertoire valide !");
        }

        File[] jsonFiles = directory.listFiles((dir, name) -> name.endsWith(".json"));
        if (jsonFiles == null || jsonFiles.length == 0) {
            System.out.println("Aucun fichier JSON trouvé dans le dossier.");
            return;
        }

        ObjectMapper objectMapper = new ObjectMapper();

        for (File file : jsonFiles) {
            try {
                List<Voiture> fileVoitures = objectMapper.readValue(file, new TypeReference<List<Voiture>>() {});
                voitures.addAll(fileVoitures);
                System.out.println("Fichier chargé : " + file.getName() + " (" + fileVoitures.size() + " voitures)");
            } catch (IOException e) {
                System.err.println("Erreur lors de la lecture du fichier " + file.getName() + " : " + e.getMessage());
            }
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
