package com.linktic.batch;

import static java.util.Arrays.stream;

import com.linktic.model.product.FieldName;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.DuplicateKeyException;

@Slf4j
@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class ProductJobConfig {


  private static final String DELIMITER = ",";
  private final String[] fieldNames = fieldsToArray();
  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;
  @Value("${massive-load.input-file}")
  private String massiveLoadFile;

  @Bean
  public FlatFileItemReader<Product> reader() {
    return new FlatFileItemReaderBuilder<Product>()
        .name("productItemReader")
        .resource(new ClassPathResource(massiveLoadFile))
        .delimited()
        .names(String.join(",", fieldNames))
        .linesToSkip(1)
        .lineMapper(lineMapper())
        .fieldSetMapper(fieldSet -> getMapper(Product.class, fieldSet))
        .build();
  }

  @SneakyThrows
  private Product getMapper(Class<Product> type, FieldSet fieldSet) {
    var beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<Product>();
    beanWrapperFieldSetMapper.setTargetType(type);
    return beanWrapperFieldSetMapper.mapFieldSet(fieldSet);
  }

  @Bean
  public LineMapper<Product> lineMapper() {
    final DefaultLineMapper<Product> defaultLineMapper = new DefaultLineMapper<>();
    final DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
    lineTokenizer.setDelimiter(DELIMITER);
    lineTokenizer.setStrict(true);
    lineTokenizer.setNames(fieldNames);
    final FieldSetMapper fieldSetMapper = new ProductFieldSetMapper();
    defaultLineMapper.setLineTokenizer(lineTokenizer);
    defaultLineMapper.setFieldSetMapper(fieldSetMapper);
    return defaultLineMapper;
  }

  @Bean
  public ProductProcessor processor() {
    return new ProductProcessor();
  }

  @Bean
  public JdbcBatchItemWriter<Product> writer(DataSource dataSource) {
    return new JdbcBatchItemWriterBuilder<Product>()
        .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
        .sql("INSERT INTO Product (name, brand, price, stock_quantity, status, discount_percentage) VALUES (:name, :brand, :price, :stockQuantity, :status, :discountPercentage)")
        .dataSource(dataSource)
        .build();
  }

  @Bean
  public Step step1(JdbcBatchItemWriter<Product> writer) {
    return stepBuilderFactory.get("step1")
        .<Product, Product>chunk(10)
        .reader(reader())
        .processor(processor())
        .writer(writer)
        .faultTolerant()
        .skipLimit(100)
        .skip(FlatFileParseException.class)
        .skip(DuplicateKeyException.class)
        .skip(JdbcSQLIntegrityConstraintViolationException.class)
        .build();
  }

  @Bean
  public Job importProductJob(Step step1) {
    return jobBuilderFactory.get("importProductJob")
        .incrementer(new RunIdIncrementer())
        //.listener(listener)
        .flow(step1)
        .end()
        .build();
  }

  private String[] fieldsToArray() {
    return stream(FieldName.values()).map(FieldName::getFieldName)
        .filter(field -> !field.equalsIgnoreCase(FieldName.ID.getFieldName()))
        .toArray(String[]::new);
  }

}
