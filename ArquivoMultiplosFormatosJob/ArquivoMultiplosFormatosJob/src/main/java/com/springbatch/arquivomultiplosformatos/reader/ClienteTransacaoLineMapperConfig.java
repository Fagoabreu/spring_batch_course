package com.springbatch.arquivomultiplosformatos.reader;

import com.springbatch.arquivomultiplosformatos.dominio.Cliente;
import com.springbatch.arquivomultiplosformatos.dominio.Transacao;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.mapping.PatternMatchingCompositeLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ClienteTransacaoLineMapperConfig {

    @Bean
    public PatternMatchingCompositeLineMapper lineMapper(){
        PatternMatchingCompositeLineMapper lineMapper = new PatternMatchingCompositeLineMapper();
        lineMapper.setTokenizers(tokenizers());
        lineMapper.setFieldSetMappers(fieldSetMappers());
        return lineMapper;
    }

    private Map<String, FieldSetMapper> fieldSetMappers() {
        Map<String, FieldSetMapper> fieldSetMappers = new HashMap<>();
        fieldSetMappers.put("0*",fieldSetMapper(Cliente.class));
        fieldSetMappers.put("1*",fieldSetMapper(Transacao.class));
        return fieldSetMappers;
    }

    private FieldSetMapper fieldSetMapper(Class classParam) {
        BeanWrapperFieldSetMapper fieldSetMapper = new BeanWrapperFieldSetMapper();
        fieldSetMapper.setTargetType(classParam);
        return fieldSetMapper;
    }

    private Map<String, LineTokenizer> tokenizers() {
        Map<String,LineTokenizer> tokenizerMap = new HashMap<>();
        tokenizerMap.put("0*",clientLineTokenizer());
        tokenizerMap.put("1*",transacaoLineTokenizer());
        return tokenizerMap;
    }

    private LineTokenizer clientLineTokenizer() {
        DelimitedLineTokenizer lineTokenizer  = new DelimitedLineTokenizer();
        lineTokenizer.setNames("nome","sobrenome","idade","email");
        lineTokenizer.setIncludedFields(1,2,3,4);
        return lineTokenizer;
    }

    private LineTokenizer transacaoLineTokenizer() {
        DelimitedLineTokenizer lineTokenizer  = new DelimitedLineTokenizer();
        lineTokenizer.setNames("id","descricao","valor");
        lineTokenizer.setIncludedFields(1,2,3);
        return lineTokenizer;
    }
}
