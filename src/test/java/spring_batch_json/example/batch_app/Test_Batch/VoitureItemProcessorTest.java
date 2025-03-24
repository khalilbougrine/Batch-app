package spring_batch_json.example.batch_app.Test_Batch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import  spring_batch_json.example.batch_app.batch.VoitureItemProcessor;
import  spring_batch_json.example.batch_app.model.Voiture;
import org.junit.jupiter.api.Test;

class VoitureItemProcessorTest {

    private final VoitureItemProcessor processor = new VoitureItemProcessor();

    @Test
    void testProcess() throws Exception {
        Voiture voitureInput = new Voiture(null, "Toyota", "Corolla", 2020,56000);
        Voiture voitureOutput = processor.process(voitureInput);

        assertNotNull(voitureOutput);
        assertEquals("Toyota", voitureOutput.getMarque());
        assertEquals("Corolla", voitureOutput.getModele());
    }
}
