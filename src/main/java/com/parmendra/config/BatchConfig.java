package com.parmendra.config;


import com.parmendra.entity.Customers;
import com.parmendra.repository.CustomerRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@EnableBatchProcessing
@Configuration
public class BatchConfig {

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private CustomerRepository customerRepository;

    // Read SCV from source
    FlatFileItemReader<Customers> fileItemReader() {

        FlatFileItemReader<Customers> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new FileSystemResource("src/main/resources/customers.csv"));
        itemReader.setName("csvReader");
        itemReader.setLinesToSkip(1); // Skip Headers
        itemReader.setLineMapper(lineMapper()); // How to read the CSV file
        return itemReader;
    }

    // LineMapper Configuration , provide way to read the CSV files
    LineMapper<Customers> lineMapper() {

        //LineTokenizer — which breaks each line of the CSV into individual values (fields).
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("cid", "name", "address", "mobile_no", "order_id");

        //Map the Data into Object
        BeanWrapperFieldSetMapper<Customers> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Customers.class);

        // LineMapper config
        DefaultLineMapper<Customers> defaultLineMapper = new DefaultLineMapper<>();
        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(fieldSetMapper);
        return defaultLineMapper;
    }

    // Processor
    @Bean
    public CusromerProcessor processor() {
        return new CusromerProcessor();
    }

    //Writer
    @Bean
    RepositoryItemWriter<Customers> writer(){
        RepositoryItemWriter<Customers> itemWriter = new RepositoryItemWriter<>();
        itemWriter.setRepository(customerRepository);
        itemWriter.setMethodName("save");
        return itemWriter;
    }

    // Step Creation
    @Bean
    public Step step1(){

       return stepBuilderFactory.get("Step-Builder").<Customers,Customers>chunk(10)
                .reader(fileItemReader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public Job runJob(){
        System.out.println("Job bean created but not yet executed.");

        return jobBuilderFactory.get("Job-Runner")
                .flow(step1()).end().build();
    }

}
