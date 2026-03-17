package com.springbatch.faturacartaocredito.domain;

public class CartaoCredito {
    private int numetoCartaoCredito;
    private Cliente cliente;

    public int getNumetoCartaoCredito() {
        return numetoCartaoCredito;
    }

    public void setNumetoCartaoCredito(int numetoCartaoCredito) {
        this.numetoCartaoCredito = numetoCartaoCredito;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
