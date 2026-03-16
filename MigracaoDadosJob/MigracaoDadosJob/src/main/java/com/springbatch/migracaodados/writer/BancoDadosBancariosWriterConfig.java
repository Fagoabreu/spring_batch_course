package com.springbatch.migracaodados.writer;

import com.springbatch.migracaodados.dominio.DadosBancarios;
import org.springframework.batch.infrastructure.item.database.JdbcBatchItemWriter;
import org.springframework.batch.infrastructure.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class BancoDadosBancariosWriterConfig {

    @Bean
    public JdbcBatchItemWriter<DadosBancarios> bancoDadosBancariosWriter(
            @Qualifier("appDataSource") DataSource dataSource
    ){
        return new JdbcBatchItemWriterBuilder<DadosBancarios>()
                .dataSource(dataSource)
                .sql("Insert into dados_bancarios (id,pessoa_id,agencia,conta,banco) values (:id,:pessoaId,:agencia,:conta,:banco)")
                .beanMapped()
                .build();
    }
}
