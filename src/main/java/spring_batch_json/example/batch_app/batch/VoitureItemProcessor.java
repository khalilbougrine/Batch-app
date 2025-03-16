package spring_batch_json.example.batch_app.batch;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import spring_batch_json.example.batch_app.model.Voiture;

@Component
public class VoitureItemProcessor implements ItemProcessor<Voiture, Voiture> {

    @Override
    public Voiture process(Voiture voiture) throws Exception {
        // Affichage de la voiture en cours de traitement
        System.out.println("Processing voiture : " + voiture);

        // Validation basique : vérifier que l'année et le kilométrage sont des valeurs valides
        if (voiture.getAnnee() <= 0 || voiture.getKilometrage() < 0) {
            // Si invalidité, renvoyer null
            return null;
        }
        return voiture;
    }
}