package com.springbatch.migracaodados.step;

import com.springbatch.migracaodados.dominio.DadosBancarios;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.ItemReader;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MigrarDadosBancariosStepConfig {

    @Bean
    public Step migrarDadosBancariostep(
            JobRepository jobRepository,
            ItemReader<DadosBancarios> arquivoDadosBancariosReader,
            ItemWriter<DadosBancarios> bancoDadosBancariosWriter
    ){
        return new StepBuilder("migrarDadosBancariosStep", jobRepository)
                .<DadosBancarios, DadosBancarios>chunk(1)
                .reader(arquivoDadosBancariosReader)
                .writer(bancoDadosBancariosWriter)
                .build();
    }
}
