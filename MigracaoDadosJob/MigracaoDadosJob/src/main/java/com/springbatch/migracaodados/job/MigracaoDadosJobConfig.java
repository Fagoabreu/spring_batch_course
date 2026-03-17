package com.springbatch.migracaodados.job;

import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.parameters.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

@Configuration
public class MigracaoDadosJobConfig {

    @Bean
    public Job migracaoDadosJob(
            JobRepository jobRepository,
            @Qualifier("migrarPessoasStep")Step migrarPessoasStep,
            @Qualifier("migrarDadosBancariostep")Step migrarDadosBancariosStep
            ){
        return new JobBuilder("migracaoDadosJob",jobRepository )
                .start(stepsParalelos(migrarPessoasStep, migrarDadosBancariosStep))
                .end()
                .incrementer(new RunIdIncrementer())
                .build();
    }

    private Flow stepsParalelos(Step migrarPessoasStep, Step migrarDadosBancariosStep) {
        Flow migrarDadosBancariosFlow = new FlowBuilder<Flow>("migrarDadosBancariosFlow")
                .start(migrarDadosBancariosStep)
                .build();
        Flow stepsParalelos = new FlowBuilder<Flow>("stepsParalelosFlow")
                .start(migrarPessoasStep)
                .split(new SimpleAsyncTaskExecutor())
                .add(migrarDadosBancariosFlow)
                .build();
        return stepsParalelos;
    }
}
