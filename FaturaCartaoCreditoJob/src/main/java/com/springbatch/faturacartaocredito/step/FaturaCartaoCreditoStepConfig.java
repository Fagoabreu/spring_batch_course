package com.springbatch.faturacartaocredito.step;

import com.springbatch.faturacartaocredito.domain.FaturaCartaoCredito;
import com.springbatch.faturacartaocredito.domain.Transacao;
import com.springbatch.faturacartaocredito.reader.FaturaCartaoCreditoReader;
import com.springbatch.faturacartaocredito.writer.TotalTransacoesFooterCallBack;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.batch.infrastructure.item.ItemStreamReader;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FaturaCartaoCreditoStepConfig {
    @Bean
    public Step faturaCartaoCreditoStep(
            JobRepository jobRepository,
            ItemStreamReader<Transacao> lerTransacoesReader,
            ItemProcessor<FaturaCartaoCredito,FaturaCartaoCredito> carregarDadosClienteProcessor,
            ItemWriter<FaturaCartaoCredito> escreverFaturaCartaoCredito,
            TotalTransacoesFooterCallBack listener
    ){
        return new StepBuilder("faturaCartaoCreditoStep",jobRepository)
                .<FaturaCartaoCredito,FaturaCartaoCredito>chunk(1)
                .reader(new FaturaCartaoCreditoReader(lerTransacoesReader))
                .processor(carregarDadosClienteProcessor)
                .writer(escreverFaturaCartaoCredito)
                .listener(listener)
                .build();

    }
}
