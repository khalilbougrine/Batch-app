package spring_batch_json.example.batch_app.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring_batch_json.example.batch_app.batch.VoitureItemProcessor;
import spring_batch_json.example.batch_app.batch.VoitureItemWriter;
import spring_batch_json.example.batch_app.batch.VoitureJsonReader;
import spring_batch_json.example.batch_app.model.Voiture;

@Configuration
public class BatchConfig {

    private final VoitureJsonReader voitureJsonReader;
    private final VoitureItemProcessor voitureItemProcessor;
    private final VoitureItemWriter voitureItemWriter;

    public BatchConfig(VoitureJsonReader voitureJsonReader,
                       VoitureItemProcessor voitureItemProcessor,
                       VoitureItemWriter voitureItemWriter) {
        this.voitureJsonReader = voitureJsonReader;
        this.voitureItemProcessor = voitureItemProcessor;
        this.voitureItemWriter = voitureItemWriter;
    }

    @Bean
    public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("step1", jobRepository)
                .<Voiture, Voiture>chunk(10, transactionManager)
                .reader(voitureJsonReader)
                .processor(voitureItemProcessor)
                .writer(voitureItemWriter)
                .build();
    }

    @Bean
    public Job importVoitureJob(JobRepository jobRepository, Step step1, JobCompletionListener jobCompletionListener) {
        return new JobBuilder("importVoitureJob", jobRepository)
                .listener(jobCompletionListener) // Ajout du listener ici
                .start(step1)
                .build();
    }
}
