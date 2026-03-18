package com.springbatch.faturacartaocredito.processor;

import com.springbatch.faturacartaocredito.domain.Cliente;
import com.springbatch.faturacartaocredito.domain.FaturaCartaoCredito;
import org.jspecify.annotations.Nullable;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.batch.infrastructure.item.validator.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class CarregaDadosClienteProcessor implements ItemProcessor<FaturaCartaoCredito,FaturaCartaoCredito> {
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public @Nullable FaturaCartaoCredito process(FaturaCartaoCredito faturaCartaoCredito) throws Exception {
        String uri = String.format("http://my-json-server.typicode.com/giuliana-bezerra/demo/profile/%d", faturaCartaoCredito.getCliente().getId());
        ResponseEntity<Cliente> response = restTemplate.getForEntity(uri, Cliente.class);

        if(response.getStatusCode() != HttpStatus.OK)
            throw new ValidationException("Cliente não encontrado!");

        faturaCartaoCredito.setCliente(response.getBody());
        return faturaCartaoCredito;
    }
}
