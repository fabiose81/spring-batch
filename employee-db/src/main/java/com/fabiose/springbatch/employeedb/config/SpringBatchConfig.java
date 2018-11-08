package com.fabiose.springbatch.employeedb.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.fabiose.springbatch.employeedb.domain.Employee;
import com.fabiose.springbatch.employeedb.mapper.EmployeeMapper;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {

	@Bean
    public Job job(JobBuilderFactory jobBuilderFactory,
                   StepBuilderFactory stepBuilderFactory,
                   ItemReader<Employee> itemReader,
                   ItemProcessor<Employee, Employee> itemProcessor,
                   ItemWriter<Employee> itemWriter
    ) {

        Step step = stepBuilderFactory.get("ETL-file-load")
                .<Employee, Employee>chunk(100)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();


        return jobBuilderFactory.get("ETL-Load")
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();
    }
	
    @Bean
    @StepScope
    @ConfigurationProperties(prefix = "app", ignoreInvalidFields = false)
    public FlatFileItemReader<Employee> itemReader(@Value("${app.path}") String path, @Value("#{jobParameters['file']}") String file) {
    		
        FlatFileItemReader<Employee> flatFileItemReader = new FlatFileItemReader<>();
        
        String pathResource = path.concat("\\").concat(file);
 
        flatFileItemReader.setResource(new FileSystemResource(pathResource));
        flatFileItemReader.setName("CSV-Reader");
        flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.setLineMapper(lineMapper());
        return flatFileItemReader;
    }
    
    @Bean
    public LineMapper<Employee> lineMapper() {
    	 DefaultLineMapper<Employee> defaultLineMapper = new DefaultLineMapper<>();
         DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();

         lineTokenizer.setDelimiter(",");
         lineTokenizer.setStrict(false);
         lineTokenizer.setNames(new String[]{"name", "project", "start", "end"});

         BeanWrapperFieldSetMapper<Employee> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
         fieldSetMapper.setTargetType(Employee.class);

         defaultLineMapper.setLineTokenizer(lineTokenizer);
         defaultLineMapper.setFieldSetMapper(fieldSetMapper);
         defaultLineMapper.setFieldSetMapper(new EmployeeMapper());

         return defaultLineMapper;
    }
}
