package com.springbatch.migracaodados.reader;

import com.springbatch.migracaodados.dominio.DadosBancarios;
import org.springframework.batch.infrastructure.item.file.FlatFileItemReader;
import org.springframework.batch.infrastructure.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.infrastructure.item.file.mapping.FieldSetMapper;
import org.springframework.batch.infrastructure.item.file.transform.FieldSet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.validation.BindException;


@Configuration
public class ArquivoDadosBancariosReaderConfig {
    @Bean
    public FlatFileItemReader<DadosBancarios> arquivoDadosBancariosReader(){
        return new FlatFileItemReaderBuilder<DadosBancarios>()
                .name("arquivoDadosBancariosReader")
                .resource(new FileSystemResource("files/dados_bancarios.csv"))
                .delimited()
                .names("pessoaId","agencia","conta","banco","id")
                .addComment("--")
                .targetType(DadosBancarios.class)
                .build();
    }
}
