package com.springbatch.faturacartaocredito.writer;

import com.springbatch.faturacartaocredito.domain.FaturaCartaoCredito;
import com.springbatch.faturacartaocredito.domain.Transacao;
import org.springframework.batch.infrastructure.item.file.*;
import org.springframework.batch.infrastructure.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.infrastructure.item.file.builder.MultiResourceItemWriterBuilder;
import org.springframework.batch.infrastructure.item.file.transform.LineAggregator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import java.io.IOException;
import java.io.Writer;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

@Configuration
public class ArquivoFaturaCartaoCreditoWriterConfig {
    @Bean
    public MultiResourceItemWriter<FaturaCartaoCredito> arquivosFaturaCartaoCredito(){
        return new MultiResourceItemWriterBuilder<FaturaCartaoCredito>()
                .name("arquivoFaturaCartaoCredito")
                .resource(new FileSystemResource("files/fatura"))
                .itemCountLimitPerResource(1)
                .resourceSuffixCreator(sufixCreator())
                .delegate(arquivoFaturaCartaoCredito())
                .build();
    }

    private FlatFileItemWriter<FaturaCartaoCredito> arquivoFaturaCartaoCredito() {
        return new FlatFileItemWriterBuilder<FaturaCartaoCredito>()
                .name("arquivoFaturaCartaoCredito")
                .resource(new FileSystemResource("files/fatura.txt"))
                .lineAggregator(lineAggregator())
                .headerCallback(headerCallback())
                .footerCallback(footerCallBack())
                .build();
    }

    private FlatFileFooterCallback footerCallBack() {
        return new TotalTransacoesFooterCallBack();
    }

    private FlatFileHeaderCallback headerCallback() {
        return new FlatFileHeaderCallback() {
            @Override
            public void writeHeader(Writer writer) throws IOException {
                writer.append((String.format("%121s\n", "Cartao XPTO")))
                        .append(String.format("%121s\n\n","Rua Vergueiro, 131"));
            }
        };
    }

    private LineAggregator<FaturaCartaoCredito> lineAggregator() {
        return new LineAggregator<FaturaCartaoCredito>() {
            @Override
            public String aggregate(FaturaCartaoCredito faturaCartaoCredito) {
                StringBuilder writer = new StringBuilder()
                        .append(String.format("Nome: %4\n",faturaCartaoCredito.getCliente().getNome()))
                        .append(String.format("Endereco: %s\n\n\n",faturaCartaoCredito.getCliente().getEndereco()))
                        .append(String.format("Fatura completa do cartao %d\n",faturaCartaoCredito.getCartaoCredito().getNumetoCartaoCredito()))
                        .append("------------\n")
                        .append("Data Descricao Valor \n")
                        .append("------------\n");
                for(Transacao transacao:faturaCartaoCredito.getTransacoes()){
                    writer.append(String.format(
                            "\n[%10s] %-80s %s",
                            new SimpleDateFormat("dd/MM/yyyy").format(transacao.getData()),
                            transacao.getDescricao(),
                            NumberFormat.getCurrencyInstance().format(transacao.getValor())
                    ));
                }
                return writer.toString();
            }
        };
    }

    private ResourceSuffixCreator sufixCreator() {
        return new ResourceSuffixCreator() {
            @Override
            public String getSuffix(int index) {
                return index+".txt";
            }
        };
    }
}
