package spring_batch_json.example.batch_app.config;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;
import spring_batch_json.example.batch_app.service.MinIOService;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Component
public class JobCompletionListener implements JobExecutionListener {

    private final MinIOService minIOService;
    private final String directoryPath;
    private final String processedPath;

    public JobCompletionListener(MinIOService minIOService,
                                 @Value("${voiture.file-directory}") String directoryPath) {

        this.minIOService = minIOService;
        this.directoryPath = directoryPath;
        this.processedPath = directoryPath + "/processed/";
        new File(processedPath).mkdirs(); // Création du dossier processed s'il n'existe pas
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
        System.out.println("🚀 Job started : " + jobExecution.getJobInstance().getJobName());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        System.out.println("✅ Job finished with status : " + jobExecution.getStatus());

        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            File directory = new File(directoryPath);
            File[] jsonFiles = directory.listFiles((dir, name) -> name.endsWith(".json"));

            if (jsonFiles == null || jsonFiles.length == 0) {
                System.out.println("⚠️ Aucun fichier JSON à traiter.");
                return;
            }

            for (File file : jsonFiles) {
                try {
                    String destinationPath = "success/" + file.getName();
                    minIOService.uploadFileToMinIO(file.getAbsolutePath(), destinationPath);
                    System.out.println("✅ Fichier transféré vers MinIO : " + destinationPath);

                    // Déplacer le fichier dans le dossier processed
                    File processedFile = new File(processedPath + file.getName());
                    Files.move(file.toPath(), processedFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("📂 Fichier déplacé vers : " + processedFile.getAbsolutePath());
                } catch (Exception e) {
                    System.err.println("❌ Erreur lors du traitement du fichier " + file.getName() + " : " + e.getMessage());
                }
            }
        } else {
            System.out.println("⚠️ Job did not complete successfully, skipping MinIO upload.");
        }
    }
}
