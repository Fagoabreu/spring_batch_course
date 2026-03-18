package com.springbatch.faturacartaocredito.reader;

import com.springbatch.faturacartaocredito.domain.FaturaCartaoCredito;
import com.springbatch.faturacartaocredito.domain.Transacao;
import org.jspecify.annotations.Nullable;
import org.springframework.batch.infrastructure.item.ExecutionContext;
import org.springframework.batch.infrastructure.item.ItemStreamException;
import org.springframework.batch.infrastructure.item.ItemStreamReader;

public class FaturaCartaoCreditoReader implements ItemStreamReader<FaturaCartaoCredito> {
    private final ItemStreamReader<Transacao>delegate;
    private Transacao transacaoAtual;

    public FaturaCartaoCreditoReader(ItemStreamReader<Transacao> delegate){
        this.delegate=delegate;
    }

    @Override
    public @Nullable FaturaCartaoCredito read() throws Exception {
        if (transacaoAtual==null){
            transacaoAtual = delegate.read();
        }

        FaturaCartaoCredito faturaCartaoCredito = null;
        Transacao transacao = transacaoAtual;
        transacaoAtual=null;

        if(transacao!=null){
            faturaCartaoCredito = new FaturaCartaoCredito();
            faturaCartaoCredito.setCartaoCredito(transacao.getCartaoCredito());
            faturaCartaoCredito.setCliente(transacao.getCartaoCredito().getCliente());
            faturaCartaoCredito.getTransacoes().add(transacao);

            while(isTransacaoRelacionada(transacao)){
                faturaCartaoCredito.getTransacoes().add(transacaoAtual);
            }
        }
        return faturaCartaoCredito;
    }

    private boolean isTransacaoRelacionada(Transacao transacao) throws Exception {
        return peek() !=null && transacao.getCartaoCredito().getNumetoCartaoCredito() == transacaoAtual.getCartaoCredito().getNumetoCartaoCredito();
    }

    private Transacao peek() throws Exception {
        transacaoAtual = delegate.read();
        return transacaoAtual;
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        delegate.open(executionContext);
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        delegate.update(executionContext);
    }

    @Override
    public void close() throws ItemStreamException {
        delegate.close();
    }
}
