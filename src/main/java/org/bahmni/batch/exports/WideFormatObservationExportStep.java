package org.bahmni.batch.exports;

import org.bahmni.batch.exception.BatchResourceException;
import org.bahmni.batch.form.LeafObservationProcessor;
import org.bahmni.batch.form.ObsFieldExtractor;
import org.bahmni.batch.form.ObservationProcessor;
import org.bahmni.batch.form.PatientFieldExtractor;
import org.bahmni.batch.form.domain.BahmniForm;
import org.bahmni.batch.form.domain.Obs;
import org.bahmni.batch.form.domain.Patient;
import org.bahmni.batch.form.domain.Person;
import org.bahmni.batch.helper.FreeMarkerEvaluator;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@Scope(value = "prototype")
public class WideFormatObservationExportStep {

    public static final String FILE_NAME_EXTENSION = ".csv";

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private DataSource dataSource;

    @Value("${outputFolder}")
    public Resource outputFolder;

    @Autowired
    private FreeMarkerEvaluator<BahmniForm> freeMarkerEvaluator;

    private BahmniForm form;

    @Autowired
    private ObjectFactory<ObservationProcessor> observationProcessorFactory;

    @Autowired
    private ObjectFactory<LeafObservationProcessor> leafObservationProcessorObjectFactory;

    public void setOutputFolder(Resource outputFolder) {
        this.outputFolder = outputFolder;
    }

    public Step getStep() {
        return stepBuilderFactory.get(getStepName())
                .<Patient, Patient>chunk(100)
                .reader(obsReader())
                .processor(observationProcessor())
                .writer(obsWriter())
                .build();
    }

    private JdbcCursorItemReader<Patient> obsReader() {
        String sql = freeMarkerEvaluator.evaluate("patientsWithFormFilled.ftl",form);
        System.out.println(sql);
        JdbcCursorItemReader<Patient> reader = new JdbcCursorItemReader<>();
        reader.setDataSource(dataSource);
        reader.setSql(sql);
        reader.setRowMapper(new BeanPropertyRowMapper<Patient>(Patient.class){
            public Patient mapRow(ResultSet rs, int i) throws SQLException {
                Patient patient = super.mapRow(rs,i);
                Person person = new Person();
                person.setIdentifier(rs.getString("person_id"));
                patient.setPerson(person);
                return patient;
            }
        });
        return reader;
    }

    private CompositeItemProcessor observationProcessor() {
        CompositeItemProcessor<Map<String, Object>, List<Patient>> compositeProcessor = new CompositeItemProcessor<>();
        List itemProcessors = new ArrayList<>();

//        ObservationProcessor observationProcessor = observationProcessorFactory.getObject();
//        observationProcessor.setForm(form);
//        itemProcessors.add(observationProcessor);

        LeafObservationProcessor leafObservationProcessor = leafObservationProcessorObjectFactory.getObject();
        leafObservationProcessor.setForm(form);
        itemProcessors.add(leafObservationProcessor);

        compositeProcessor.setDelegates(itemProcessors);

        return compositeProcessor;
    }

    private FlatFileItemWriter<Patient> obsWriter() {

        FlatFileItemWriter<Patient> writer = new FlatFileItemWriter<>();
        writer.setResource(new FileSystemResource(getOutputFile()));

        DelimitedLineAggregator<Patient> delimitedLineAggregator = new DelimitedLineAggregator<>();
        delimitedLineAggregator.setDelimiter(",");
        PatientFieldExtractor fieldExtractor = new PatientFieldExtractor(form);
        delimitedLineAggregator.setFieldExtractor(fieldExtractor);

        writer.setLineAggregator(delimitedLineAggregator);
        writer.setHeaderCallback(fieldExtractor);

        return writer;
    }

    private File getOutputFile(){
        File outputFile;

        try {
            outputFile = new File(outputFolder.getFile(),form.getDisplayName() + FILE_NAME_EXTENSION);
        }
        catch (IOException e) {
            throw new BatchResourceException("Unable to create a file in the outputFolder ["+ outputFolder.getFilename()+"]",e);
        }

        return outputFile;
    }


    public void setForm(BahmniForm form) {
        this.form = form;
    }

    public String getStepName() {
        String formName = form.getFormName().getName();
        return formName.substring(0,Math.min(formName.length(), 100));
    }
}