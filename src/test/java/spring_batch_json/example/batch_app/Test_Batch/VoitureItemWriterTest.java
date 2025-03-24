package spring_batch_json.example.batch_app.Test_Batch;

import static org.mockito.Mockito.*;

import spring_batch_json.example.batch_app.batch.VoitureItemWriter;
import spring_batch_json.example.batch_app.model.Voiture;
import spring_batch_json.example.batch_app.repository.VoitureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.item.Chunk;

import java.util.Arrays;
import java.util.List;

class VoitureItemWriterTest {

    @InjectMocks
    private VoitureItemWriter writer;

    @Mock
    private VoitureRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testWrite() {
        Voiture voiture1 = new Voiture("1L", "Toyota", "Corolla", 2020,45000);
        Voiture voiture2 = new Voiture("2L", "Honda", "Civic", 2022,987410);
        List<Voiture> voitures = Arrays.asList(voiture1, voiture2);

        // Créer un Chunk à partir de la liste de voitures
        Chunk<Voiture> chunk = new Chunk<>(voitures);

        // Appeler la méthode write avec le Chunk
        writer.write(chunk);

        // Vérifier que la méthode saveAll a été appelée une fois avec le chunk
        verify(repository, times(1)).saveAll(voitures);
    }
}
