package com.jcs.batch.job;

import com.jcs.batch.listener.EmpJobListener;
import com.jcs.batch.listener.EmpStepReadListener;
import com.jcs.batch.mapper.EmployeeFileRowMapper;
import com.jcs.batch.model.EmployeeDTO;
import com.jcs.batch.policy.EmployeeJobSkipPolicy;
import com.jcs.batch.processor.EmployeeProcessor;
import com.jcs.batch.writer.EmployeeWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
@Slf4j
public class EmpJob {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private DataSource dataSource;

    //    using custom ItemWriter
    @Autowired
    private EmployeeWriter employeeWriter;

    private final String INSERT_QUERY = "insert into employee (emp_id, first_name, last_name, email, phone_no, hire_date, job_id, salary, new_salary) values (:empId, :firstName, :lastName, :email, :phoneNo, :hireDate, :jobId, :salary, :newSalary)";

    @Bean
    public FlatFileItemReader<EmployeeDTO> reader() {
        FlatFileItemReader<EmployeeDTO> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("static/sample-data.csv"));
        reader.setLineMapper(lineMapper());
        reader.setLinesToSkip(1);
        return reader;
    }

    @Bean
    public DefaultLineMapper<EmployeeDTO> lineMapper() {
        DefaultLineMapper<EmployeeDTO> defaultLineMapper = new DefaultLineMapper<>();
        defaultLineMapper.setLineTokenizer(lineTokenizer());
        defaultLineMapper.setFieldSetMapper(fieldSetMapper());
        return defaultLineMapper;
    }

    @Bean
    public LineTokenizer lineTokenizer() {
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setDelimiter(",");
        tokenizer.setNames("EmployeeId", "EmpFirstName", "EmpLastName", "EmpEmail", "EmpPhoneNo", "EmpHireDate", "EmpJobId", "EmpSalary");
        return tokenizer;
    }

    @Bean
    public FieldSetMapper<EmployeeDTO> fieldSetMapper() {
//        BeanWrapperFieldSetMapper<EmployeeDTO> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
//        fieldSetMapper.setTargetType(EmployeeDTO.class);
        return new EmployeeFileRowMapper();
    }

    @Bean
    public ItemProcessor<EmployeeDTO, EmployeeDTO> processor() {
        return new EmployeeProcessor();
    }

//    using inbuilt ItemWriter
//    @Bean
//    public JdbcBatchItemWriter<EmployeeDTO> writer() {
//        log.info("inside writer");
//        JdbcBatchItemWriter<EmployeeDTO> writer = new JdbcBatchItemWriter<>();
//        writer.setDataSource(dataSource);
//        writer.setSql(INSERT_QUERY);
//        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<EmployeeDTO>());
//        return writer;
//    }

    @Bean
    public JobExecutionListener listener() {
        return new EmpJobListener();
    }

    @Bean
    public Step stepA() {
        return stepBuilderFactory.get("stepA")
                .<EmployeeDTO, EmployeeDTO>chunk(2)
                .reader(reader())
                .processor(processor())
                .writer(employeeWriter)
                .faultTolerant().skipPolicy(skipPolicy())
                .listener(readListener())
//                .listener(processListener())
//                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public Step stepB() {
        return stepBuilderFactory.get("stepB")
                .<EmployeeDTO, EmployeeDTO>chunk(3)
                .reader(reader())
                .processor(processor())
                .writer(employeeWriter)
//                .faultTolerant().skipPolicy(skipPolicy())
//                .listener(readListener())
//                .listener(processListener())
//                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public Job jobA() {
        return jobBuilderFactory.get("emp-job")
                .start(stepA())
//                .listener(listener())
//                .next(stepB())
                .build();
    }

    @Bean
    public SkipPolicy skipPolicy() {
        return new EmployeeJobSkipPolicy();
    }

    @Bean
    public ItemReadListener<EmployeeDTO> readListener() {
        return new EmpStepReadListener();
    }

//    @Bean
//    public ItemProcessListener<EmployeeDTO, EmployeeDTO> processListener() {
//        return new EmpProcessListener();
//    }

//    @Bean
//    public TaskExecutor taskExecutor() {
//        SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
//        asyncTaskExecutor.setConcurrencyLimit(4);
//        return asyncTaskExecutor;
//    }

//    @Bean
//    public Job jobB() {
//        return jobBuilderFactory.get("emp-job-2")
//                .start(taskletStep())
//                .build();
//    }

//    @Bean
//    public Step taskletStep() {
//        return stepBuilderFactory.get("tasklet-step")
//                .tasklet(new ArchiveDataCleanup())
//                .build();
//    }
}
