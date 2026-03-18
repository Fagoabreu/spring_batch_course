package com.springbatch.faturacartaocredito.step;

import com.springbatch.faturacartaocredito.domain.FaturaCartaoCredito;
import com.springbatch.faturacartaocredito.domain.Transacao;
import com.springbatch.faturacartaocredito.reader.FaturaCartaoCreditoReader;
import com.springbatch.faturacartaocredito.writer.TotalTransacoesFooterCallback;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.batch.infrastructure.item.ItemStreamReader;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class FaturaCartaoCreditoStepConfig {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    public FaturaCartaoCreditoStepConfig(JobRepository jobRepository,
                                         PlatformTransactionManager transactionManager) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
    }

    @Bean
    Step faturaCartaoCreditoStep(
            ItemStreamReader<Transacao> lerTransacoesReader,
            ItemProcessor<FaturaCartaoCredito, FaturaCartaoCredito> carregarDadosClienteProcessor,
            ItemWriter<FaturaCartaoCredito> escreverFaturaCartaoCredito,
            TotalTransacoesFooterCallback listener) {
        return new StepBuilder("faturaCartaoCreditoStep", jobRepository)
                .<FaturaCartaoCredito, FaturaCartaoCredito>chunk(1)
                .reader(new FaturaCartaoCreditoReader(lerTransacoesReader))
                .processor(carregarDadosClienteProcessor)
                .writer(escreverFaturaCartaoCredito)
                .listener(listener)
                .build();
    }
}
