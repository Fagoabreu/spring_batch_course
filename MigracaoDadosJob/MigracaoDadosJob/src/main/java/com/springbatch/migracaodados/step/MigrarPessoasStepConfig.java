package com.springbatch.migracaodados.step;

import com.springbatch.migracaodados.dominio.Pessoa;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.ItemReader;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MigrarPessoasStepConfig {

    @Bean
    public Step migrarPessoasStep(
            JobRepository jobRepository,
            ItemReader<Pessoa> arquivoPessoaReader,
            ItemWriter<Pessoa> bancoPessoaWriter
    ){
        return new StepBuilder("migrarPessoasStep", jobRepository)
                .<Pessoa, Pessoa>chunk(1)
                .reader(arquivoPessoaReader)
                .writer(bancoPessoaWriter)
                .build();
    }
}
