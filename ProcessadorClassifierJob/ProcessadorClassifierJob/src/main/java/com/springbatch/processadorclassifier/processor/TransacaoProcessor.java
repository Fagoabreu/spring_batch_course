package com.springbatch.processadorclassifier.processor;

import com.springbatch.processadorclassifier.dominio.Transacao;
import org.springframework.batch.item.ItemProcessor;

public class TransacaoProcessor implements ItemProcessor<Transacao,Transacao> {

    @Override
    public Transacao process(Transacao transacao) throws Exception {
        System.out.println(String.format("Aplicando regras de negocio na transacao %s",transacao.getId()));
        return transacao;
    }
}
