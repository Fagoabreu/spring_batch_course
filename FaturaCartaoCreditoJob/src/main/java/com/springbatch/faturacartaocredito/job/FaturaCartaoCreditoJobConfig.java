package com.springbatch.faturacartaocredito.job;

import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.parameters.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FaturaCartaoCreditoJobConfig {
    @Bean
    public Job faturaCartaoCreditoJob(Step faturaCartaoCreditoStep, JobRepository jobRepository){
        return new JobBuilder("jobBuilder",jobRepository)
                .start(faturaCartaoCreditoStep)
                .incrementer(new RunIdIncrementer())
                .build();

    }
}
