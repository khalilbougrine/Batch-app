package spring_batch_json.example.batch_app.batch;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import spring_batch_json.example.batch_app.model.Voiture;
import spring_batch_json.example.batch_app.repository.VoitureRepository;

@Component
public class VoitureItemWriter implements ItemWriter<Voiture> {

    private final VoitureRepository voitureRepository;

    public VoitureItemWriter(VoitureRepository voitureRepository) {
        this.voitureRepository = voitureRepository;
    }

    @Override
    public void write(Chunk<? extends Voiture> chunk) {
        // Log the items being written
        System.out.println("Writing voitures: " + chunk.getItems());

        // Save all items to the repository
        voitureRepository.saveAll(chunk.getItems());
    }
}