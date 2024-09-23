package org.example.compondRecord;

import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.example.simpleRecord.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AvroPersonService {
    public  void generateAvor() throws IOException {

        Address address = new Address().newBuilder()
                .setCity("Anytown")
                .setStreet("123 Main St")
                .setZipCode("12345").build();

        List <Address> addresses = new ArrayList<>();
        addresses.add(address);
        Person person = new Person().newBuilder()
                .setName("John Doe")
                .setAge(30)
                .setAddresses(addresses)
                .build();

        File file = new File("person.avro");
        DatumWriter<Person> personDatumWriter = new SpecificDatumWriter<Person>(Person.class);
        DataFileWriter<Person> dataFileWriter = new DataFileWriter<Person>(personDatumWriter);
        dataFileWriter.create(person.getSchema(), file);
        dataFileWriter.append(person);
        dataFileWriter.close();

    }

    public void avroToJson() throws IOException {
        DatumReader<Person> personDatumReader = new SpecificDatumReader<Person>(Person.class);
        DataFileReader<Person> dataFileReader = new DataFileReader<Person>(new File("person.avro"), personDatumReader);
        Person person = null;
        while (dataFileReader.hasNext()) {
            person = dataFileReader.next(person);
            System.out.println(person);
        }

    }
}
