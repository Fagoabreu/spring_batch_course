package com.springbatch.migracaodados.job;

import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.parameters.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MigracaoDadosJobConfig {

    @Bean
    public Job migracaoDadosJob(
            JobRepository jobRepository,
            Step migrarPessoasStep,
            Step migrarDadosBancariosStep
            ){
        return new JobBuilder("migracaoDadosJob",jobRepository )
                .start(migrarPessoasStep)
                .next(migrarDadosBancariosStep)
                .incrementer(new RunIdIncrementer())
                .build();
    }
}
