package com.springbatch.contasbancarias.writer;

import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springbatch.contasbancarias.dominio.Conta;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Configuration
public class JdbcContaWriterConfig {
	@Bean
	public JdbcBatchItemWriter<Conta> jdbcContaWriter(
			@Qualifier("appDataSource")DataSource dataSource
			) {
		return new JdbcBatchItemWriterBuilder<Conta>()
				.dataSource(dataSource)
				.sql("insert into conta (tipo,limite,cliente_id) values(?,?,?)")
				.itemPreparedStatementSetter(itemPrepareStatementSetter())
				.build();
	}

	private ItemPreparedStatementSetter<Conta> itemPrepareStatementSetter() {
		return new ItemPreparedStatementSetter<Conta>() {
			@Override
			public void setValues(Conta conta, PreparedStatement preparedStatement) throws SQLException {
				preparedStatement.setString(1,conta.getTipo().name());
				preparedStatement.setDouble(2,conta.getLimite());
				preparedStatement.setString(3,conta.getClienteId());
			}
		};
	}
}
